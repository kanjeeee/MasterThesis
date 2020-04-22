import org.apache.jena.atlas.lib.Pair;
import org.apache.jena.atlas.lib.tuple.Tuple;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntTools;
import org.apache.jena.tdb.store.Hash;
import org.apache.jena.util.iterator.ExtendedIterator;

import javax.jnlp.IntegrationService;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

enum Type {

    first, second, third
}

public class Calculator {

    String text = "";


    double calculateFirst(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title) {
        return calculate(ont1, ont2, mappings, writeToFile, title, Type.first);
    }

    double calculateSecond(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title) {
        return calculate(ont1, ont2, mappings, writeToFile, title, Type.second);
    }

    double calculateThird(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title) {
        return calculate(ont1, ont2, mappings, writeToFile, title, Type.third);
    }

    double calculate(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title, Type type) {

        double value = 0;
        double wholeTreeValue = 0;
        Integer iterator = 0;

        text = "Klasa,wartosc\n";

        ExtendedIterator classes = mappings.listClasses();
        while (classes.hasNext()) {
            OntClass Ontclass = (OntClass) classes.next();
            if (Ontclass.getEquivalentClass() != null) {

                iterator++;
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
                        System.out.print(rightPad(iterator.toString() + " ", 3));
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

        System.out.println("MAX NA DRZEWIE " + wholeTreeValue);

        if (writeToFile) {
            writeToFile(text, title);
        }
        return value;
    }

    private double getWholeTreeValue(Type type, double wholeTreeValue, OntModel ontModel) {
        List<OntClass> list = OntTools.namedHierarchyRoots(ontModel);
        for( int i = 0; i < list.size(); i++) {

            double val = countTree(list.get(i), null, type);
            wholeTreeValue = wholeTreeValue + val;
//            System.out.println(i + ". " +list.get(i));
        }
        return wholeTreeValue;
    }

    double countTree(OntClass tree, OntModel mappings, Type type) {
        if (type == Type.first) {
            Pair<Integer, Integer> values = valueOf(tree, mappings);

            Integer a = values.getLeft();
            Integer b = values.getRight();

            double val = (a != 0 && b != 0 ) ? Math.pow(a, b) : 0;
//            System.out.println(rightPad("Class: " + tree, 50) + " value first: " + rightPad(values.getLeft().toString(), 3) + " second: " + rightPad(values.getRight().toString(), 3)+ "value: "+val);

            if (mappings != null) {
                text = text + tree.toString() + ",=" + (val != 0 ? (values.getLeft().toString() + "^" + values.getRight().toString()) : "0") + "\n";
            }

            return val;
        } else {

            HashMap map = new HashMap<Integer, Integer>();
            valueOf(tree, mappings, map, 1);

            double aa = 0;
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

//                System.out.println("key:" + key + " value: " + value + " łącznie: " + (type == Type.second ? Math.pow(value, key) : (value * key)));

                aa = aa + (type == Type.second ? Math.pow(value, key) : (value * key));

                if (mappings != null) {
                    text = text + value.toString() + (type == Type.second ? "^" : "*") + key.toString() + (it.hasNext() ?  " + " : "");
                }
            }

            if (mappings != null) {
                text = text + "\n";
            }

            return aa;
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
            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
