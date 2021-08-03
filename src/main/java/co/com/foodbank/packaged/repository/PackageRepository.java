package co.com.foodbank.packaged.repository;

import java.util.Collection;
import java.util.Date;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import co.com.foodbank.packaged.exception.PackageNotFoundException;
import co.com.foodbank.packaged.v1.model.Packaged;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.packaged.repository
 *         11/07/2021
 */
@Repository
public interface PackageRepository extends MongoRepository<Packaged, String> {

    @Query("{'datePackage': { '$lt' : ?0 }}")
    Collection<Packaged> findByDate(Date date) throws PackageNotFoundException;

}
