package co.com.foodbank.packaged.v1.model;

import java.util.Collection;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import co.com.foodbank.product.dto.IProduct;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.v1.model 11/07/2021
 */

@Document(collection = "Package")
public class Package implements IPackage {

    @Id
    private String id;
    private Collection<IProduct> product;
    private Long units;
    private Date datePackage;

    /**
     * Default constructor.
     */
    public Package() {}


    public void setId(String id) {
        this.id = id;
    }

    public void setProduct(Collection<IProduct> product) {
        this.product = product;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

    public void setDatePackage(Date datePackage) {
        this.datePackage = datePackage;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<IProduct> getProduct() {
        return product;
    }

    @Override
    public Long getUnits() {
        return units;
    }

    @Override
    public Date getDatePackage() {
        return datePackage;
    }



}
