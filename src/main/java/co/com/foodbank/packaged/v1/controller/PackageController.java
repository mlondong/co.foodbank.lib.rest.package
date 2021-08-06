package co.com.foodbank.packaged.v1.controller;

import java.util.Collection;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import co.com.foodbank.packaged.dto.IPackaged;
import co.com.foodbank.packaged.dto.ItemDTO;
import co.com.foodbank.packaged.dto.PackagedDTO;
import co.com.foodbank.packaged.exception.PackageErrorException;
import co.com.foodbank.packaged.exception.PackageNotFoundException;
import co.com.foodbank.packaged.service.PackageService;
import co.com.foodbank.stock.sdk.exception.SDKStockNotFoundException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceIllegalArgumentException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceNotAvailableException;

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



    /**
     * Method to create Packaged
     * 
     * @param dto
     * @return {@code IPackaged}
     */
    public IPackaged create(PackagedDTO dto) throws PackageErrorException {
        return service.create(dto);
    }


    /**
     * Method to update state in Packaged
     * 
     * @param option
     * @param id
     * @return {@code IPackaged}
     */
    public IPackaged updateState(String option, @NotNull @NotBlank String id)
            throws PackageErrorException {
        return service.update(option, id);
    }


    /**
     * Method to add products in package.
     * 
     * @param iProduct
     * @param idPackaged
     * @return {@code IPackaged}
     * @throws SDKProductNotFoundException
     * @throws SDKProductServiceIllegalArgumentException
     * @throws SDKProductServiceException
     * @throws JsonProcessingException
     * @throws SDKProductServiceNotAvailableException
     * @throws JsonMappingException
     * @throws PackageErrorException
     */
    public IPackaged addItem(@NotNull @NotBlank String idPackaged, ItemDTO item)
            throws JsonMappingException, SDKStockServiceNotAvailableException,
            JsonProcessingException, SDKStockServiceException,
            SDKStockServiceIllegalArgumentException, SDKStockNotFoundException,
            PackageErrorException {
        return service.addItem(idPackaged, item);
    }



    /**
     * Method to remove product.
     * 
     * @param idPackaged
     * @param item
     * @return {@code IPackaged}
     * @throws PackageErrorException
     * @throws SDKStockNotFoundException
     * @throws SDKStockServiceIllegalArgumentException
     * @throws SDKStockServiceException
     * @throws JsonProcessingException
     * @throws SDKStockServiceNotAvailableException
     * @throws JsonMappingException
     */
    public IPackaged removeProduct(@NotNull @NotBlank String idPackaged,
            @NotNull @NotBlank @Valid ItemDTO item) throws JsonMappingException,
            SDKStockServiceNotAvailableException, JsonProcessingException,
            SDKStockServiceException, SDKStockServiceIllegalArgumentException,
            SDKStockNotFoundException, PackageErrorException {
        return service.removeProduct(item, idPackaged);
    }

}
