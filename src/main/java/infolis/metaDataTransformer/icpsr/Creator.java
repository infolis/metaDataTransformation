
package infolis.metaDataTransformer.icpsr;

/**
 *
 * @author domi
 */
public class Creator {
    
    private String firstName;
    private String lastName;
    private String creatorType;
    
    @Override
    public String toString() {
        return  getFirstName() + "\t" +getLastName();
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the creatorType
     */
    public String getCreatorType() {
        return creatorType;
    }
}
