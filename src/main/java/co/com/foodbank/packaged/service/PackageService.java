package co.com.foodbank.packaged.service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.com.foodbank.packaged.dto.IPackaged;
import co.com.foodbank.packaged.exception.PackageNotFoundException;
import co.com.foodbank.packaged.repository.PackageRepository;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.service 11/07/2021
 */
@Service
@Transactional
public class PackageService {

    @Autowired
    private PackageRepository repository;

    @Autowired
    private ModelMapper modelMapper;



    /**
     * Method to find Package by id.
     * 
     * @param id
     * @return {@code IPackage}
     * @throws PackageNotFoundException
     */
    public IPackaged findById(String id) throws PackageNotFoundException {
        return modelMapper.map(repository.findById(id), IPackaged.class);
    }



    /**
     * method to find Package by date.
     * 
     * @param date
     * @return {@code Collection<IPackage>}
     */
    public Collection<IPackaged> findByDate(Date date)
            throws PackageNotFoundException {
        return repository.findByDate(date).stream()
                .map(d -> modelMapper.map(d, IPackaged.class))
                .collect(Collectors.toList());
    }


    /**
     * Method find all packages.
     * 
     * @return {@code Collection<IPackage>}
     */
    public Collection<IPackaged> findAll() throws PackageNotFoundException {
        return repository.findAll().stream()
                .map(d -> modelMapper.map(d, IPackaged.class))
                .collect(Collectors.toList());
    }

}
