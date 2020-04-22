import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.BasicConfigurator;

import java.util.Iterator;

public class Main {

    public static void main(String args[]) {

        BasicConfigurator.configure();
        ExternalLoader loader = new ExternalLoader();

        Calculator calculator = new Calculator();


        //ZMIANY Paperdyne cmt
//
//        OntModel first = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/cmt.owl");
//        OntModel second = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/paperdyne.owl");
//
//        OntModel mappings0 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/mappings-Paperdyne-cmt.owl");

//        System.out.println("Values " + calculator.calculateFirst(first, second, mappings0, true, "miara1_cmt-paperdyne"));
//        System.out.println("Values " + calculator.calculateSecond(first, second, mappings0, true, "miara2_cmt-paperdyne"));
//        System.out.println("Values " + calculator.calculateThird(first, second, mappings0, true, "miara3_cmt-paperdyne"));

        OntModel first14 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/cmt14.owl");
        OntModel second14 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/paperdyne14.owl");

        OntModel mappings14 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/mappings-Paperdyne-cmt_vol14.owl");

        System.out.println("Values " + calculator.calculateFirst(first14, second14, mappings14, true, "miara1_cmt-paperdyne14"));
        System.out.println("Values " + calculator.calculateSecond(first14, second14, mappings14, true, "miara2_cmt-paperdyne14"));
        System.out.println("Values " + calculator.calculateThird(first14, second14, mappings14, true, "miara3_cmt-paperdyne14"));

        //ZMIANY EKAW SIGKDD

//        OntModel first = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/ekaw.owl");
//        OntModel second = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/sigkdd.owl");
//
//        OntModel mappings0 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/mappings-EKAW-SIGKDD.owl");
//
//        Calculator calculator = new Calculator();
//
//        System.out.println("Values " + calculator.calculateFirst(first, second, mappings0, true, "miara1_EKAW-SIGKDD"));
//        System.out.println("Values " + calculator.calculateSecond(first, second, mappings0, true, "miara2_EKAW-SIGKDD"));
//        System.out.println("Values " + calculator.calculateThird(first, second, mappings0, true, "miara3_EKAW-SIGKDD"));

//        OntModel first14 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/ekaw14.owl");
//        OntModel second14 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/sigkdd14.owl");
//
//        OntModel mappings14 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/mappings-EKAW-SIGKDD_vol14.owl");
//
//
//        System.out.println("Values " + calculator.calculateFirst(first14, second14, mappings14, true, "miara1_EKAW-SIGKDD14"));
//        System.out.println("Values " + calculator.calculateSecond(first14, second14, mappings14, true, "miara2_EKAW-SIGKDD14"));
//        System.out.println("Values " + calculator.calculateThird(first14, second14, mappings14, true, "miara3_EKAW-SIGKDD14"));


        //ZMIANY Conference confOf

//        OntModel first = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/Conference.owl");
//        OntModel second = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/confOf.owl");
//
//        OntModel mappings0 = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/mappings-Conference-confOf.owl");
//
//        Calculator calculator = new Calculator();
//
//        System.out.println("Values " + calculator.calculateFirst(first, second, mappings0, true, "miara1_Conference-confOf"));
//        System.out.println("Values " + calculator.calculateSecond(first, second, mappings0, true, "miara2_Conference-confOf"));
//        System.out.println("Values " + calculator.calculateThird(first, second, mappings0, true, "miara3_Conference-confOf"));
//
//
//        OntModel first14 = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/Conference14.owl");
//        OntModel second14 = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/confOf14.owl");
//
//        OntModel mappings14 = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/mappings-Conference-confOf_vol14.owl");
//
//
//        System.out.println("Values " + calculator.calculateFirst(first14, second14, mappings14, true, "miara1_Conference-confOf14"));
//        System.out.println("Values " + calculator.calculateSecond(first14, second14, mappings14, true, "miara2_Conference-confOf14"));
//        System.out.println("Values " + calculator.calculateThird(first14, second14, mappings14, true, "miara3_Conference-confOf14"));

        int version = 12;

        while (version <= 12) {

//            OntModel model1 = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/Conference" + version +".owl");
//            OntModel model2 = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/confOf.owl");
//
//            OntModel mappings = loader.getOntologyModel("/Users/karolinakania/Desktop/Conference - Conf Of/mappings-Conference-confOf_vol" + version +".owl");
//
//
//            System.out.println("Values " + calculator.calculateFirst(model1, model2, mappings, true, "miara1_Conference-confOf" + version));
//            System.out.println("Values " + calculator.calculateSecond(model1, model2, mappings, true, "miara2_Conference-confOf"+ version));
//            System.out.println("Values " + calculator.calculateThird(model1, model2, mappings, true, "miara3_Conference-confOf"+ version));

            //ekaw sigkdd

//            OntModel model1 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/ekaw" + version +".owl");
//            OntModel model2 = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/sigkdd.owl");
//
//            OntModel mappings = loader.getOntologyModel("/Users/karolinakania/Desktop/EKAW - SIGKDD/mappings-EKAW-SIGKDD_vol" + version +".owl");
//
//
//            System.out.println("Values " + calculator.calculateFirst(model1, model2, mappings, true, "miara1_EKAW-SIGKDD" + version));
//            System.out.println("Values " + calculator.calculateSecond(model1, model2, mappings, true, "miara2_EKAW-SIGKDD"+ version));
//            System.out.println("Values " + calculator.calculateThird(model1, model2, mappings, true, "miara3_EKAW-SIGKDD"+ version));

            //Paperdyne cmt
//
//            OntModel model1 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/cmt" + version +".owl");
//            OntModel model2 = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/paperdyne.owl");
//
//            OntModel mappings = loader.getOntologyModel("/Users/karolinakania/Desktop/Paperdyne - cmt/mappings-Paperdyne-cmt_vol" + version +".owl");
//
//
//            System.out.println("Values " + calculator.calculateFirst(model1, model2, mappings, true, "miara1_cmt-paperdyne" + version));
//            System.out.println("Values " + calculator.calculateSecond(model1, model2, mappings, true, "miara2_cmt-paperdyne"+ version));
//            System.out.println("Values " + calculator.calculateThird(model1, model2, mappings, true, "miara3_cmt-paperdyne"+ version));

            version++;
        }
    }
}
