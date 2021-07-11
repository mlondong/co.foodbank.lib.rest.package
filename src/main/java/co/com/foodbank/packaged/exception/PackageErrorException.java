package co.com.foodbank.packaged.exception;


/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.exception
 *         11/07/2021
 */
public class PackageErrorException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PackageErrorException(String ex) {
        super(ex);
    }
}
