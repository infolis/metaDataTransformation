
package infolis.metaDataTransformer.icpsr;

/**
 *
 * @author domi
 */
public class Publication {
    
    private String url;
    private String doi;
    private MetaData metadata;
    
    @Override
    public String toString() {
        return getUrl() + "\t" + getDoi() + "\t" + getMetadata();
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the doi
     */
    public String getDoi() {
        return doi;
    }

    /**
     * @return the metadata
     */
    public MetaData getMetadata() {
        return metadata;
    }

    
}
