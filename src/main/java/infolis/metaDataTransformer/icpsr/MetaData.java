
package infolis.metaDataTransformer.icpsr;

/**
 *
 * @author domi
 */
public class MetaData {
    
    private Zotero[] zotero;
    private CrossRefWorks crossRefWorks;
    
    @Override
    public String toString() {
        return getZotero()[0].toString() + "\t" + getCrossRefWorks().toString();
    }

    /**
     * @return the zotero
     */
    public Zotero[] getZotero() {
        return zotero;
    }

    /**
     * @return the crossRefWorks
     */
    public CrossRefWorks getCrossRefWorks() {
        return crossRefWorks;
    }
    
}
