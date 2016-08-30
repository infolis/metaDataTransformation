
package infolis.metaDataTransformer.ontology;

import java.util.Map;

/**
 *
 * @author domi
 */
public class Record {
    
    private Map entity;
    private Map entityLink;

    /**
     * @return the entity
     */
    public Map getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Map entity) {
        this.entity = entity;
    }

    /**
     * @return the entityLink
     */
    public Map getEntityLink() {
        return entityLink;
    }

    /**
     * @param entityLink the entityLink to set
     */
    public void setEntityLink(Map entityLink) {
        this.entityLink = entityLink;
    }

    
}
