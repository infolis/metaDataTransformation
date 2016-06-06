package infolis.metaDataTransformer.icpsr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import infolis.metaDataTransformer.output.OutputWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

/**
 *
 * @author domi
 */
public class ICPSRTransformer {

    public static void main(String args[]) throws FileNotFoundException {

        String inputPath = args[0];
        String outputPath = args[1];
        
        File inDir = new File(inputPath);
        for (File f : inDir.listFiles()) {

            BufferedReader read = new BufferedReader(new FileReader(f));
            
            try {
                Gson gson = new GsonBuilder().create();
                Publication p = gson.fromJson(read, Publication.class);

                String title = p.getMetadata().getZotero()[0].getTitle();
                String identifier = p.getDoi();
                String url = p.getUrl();
                String abstractDesc = p.getMetadata().getZotero()[0].getAbstractNote();
                String language = p.getMetadata().getZotero()[0].getLanguage();
                Creator[] authors = p.getMetadata().getZotero()[0].getCreators();
                String[] authorsStrings = new String[authors.length];
                for (int i = 0; i < authors.length; i++) {
                    authorsStrings[i] = authors[i].getFirstName() + " " + authors[i].getLastName();
                }
                String[] subjects = new String[0];
                if(p.getMetadata().getCrossRefWorks()!= null && p.getMetadata().getCrossRefWorks().getSubject() != null) {
                    subjects = p.getMetadata().getCrossRefWorks().getSubject();
                }

                OutputWriter write = new OutputWriter(language, abstractDesc, title, identifier, url, Arrays.asList(authorsStrings), Arrays.asList(subjects), outputPath, f.getName());
                write.writeOutput();
            } catch (Exception e) {
                System.out.println(f.getName());
            }

        }
    }

}
