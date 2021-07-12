package co.com.foodbank.packaged.v1.model;

import java.util.Collection;
import java.util.Date;
import co.com.foodbank.product.dto.IProduct;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.v1.model 11/07/2021
 */
public interface IPackaged {

    String getId();

    Collection<IProduct> getProduct();

    Long getUnits();

    Date getDatePackage();

}
