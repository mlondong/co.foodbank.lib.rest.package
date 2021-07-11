package co.com.foodbank.packaged.repository;

import java.util.Collection;
import java.util.Date;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import co.com.foodbank.packaged.exception.PackageNotFoundException;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.pckage.repository
 *         11/07/2021
 */
@Repository
public interface PackageRepository extends MongoRepository<Package, String> {

    @Query("{'date': ?0}")
    Collection<Package> findByDate(Date date) throws PackageNotFoundException;

}
