package co.com.foodbank.packaged.v1.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * Class to handle state Open in Packaged to add items
 * 
 * @author mauricio.londono@gmail.com co.com.foodbank.packaged.v1.model
 *         6/08/2021
 */
@JsonAutoDetect(fieldVisibility = Visibility.DEFAULT)
public class OpenPackaged implements IStatePackaged, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    /**
     * Default constructor.
     */
    public OpenPackaged() {}

    @Override
    public void open(Packaged pack) {
        pack.setState(this);
    }

    @Override
    public void close(Packaged pack) {}



}
