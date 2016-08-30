
package infolis.metaDataTransformer.ontology;

import java.util.List;

/**
 *
 * @author domi
 */
public class EntityLink {
    
    private String fromEntity;
    private String toEntity;
    private List<String> entityRelations;
    private String linkReason;
    private double confidence;

    /**
     * @return the toEntity
     */
    public String getToEntity() {
        return toEntity;
    }

    /**
     * @param toEntity the toEntity to set
     */
    public void setToEntity(String toEntity) {
        this.toEntity = toEntity;
    }

    /**
     * @return the fromEntity
     */
    public String getFromEntity() {
        return fromEntity;
    }

    /**
     * @param fromEntity the fromEntity to set
     */
    public void setFromEntity(String fromEntity) {
        this.fromEntity = fromEntity;
    }

    /**
     * @return the relation
     */
    public List<String> getEntityRelations() {
        return entityRelations;
    }

    /**
     * @param relation the relation to set
     */
    public void setEntityRelations(List<String> entityRelations) {
        this.entityRelations = entityRelations;
    }

    /**
     * @return the linkReason
     */
    public String getLinkReason() {
        return linkReason;
    }

    /**
     * @param linkReason the linkReason to set
     */
    public void setLinkReason(String linkReason) {
        this.linkReason = linkReason;
    }

    /**
     * @return the confidence
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * @param confidence the confidence to set
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    
}
