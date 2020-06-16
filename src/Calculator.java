import org.apache.jena.atlas.lib.Pair;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntTools;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

enum MeasureType {

    MAX_DEPTH_POWER,
    DEPTH_POWER,
    DEPTH_SUM
}

public class Calculator {

    String text = "";


    double calculateMeasureMaxDepth(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title) {
        return calculate(ont1, ont2, mappings, writeToFile, title, MeasureType.MAX_DEPTH_POWER);
    }

    double calculateDepthPower(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title) {
        return calculate(ont1, ont2, mappings, writeToFile, title, MeasureType.DEPTH_POWER);
    }

    double calculateDepthSum(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title) {
        return calculate(ont1, ont2, mappings, writeToFile, title, MeasureType.DEPTH_SUM);
    }

    double calculate(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title, MeasureType type) {

        double value = 0;
        double wholeTreeValue = 0;

        text = "Klasa,wartosc\n";

        ExtendedIterator classes = mappings.listClasses();
        while (classes.hasNext()) {
            OntClass Ontclass = (OntClass) classes.next();
            if (Ontclass.getEquivalentClass() != null) {

                OntClass tree = ont1.getOntClass(Ontclass.getURI());

                if (tree == null) {
                    tree = ont2.getOntClass(Ontclass.getURI());
                }

                if (tree != null) {
                    double val = countTree(tree, mappings, type);
                    value = value + val;
                }


                ExtendedIterator equivalents = Ontclass.listEquivalentClasses();
                while (equivalents.hasNext()) {
                    OntClass equivalent = (OntClass) equivalents.next();

                    tree = ont1.getOntClass(equivalent.getURI());

                    if (tree == null) {
                        tree = ont2.getOntClass(equivalent.getURI());
                    }

                    if (tree != null) {
                        double val = countTree(tree, mappings, type);
                        value = value + val;
                    }
                }
            }
        }

        text = text + "\n" + "SUMA:," + value;

        wholeTreeValue = getWholeTreeValue(type, wholeTreeValue, ont1);

        wholeTreeValue = getWholeTreeValue(type, wholeTreeValue, ont2);

        text = text + "\n" + "MAX WARTOSC:, " + wholeTreeValue;

        if (writeToFile) {
            writeToFile(text, title);
        }
        return value;
    }

    private double getWholeTreeValue(MeasureType type, double wholeTreeValue, OntModel ontModel) {
        List<OntClass> list = OntTools.namedHierarchyRoots(ontModel);
        for( int i = 0; i < list.size(); i++) {

            double val = countTree(list.get(i), null, type);
            wholeTreeValue = wholeTreeValue + val;
        }
        return wholeTreeValue;
    }

    double countTree(OntClass tree, OntModel mappings, MeasureType type) {
        if (type == MeasureType.MAX_DEPTH_POWER) {
            Pair<Integer, Integer> values = valueOf(tree, mappings);

            Integer base = values.getLeft();
            Integer exponent = values.getRight();

            double val = (base != 0 && exponent != 0 ) ? Math.pow(base, exponent) : 0;

            if (mappings != null) {
                text = text + tree.toString() + ",=" + (val != 0 ? (values.getLeft().toString() + "^" + values.getRight().toString()) : "0") + "\n";
            }

            return val;
        } else {

            HashMap map = new HashMap<Integer, Integer>();
            valueOf(tree, mappings, map, 1);

            double sum = 0;
            Iterator it = map.keySet().iterator();


            if (mappings != null) {
                text = text + tree.toString();
            } 

            if (mappings != null) {
                if (it.hasNext()) {
                    text = text + ",= ";
                } else {
                    text = text + ",=0";
                }
            }

            while (it.hasNext()) {
                Integer key = (Integer)it.next();
                Integer value = (Integer) map.get(key);

                sum = sum + (type == MeasureType.DEPTH_POWER ? Math.pow(value, key) : (value * key));

                if (mappings != null) {
                    text = text + value.toString() + (type == MeasureType.DEPTH_POWER ? "^" : "*") + key.toString() + (it.hasNext() ?  " + " : "");
                }
            }

            if (mappings != null) {
                text = text + "\n";
            }

            return sum;
        }
    }

    Pair<Integer, Integer> valueOf(OntClass model, OntModel mappings) {
        Integer value = 0;
        Integer height = 1;

        if (model.hasSubClass()) {
            for (Iterator i = model.listSubClasses(); i.hasNext();) {
                OntClass subModel = (OntClass) i.next();
                if (mappings == null || mappings.getOntClass(subModel.getURI()) == null) {
                    Pair<Integer, Integer> values = valueOf(subModel, mappings);
                    value = value + 1 + values.getLeft();
                    height = Integer.max(height, 1 + values.getRight());
                }
            }
            return new Pair<>(value, height);
        } else {
            return new Pair(0,0);
        }
    }

    void valueOf(OntClass model, OntModel mappings, HashMap<Integer, Integer> map, Integer height) {

        if (model.hasSubClass()) {

            if (map.get(height) == null ) {
                map.put(height, 0);
            }

            for (Iterator i = model.listSubClasses(); i.hasNext();) {
                OntClass subModel = (OntClass) i.next();

                if (mappings == null ||  mappings.getOntClass(subModel.getURI()) == null) {

                    Integer value = map.get(height) + 1;
                    map.put(height, value);
                    valueOf(subModel, mappings, map, height + 1);
                }
            }
        }
    }

    private String rightPad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    private void writeToFile(String string, String documentTitle) {

        try (PrintWriter writer = new PrintWriter(new File(documentTitle+ ".csv"))) {
            writer.write(string);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
