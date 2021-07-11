package co.com.foodbank.pckage.exception;


/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.exception
 *         11/07/2021
 */
public class PackageNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PackageNotFoundException(String id) {
        super(id);
    }
}
