
package infolis.metaDataTransformer.icpsr;

import java.util.Map;


/**
 *
 * @author domi
 */
public class Zotero {
    
    private Creator [] creators;
    private String title;
    private String language;
    private String abstractNote;
    //? subjects?
    
    @Override
    public String toString() {
        return  getCreators()[0] + "\t" +getTitle() + "\t" + getLanguage() + "\t" + getAbstractNote();
    }

    /**
     * @return the creators
     */
    public Creator[] getCreators() {
        return creators;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return the abstractNote
     */
    public String getAbstractNote() {
        return abstractNote;
    }
    
    
}
