package co.com.foodbank.packaged.v1.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Interface to implements the state in the package before send to the
 * beneficiary
 * 
 * @author mauricio.londono@gmail.com co.com.foodbank.packaged.v1.model
 *         6/08/2021
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "state")
@JsonSubTypes({@Type(value = OpenPackaged.class, name = "OpenPackaged"),
        @Type(value = ClosePackaged.class, name = "ClosePackaged")})
public interface IStatePackaged {

    /**
     * Method to open when try to add items in package.
     * 
     * @param packaged
     */
    void open(Packaged packaged);

    /**
     * Method to close when try toa dd items in package.
     * 
     * @param packaged
     */
    void close(Packaged packaged);


}
