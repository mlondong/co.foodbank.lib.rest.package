package co.com.foodbank.packaged.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import co.com.foodbank.contribution.dto.ContributionPK;
import co.com.foodbank.packaged.dto.IPackaged;
import co.com.foodbank.packaged.dto.ItemDTO;
import co.com.foodbank.packaged.dto.PackagedDTO;
import co.com.foodbank.packaged.exception.PackageErrorException;
import co.com.foodbank.packaged.exception.PackageNotFoundException;
import co.com.foodbank.packaged.item.Item;
import co.com.foodbank.packaged.repository.PackageRepository;
import co.com.foodbank.packaged.v1.model.Packaged;
import co.com.foodbank.product.dto.ProductPK;
import co.com.foodbank.stock.dto.StockDTO;
import co.com.foodbank.stock.sdk.exception.SDKStockNotFoundException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceIllegalArgumentException;
import co.com.foodbank.stock.sdk.exception.SDKStockServiceNotAvailableException;
import co.com.foodbank.stock.sdk.model.ResponseStockData;
import co.com.foodbank.stock.sdk.service.SDKStockService;

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

    @Autowired
    @Qualifier("sdkStock")
    private SDKStockService sdkStock;


    private final static String PRODUCT_NOT_FOUND = "Product Not Found";

    private final static String PRODUCT_WITHOUT_EXISTENCE =
            "Product withOut existence.";

    private final static String STOCK_WARNING = "Not All Stocks are udated.";

    private final static String PRODUCT_REPETEAD_IN_STOCK =
            "You have some products added, please remove and try to add another quantities.";


    private Long requiredValue = 0L;

    private Long currentValue = 0L;

    /**
     * Method to find Package by id.
     * 
     * @param id
     * @return {@code IPackage}
     * @throws PackageNotFoundException
     */
    public IPackaged findById(String id) throws PackageNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new PackageNotFoundException(id));
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
     * @throws PackageNotFoundException
     * @return {@code Collection<IPackage>}
     */
    public Collection<IPackaged> findAll() throws PackageNotFoundException {
        return repository.findAll().stream()
                .map(d -> modelMapper.map(d, IPackaged.class))
                .collect(Collectors.toList());
    }


    /**
     * Method to create a Packaged
     * 
     * @param dto
     * @throws PackageErrorException
     * @return {@code IPackaged}
     */
    public IPackaged create(PackagedDTO dto) throws PackageErrorException {

        Packaged packaged = modelMapper.map(dto, Packaged.class);

        return repository.save(packaged);
    }


    /**
     * Method to crate a Packaged
     * 
     * @param id
     * @param dto
     * @throws PackageErrorException
     * @return {@code IPackaged}
     */
    public IPackaged update(PackagedDTO dto, String id)
            throws PackageErrorException {

        Packaged result = modelMapper.map(this.findById(id), Packaged.class);

        if (Objects.isNull(result)) {
            throw new PackageErrorException(id);
        }

        result.setDatePackage(dto.getDatePackage());
        result.setUnits(Long.valueOf(dto.getUnits()));

        return repository.save(result);
    }



    /**
     * Method to add products in package.
     * 
     * @param idProduct
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
    public IPackaged addItem(String idPackaged, ItemDTO item)
            throws JsonMappingException, SDKStockServiceNotAvailableException,
            JsonProcessingException, SDKStockServiceException,
            SDKStockServiceIllegalArgumentException, SDKStockNotFoundException,
            PackageErrorException {


        Map<String, Item> currentItems = checkCurrentPackage(idPackaged, item);


        requiredValue = Long.valueOf(item.getQuantity());

        Map<String, Item> newItems = getItemsInProduct(item);
        currentItems.putAll(newItems);

        Collection<Item> onlyItems = currentItems.values();

        IPackaged result = saveAllItems(onlyItems, idPackaged);
        Collection<ResponseStockData> updated = updateStock(newItems);

        if (updated.size() != newItems.size()) {
            String er = newItems.keySet().stream()
                    .collect(Collectors.joining(" ; "));
            throw new PackageErrorException(STOCK_WARNING + er);
        }

        return result;
    }



    /**
     * Method to check Packaged and get all items in a Map.
     * 
     * @param idPackaged
     * @param item
     * @return {@code Map<String, Item>}
     * @throws PackageErrorException
     */
    private Map<String, Item> checkCurrentPackage(String idPackaged,
            ItemDTO item) throws PackageErrorException {

        IPackaged foundPackage = this.findById(idPackaged);

        Collection<Item> foundItems = foundPackage.getProduct();
        Map<String, Item> current = new HashMap<>();
        foundItems.stream().forEach(d -> {
            current.put(idPackaged, d);
        });


        Collection<Item> repeteadItems = foundItems.stream()
                .filter(d -> d.getProduct().getId()
                        .equals(item.getProduct().getProduct()))
                .collect(Collectors.toList());
        if (!repeteadItems.isEmpty()) {
            throw new PackageErrorException(showProduct(repeteadItems) + " "
                    + PRODUCT_REPETEAD_IN_STOCK);
        }


        return current;
    }



    private String showProduct(Collection<Item> repeteadItems) {
        return repeteadItems.stream().map(d -> d.getProduct().getName())
                .findFirst().get();
    }



    /**
     * Update Stock before save all Items.
     * 
     * @param valuesToUpdate
     * @return
     * @return
     */
    private Collection<ResponseStockData> updateStock(
            Map<String, Item> valuesToUpdate) {

        Collection<ResponseStockData> updated = new ArrayList<>();

        valuesToUpdate.forEach((key, value) -> {
            try {
                ResponseStockData data = sdkStock.findStockById(key);
                ResponseStockData stock = sdkStock.updateStock(key,
                        buildStockDTO(value, data.getQuantity()));
                updated.add(stock);
            } catch (SDKStockServiceNotAvailableException
                    | SDKStockNotFoundException | SDKStockServiceException
                    | SDKStockServiceIllegalArgumentException e) {
                e.printStackTrace();
            }
        });

        return updated;
    }



    private StockDTO buildStockDTO(Item data, Long unitsDB) {
        Long units = unitsDB - data.getUnits();

        StockDTO dto = new StockDTO();
        dto.setQuantity(String.valueOf(units));
        dto.setContribution(
                generateContributionPK(data.getContribution().getId()));
        dto.setDateStock(new Date());
        dto.setProduct(generateProductPK(data.getProduct().getId()));
        return dto;
    }



    private ProductPK generateProductPK(String id) {
        ProductPK pk = new ProductPK();
        pk.setProduct(id);
        return pk;
    }



    private ContributionPK generateContributionPK(String id) {
        ContributionPK pk = new ContributionPK();
        pk.setContribution(id);
        return pk;
    }



    /**
     * Method to save Packaged.
     * 
     * @param idPackaged
     * 
     * @param buildItemCont
     */
    private IPackaged saveAllItems(Collection<Item> items, String idPackaged) {
        Packaged packaged = new Packaged();
        packaged.setId(idPackaged);
        packaged.setDatePackage(new Date());
        packaged.setProduct(items);
        packaged.setUnits(calculateUnits(items));
        return repository.save(packaged);
    }



    private long calculateUnits(Collection<Item> items) {
        return items.stream().map(d -> d.getUnits())
                .collect(Collectors.summarizingLong(Long::longValue)).getSum();
    }



    /**
     * * Method to get products in all contributions and build Items according
     * to parameters.
     * 
     * @param item
     * @param buildItemProd
     * @param buildItemCont
     * @return {@code Collection<Item>}
     * @throws SDKStockNotFoundException
     * @throws SDKStockServiceException
     * @throws SDKStockServiceIllegalArgumentException
     */
    private Map<String, Item> getItemsInProduct(ItemDTO item)
            throws SDKStockNotFoundException, SDKStockServiceException,
            SDKStockServiceIllegalArgumentException {

        Map<String, Item> buildItemProd = new HashMap<>();
        Collection<ResponseStockData> stock = new ArrayList<>();

        /** SEARCH PRODUCTS TO BUILD ITEMS */
        stock = this.searchProductInStock(item.getProduct());

        /** BUILD ITEMS WITH PRODUCTS FOUNDED **/
        buildItemProd = this.matchProductInContribution(stock, item);

        if (buildItemProd.isEmpty()) {
            throw new SDKStockNotFoundException(pkProduct(item.getProduct()),
                    PRODUCT_NOT_FOUND);
        }

        return buildItemProd;
    }



    /**
     * @param product
     * @throws SDKStockServiceIllegalArgumentException
     * @throws SDKStockServiceException
     * @throws SDKStockNotFoundException
     * @throws SDKStockServiceNotAvailableException
     */
    private Collection<ResponseStockData> searchProductInStock(
            ProductPK product) throws SDKStockServiceNotAvailableException,
            SDKStockNotFoundException, SDKStockServiceException,
            SDKStockServiceIllegalArgumentException {

        Collection<ResponseStockData> result =
                sdkStock.findStockByProduct(pkProduct(product));

        if (result.isEmpty()) {
            throw new SDKStockNotFoundException(pkProduct(product),
                    PRODUCT_NOT_FOUND);
        }
        return result;
    }



    /**
     * Get PK in Product.
     * 
     * @param product
     * @return {@code String}
     */
    private String pkProduct(ProductPK product) {
        return product.getProduct().toString();
    }



    /**
     * Search product in Contribution and build item.
     * 
     * @param response
     * @param productPK
     * @return {@code Item}
     * @throws SDKStockNotFoundException
     */
    private Map<String, Item> matchProductInContribution(
            Collection<ResponseStockData> response, ItemDTO item)
            throws SDKStockNotFoundException {

        Map<String, Item> result = new HashMap<>();

        /** APPLY FIRTS FILTER */
        List<ResponseStockData> candidatesFilterOne =
                filterCandidatesPlusThanCero(response, item);

        /** ORDER CANDIDATES */
        candidatesFilterOne
                .sort(Comparator.comparing(ResponseStockData::getQuantity));

        /** APPLY SECOND FILTER */
        Map<String, Item> candidatesFilterTwo =
                buildCandidates(candidatesFilterOne, item);
        result.putAll(candidatesFilterTwo);

        return result;
    }



    /**
     * Method to filter products candidates with quantity db less than quantity
     * product
     * 
     * @param candidatesFilterOne
     * @param item
     * @return {@code List<ResponseStockData>}
     */
    private Map<String, Item> buildCandidates(
            List<ResponseStockData> candidates, ItemDTO item) {

        /** FILTER PRODUCTS EQUALS TO ITEM PRODUCT DB <= ITEM NEED IT **/
        Map<String, Item> map = new HashMap<>();

        candidates.stream().forEach(d -> {
            Long val = calculateQuantityForItem(Long.valueOf(d.getQuantity()));
            map.put(d.getId(), this.buildItem(d, val));
        });

        requiredValue = 0L;
        currentValue = 0L;

        return map;
    }



    private Long calculateQuantityForItem(Long valItemBD) {

        Long value = 0L;

        if (requiredValue != 0) {
            if (valItemBD <= requiredValue) {
                value = valItemBD;
                requiredValue -= valItemBD;
                currentValue += valItemBD;
            } else if (valItemBD > requiredValue) {
                value = requiredValue;
                currentValue += value;
                requiredValue = 0L;
            }
        }

        return value;
    }



    /**
     * Method to filter all stocks with quantity plus then zero.
     * 
     * @param response
     * @param item
     * @return {@code List<ResponseStockData>}
     * @throws SDKStockNotFoundException
     */
    private List<ResponseStockData> filterCandidatesPlusThanCero(
            Collection<ResponseStockData> response, ItemDTO item)
            throws SDKStockNotFoundException {

        /** FILTER 1 : SELECT ONLY THE RODUCTS WITH QUANTITY AVAILABLE **/
        Predicate<ResponseStockData> filter2 = d -> d.getQuantity() > 0;

        List<ResponseStockData> candidates =
                response.stream().filter(filter2).collect(Collectors.toList());

        if (candidates.isEmpty()) {
            throw new SDKStockNotFoundException(pkProduct(item.getProduct()),
                    PRODUCT_WITHOUT_EXISTENCE);
        }

        return candidates;
    }



    /**
     * Method to build Item from Stock founded.
     * 
     * @param stock
     * @return {@code Item}
     */
    private Item buildItem(ResponseStockData stock, Long units) {
        return new Item(stock.getProduct(), stock.getContribution(), units);
    }



    /**
     * Method to remove product.
     * 
     * @param idProduct
     * @param idPackaged
     * @return {@code IPackaged}
     */

    public IPackaged removeProduct(String idPackaged, ProductPK idProduct) {

        /* SE CONSULTA SI EXISTE EL PRODUCTO */
        /* SE ELIMINA EL PRODUCTO DEL STOCK */
        /* SE ADICIONA LA CANTIDAD AL STOCK QUE SE RETIRO DEL PACKAGE */
        return null;
    }

}
