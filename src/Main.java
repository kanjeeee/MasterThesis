import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.BasicConfigurator;

import java.util.Iterator;

public class Main {

    public static void main(String args[]) {

        BasicConfigurator.configure();

        System.out.println("karo jest super");

        ExternalLoader loader = new ExternalLoader();

//        OntModel model1 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/ekaw.owl");
//        OntModel model2 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/sigkdd.owl");



//        OntModel first = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/cmt.owl");
//        OntModel second = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/paperdyne.owl");
//
//        OntModel mappings0 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/mappings-Paperdyne-cmt.owl");
//
//        Calculator calculator = new Calculator();
//
//        System.out.println("Values " + calculator.calculateFirst(first, second, mappings0, true, "miara1_cmt-paperdyne"));
//        System.out.println("Values " + calculator.calculateSecond(first, second, mappings0, true, "miara2_cmt-paperdyne"));

        OntModel first = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/ekaw.owl");
        OntModel second = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/sigkdd.owl");

        OntModel mappings0 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/mappings-EKAW-SIGKDD.owl");

        Calculator calculator = new Calculator();

        System.out.println("Values " + calculator.calculateFirst(first, second, mappings0, true, "miara1_ekaw-sigkdd"));
        System.out.println("Values " + calculator.calculateSecond(first, second, mappings0, true, "miara2_ekaw-sigkdd"));

        int version = 1;
        
        while (version <= 7) {

            OntModel model1 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/cmt" + version +".owl");
            OntModel model2 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/paperdyne.owl");

            OntModel mappings = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/mappings-Paperdyne-cmt_vol" + version +".owl");


            System.out.println("Values " + calculator.calculateFirst(model1, model2, mappings, true, "miara1_cmt-paperdyne" + version));
            System.out.println("Values " + calculator.calculateSecond(model1, model2, mappings, true, "miara2_cmt-paperdyne"+ version));

            version++;
        }
    }
}
