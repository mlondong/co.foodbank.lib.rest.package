package co.com.foodbank.packaged.v1.model;

import java.util.Collection;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import co.com.foodbank.packaged.dto.IPackaged;
import co.com.foodbank.packaged.item.Item;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.v1.model 11/07/2021
 */
@Document(collection = "Package")
public class Packaged implements IPackaged {

    @Id
    private String id;
    private Collection<Item> item;
    private Long units;
    private Date datePackage;
    private IStatePackaged state;

    /**
     * Default constructor.
     */
    public Packaged() {}


    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Collection<Item> getItem() {
        return item;
    }

    public void setItem(Collection<Item> item) {
        this.item = item;
    }

    @Override
    public Long getUnits() {
        return units;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

    @Override
    public Date getDatePackage() {
        return datePackage;
    }

    public void setDatePackage(Date datePackage) {
        this.datePackage = datePackage;
    }

    public IStatePackaged getState() {
        return state;
    }

    public void setState(IStatePackaged state) {
        this.state = state;
    }



}
