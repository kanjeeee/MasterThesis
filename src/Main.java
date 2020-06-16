import org.apache.jena.ontology.OntModel;
import org.apache.log4j.BasicConfigurator;


public class Main {

    public static void main(String args[]) {

        BasicConfigurator.configure();
        ExternalLoader loader = new ExternalLoader();

        Calculator calculator = new Calculator();


        OntModel first = loader.getOntologyModel("../paperdyne-sofsem/paperdyne.owl");
        OntModel second = loader.getOntologyModel("../paperdyne-sofsem/sofsem.owl");


        OntModel mappings0 = loader.getOntologyModel("../paperdyne-sofsem/mappings.owl");

        System.out.println("Value " + calculator.calculateDepthSum(first, second, mappings0, true, "miara3_paperdyne-sofsem"));

    }
}
