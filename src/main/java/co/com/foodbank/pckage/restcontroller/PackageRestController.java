package co.com.foodbank.pckage.restcontroller;

import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.com.foodbank.pckage.exception.PackageNotFoundException;
import co.com.foodbank.pckage.v1.controller.PackageController;
import co.com.foodbank.pckage.v1.model.IPackage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/package")
@Tag(name = "Package", description = "the packaging API")
@Validated
public class PackageRestController {



    @Autowired
    private PackageController controller;



    /**
     * Method find Package by Id.
     * 
     * @param id
     * @return {@code ResponseEntity<IPackage>}
     * @throws PackageNotFoundException
     */
    @Operation(summary = "Find Package by Id.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Package found.",
                            content = {
                                    @Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Service not available.",
                            content = @Content),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request.", content = @Content)})
    @GetMapping(value = "/findById/{id-package}")
    public ResponseEntity<IPackage> findById(
            @PathVariable("id-package") @NotBlank @NotNull String id)
            throws PackageNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.findById(id));
    }



    /**
     * Method to find Packages by Date.
     * 
     * @param date
     * @return {@code ResponseEntity<Collection<IPackage>>}
     * @throws PackageNotFoundException
     */
    @Operation(summary = "Find Package by date.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Package found.",
                            content = {
                                    @Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Service not available.",
                            content = @Content),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request.", content = @Content)})
    @GetMapping(value = "/findByDate/{date}")
    public ResponseEntity<Collection<IPackage>> findByDate(
            @PathVariable("date") @NotBlank @NotNull Date date)
            throws PackageNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.findByDate(date));
    }



    /**
     * Method find all Package.
     * 
     * @param id
     * @return {@code ResponseEntity<IPackage>}
     * @throws PackageNotFoundException
     */
    @Operation(summary = "Find all Packages.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Package found.",
                            content = {
                                    @Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Service not available.",
                            content = @Content),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request.", content = @Content)})
    @GetMapping(value = "/findById/{id-package}")
    public ResponseEntity<Collection<IPackage>> findAll()
            throws PackageNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(controller.findAll());
    }


}
