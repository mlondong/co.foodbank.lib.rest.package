package co.com.foodbank.packaged.v1.controller;

import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import co.com.foodbank.packaged.dto.IPackaged;
import co.com.foodbank.packaged.exception.PackageNotFoundException;
import co.com.foodbank.packaged.service.PackageService;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.v1.controller
 *         11/07/2021
 */
@Controller
public class PackageController {


    @Autowired
    private PackageService service;


    /**
     * Method to find a Package by Id.
     * 
     * @param id
     * @return {@code IPackage}
     */
    public IPackaged findById(@NotBlank @NotNull String id)
            throws PackageNotFoundException {
        // TODO Auto-generated method stub
        return service.findById(id);
    }

    /**
     * Method to find a Package by date.
     * 
     * @param date
     * @return {@code Collection<IPackage>}
     */
    public Collection<IPackaged> findByDate(@NotBlank @NotNull Date date)
            throws PackageNotFoundException {
        // TODO Auto-generated method stub
        return service.findByDate(date);
    }


    /**
     * Method find all packages.
     * 
     * @return {@code Collection<IPackage>}
     */
    public Collection<IPackaged> findAll() {
        // TODO Auto-generated method stub
        return service.findAll();
    }

}
