package co.com.foodbank.packaged.restcontroller;

import java.util.Collection;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import co.com.foodbank.packaged.dto.IPackaged;
import co.com.foodbank.packaged.dto.ItemDTO;
import co.com.foodbank.packaged.dto.PackagedDTO;
import co.com.foodbank.packaged.exception.PackageErrorException;
import co.com.foodbank.packaged.exception.PackageNotFoundException;
import co.com.foodbank.packaged.v1.controller.PackageController;
import co.com.foodbank.packaged.v1.model.Packaged;
import co.com.foodbank.stock.sdk.exception.SDKStockNotFoundException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceIllegalArgumentException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceNotAvailableException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/package")
@Tag(name = "Packaged", description = "the packaging API")
@Validated
public class PackageRestController {



    @Autowired
    private PackageController controller;



    /**
     * Method to Remove Product in Package.
     * 
     * @param dto
     * @param id
     * @return {@code ResponseEntity<IPackaged>}
     * @throws PackageErrorException
     * @throws SDKStockNotFoundException
     * @throws SDKStockServiceIllegalArgumentException
     * @throws SDKStockServiceException
     * @throws JsonProcessingException
     * @throws SDKStockServiceNotAvailableException
     * @throws JsonMappingException
     */
    @Operation(summary = "Remove Product in Package", description = "",
            tags = {"packaged"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Product removed",
                            content = @Content(schema = @Schema(
                                    implementation = Packaged.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input"),
                    @ApiResponse(responseCode = "409",
                            description = "Product already exists")})
    @PutMapping(value = "/removeProduct/id-packaged/{id-package}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IPackaged> removeProduct(
            @PathVariable("id-package") @NotNull @NotBlank String idPackaged,
            @RequestBody @Valid ItemDTO item) throws PackageErrorException,
            JsonMappingException, SDKStockServiceNotAvailableException,
            JsonProcessingException, SDKStockServiceException,
            SDKStockServiceIllegalArgumentException, SDKStockNotFoundException {
        return new ResponseEntity<IPackaged>(
                controller.removeProduct(idPackaged, item), HttpStatus.OK);
    }



    /**
     * Method to Add Product in Package.
     * 
     * @param dto
     * @param id
     * @return {@code ResponseEntity<IPackaged>}
     * @throws PackageErrorException
     * @throws SDKProductNotFoundException
     * @throws SDKProductServiceIllegalArgumentException
     * @throws SDKProductServiceException
     * @throws JsonProcessingException
     * @throws SDKProductServiceNotAvailableException
     * @throws JsonMappingException
     */
    @Operation(summary = "Add Product in Package", description = "",
            tags = {"packaged"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Product added",
                            content = @Content(schema = @Schema(
                                    implementation = Packaged.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input"),
                    @ApiResponse(responseCode = "409",
                            description = "Product already exists")})
    @PutMapping(value = "/addItem/id-packaged/{id-package}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IPackaged> addItem(
            @PathVariable("id-package") @NotNull @NotBlank String idPackaged,
            @RequestBody @Valid ItemDTO item) throws PackageErrorException,
            JsonMappingException, SDKStockServiceNotAvailableException,
            JsonProcessingException, SDKStockServiceException,
            SDKStockServiceIllegalArgumentException, SDKStockNotFoundException {
        return new ResponseEntity<IPackaged>(
                controller.addItem(idPackaged, item), HttpStatus.OK);
    }



    /**
     * Method to create a Package.
     * 
     * @param dto
     * @return {@code ResponseEntity<IPackaged>}
     * @throws PackageErrorException
     */
    @Operation(summary = "Create a new Package", description = "",
            tags = {"packaged"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Packaged created",
                            content = @Content(schema = @Schema(
                                    implementation = Packaged.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input"),
                    @ApiResponse(responseCode = "409",
                            description = "Packaged already exists")})
    @PostMapping(value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IPackaged> create(@RequestBody @Valid PackagedDTO dto)
            throws PackageErrorException {
        return new ResponseEntity<IPackaged>(controller.create(dto),
                HttpStatus.CREATED);
    }



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
    public ResponseEntity<IPackaged> findById(
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
    public ResponseEntity<Collection<IPackaged>> findByDate(
            @PathVariable("date") @DateTimeFormat(
                    pattern = "yyyy-MM-dd") Date date)
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
    @GetMapping(value = "/findAll")
    public ResponseEntity<Collection<IPackaged>> findAll()
            throws PackageNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(controller.findAll());
    }



}
