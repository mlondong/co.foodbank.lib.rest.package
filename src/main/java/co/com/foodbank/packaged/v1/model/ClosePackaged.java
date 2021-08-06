package co.com.foodbank.packaged.v1.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * Class to close Packaged when try to add items in packaged.
 * 
 * @author mauricio.londono@gmail.com co.com.foodbank.packaged.v1.model
 *         6/08/2021
 */
@JsonAutoDetect(fieldVisibility = Visibility.DEFAULT)
public class ClosePackaged implements IStatePackaged, Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ClosePackaged() {}


    @Override
    public void open(Packaged pack) {}

    @Override
    public void close(Packaged pack) {
        pack.setState(this);
    }



}
