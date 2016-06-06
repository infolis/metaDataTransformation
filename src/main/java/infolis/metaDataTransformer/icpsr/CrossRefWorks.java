
package infolis.metaDataTransformer.icpsr;

/**
 *
 * @author domi
 */
public class CrossRefWorks {
    
    private String[] subject;
    
    @Override
    public String toString() {        
        return getSubject()[0] + "\t" + getSubject()[1];
    }

    /**
     * @return the subject
     */
    public String[] getSubject() {
        return subject;
    }
    
}
