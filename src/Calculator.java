import org.apache.jena.atlas.lib.Pair;
import org.apache.jena.atlas.lib.tuple.Tuple;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.tdb.store.Hash;
import org.apache.jena.util.iterator.ExtendedIterator;

import javax.jnlp.IntegrationService;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

enum Type {

    first, second
}

public class Calculator {

    String text = "";


    double calculateFirst(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title) {
        return calculate(ont1, ont2, mappings, writeToFile, title, Type.first);
    }

    double calculateSecond(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title) {
        return calculate(ont1, ont2, mappings, writeToFile, title, Type.second);
    }

    double calculate(OntModel ont1, OntModel ont2, OntModel mappings, Boolean writeToFile, String title, Type type) {

        double value = 0;
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
//                    System.out.print(rightPad(iterator.toString() + " ", 3));
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
        if (writeToFile) {
            writeToFile(text, title);
        }
        return value;
    }

    double countTree(OntClass tree, OntModel mappings, Type type) {
        if (type == Type.first) {
            Pair<Integer, Integer> values = valueOf(tree, mappings);

            Integer a = values.getLeft();
            Integer b = values.getRight();

            double val = (a != 0 && b != 0 ) ? Math.pow(a, b) : 0;
            System.out.println(rightPad("Class: " + tree, 50) + " value first: " + rightPad(values.getLeft().toString(), 3) + " second: " + rightPad(values.getRight().toString(), 3)+ "value: "+val);

            text = text + tree.toString() + ",=" + (val != 0 ? (values.getLeft().toString() + "^" + values.getRight().toString()) : "0") + "\n";

            return val;
        } else {

            HashMap map = new HashMap<Integer, Integer>();
            valueOf(tree, mappings, map, 1);

            double aa = 0;
            Iterator it = map.keySet().iterator();

            text = text + tree.toString();
            if (it.hasNext()) {
                text = text + ",= ";
            } else {
                text = text + ",=0";
            }

            while (it.hasNext()) {
                Integer key = (Integer)it.next();
                Integer value = (Integer) map.get(key);

                System.out.println("key:" + key + " value: " + value + " łącznie: " + Math.pow(value, key));

                aa = aa + Math.pow(value, key);
                text = text + value.toString() + "^" + key.toString() + (it.hasNext() ?  " + " : "");
            }

            text = text + "\n";

            return aa;
        }
    }

    Pair<Integer, Integer> valueOf(OntClass model, OntModel mappings) {
        Integer value = 0;
        Integer height = 1;

        if (model.hasSubClass()) {
            for (Iterator i = model.listSubClasses(); i.hasNext();) {
                OntClass subModel = (OntClass) i.next();
                if (mappings.getOntClass(subModel.getURI()) == null) {
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

//            Integer height = map.keySet().size() + 1;

            if (map.get(height) == null ) {
                map.put(height, 0);
            }

            for (Iterator i = model.listSubClasses(); i.hasNext();) {
                OntClass subModel = (OntClass) i.next();

//                System.out.println("SUBCLASS" + subModel);
                if (mappings.getOntClass(subModel.getURI()) == null) {

                    Integer value = map.get(height) + 1;

//                    System.out.println("dodajemy na wysokości: " + height + " wartość: " + value);

                    map.put(height, value);
//                    System.out.println("KARO JEST SUPER " + subModel + " height: " + height + " values: " + value);

                    valueOf(subModel, mappings, map, height + 1);

//                    Pair<Integer, Integer> values = valueOf(subModel, mappings);
//                    value = value + 1 + values.getLeft();
//                    height = Integer.max(height, 1 + values.getRight());
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
