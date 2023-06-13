package BackendProject.controllers;

import BackendProject.dto.*;
import BackendProject.dto.request.RequestData;
import BackendProject.entities.Product;
import BackendProject.services.ProductService;
import BackendProject.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('product:add')")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json"})
    public Product add(@RequestBody AddProduct addProduct) throws Exception {
        GeneralUtil.checkRequiredProperties(addProduct, Arrays.asList("ownerId", "productType", "productName", "price"));
        return productService.add(addProduct);
    }

    @PreAuthorize("hasAuthority('product:read')")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {"application/json"})
    public List<Product> getAll(){
        return productService.getAll();
    }
    @PreAuthorize("hasAuthority('product:read')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
    public Product getById(@PathVariable Long id) throws Exception {
        return productService.getById(id);
    }

    @PreAuthorize("hasAuthority('product:read')")
    @RequestMapping(value = "/searchOwner", method = RequestMethod.POST, produces = {"application/json"})
    public Slice<Product> searchByOwnerEmail(@RequestBody RequestData<SearchProduct> rd) {
        return productService.searchByOwnerId(rd.getData(), rd.getPaging());
    }

    @PreAuthorize("hasAuthority('product:read')")
    @RequestMapping(value = "/searchType", method = RequestMethod.POST, produces = {"application/json"})
    public Slice<Product> searchByProductType(@RequestBody RequestData<SearchProduct> rd) {
        return productService.searchByProductType(rd.getData(), rd.getPaging());
    }

    @PreAuthorize("hasAuthority('product:read')")
    @RequestMapping(value = "/searchName", method = RequestMethod.POST, produces = {"application/json"})
    public Slice<Product> searchByProductName(@RequestBody RequestData<SearchProduct> rd) {
        return productService.searchByProductName(rd.getData(), rd.getPaging());
    }

    @PreAuthorize("hasAuthority('product:read')")
    @RequestMapping(value = "/searchPriceh", method = RequestMethod.POST, produces = {"application/json"})
    public Slice<Product> searchByPriceHigher(@RequestBody RequestData<SearchProduct> rd) {
        return productService.searchByPriceHiger(rd.getData(), rd.getPaging());
    }
    @PreAuthorize("hasAuthority('product:read')")
    @RequestMapping(value = "/searchPricel", method = RequestMethod.POST, produces = {"application/json"})
    public Slice<Product> searchByPriceLower(@RequestBody RequestData<SearchProduct> rd) {
        return productService.searchByPriceLower(rd.getData(), rd.getPaging());
    }

    @PreAuthorize("hasAuthority('product:edit')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = {"application/json"})
    public Product edit(@PathVariable Long id, @RequestBody AddProduct addProduct) throws Exception {
        GeneralUtil.checkRequiredProperties(addProduct, Arrays.asList("price"));
        return productService.edit(id, addProduct);
    }
}
