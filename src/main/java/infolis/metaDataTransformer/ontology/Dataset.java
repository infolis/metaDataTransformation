package infolis.metaDataTransformer.ontology;

import java.util.List;
import java.util.Set;

/**
 *
 * @author domi
 */
public class Dataset {
    
    private String name;
    private Set<String> numericInfo;
    private String identifier;
    private Set<String> spatial;
    private double reliability;
    private Set<String> tags;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the number
     */
    public Set<String> getNumericInfo() {
        return numericInfo;
    }

    /**
     * @param number the number to set
     */
    public void setNumericInfo(Set<String> numericInfo) {
        this.numericInfo = numericInfo;
    }

    /**
     * @return the spatial
     */
    public Set<String> getSpatial() {
        return spatial;
    }

    /**
     * @param spatial the spatial to set
     */
    public void setSpatial(Set<String> spatial) {
        this.spatial = spatial;
    }

    /**
     * @return the reliability
     */
    public double getReliability() {
        return reliability;
    }

    /**
     * @param reliability the reliability to set
     */
    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    /**
     * @return the tags
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    
}
