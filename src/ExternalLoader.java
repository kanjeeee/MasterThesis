import jena.*;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

public class ExternalLoader {

    public OntModel getOntologyModel(String ontoFile) {

        OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        try {
            InputStream in = FileManager.get().open(ontoFile);
            try
            {
                ontoModel.read(in, null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("Ontology " + ontoFile + " loaded.");
        } catch (JenaException je) {
            System.out.println("ERROR" + je.getMessage());
            je.printStackTrace();
        }
        return ontoModel;
    }

}
