package BackendProject.services;

import BackendProject.dto.AddProduct;
import BackendProject.dto.SearchProduct;
import BackendProject.dto.request.Paging;
import BackendProject.entities.Product;
import BackendProject.entities.User;
import BackendProject.repositories.ProductRepository;
import BackendProject.util.GeneralUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product add(AddProduct addProduct) throws Exception{
        Product product = new Product();
        GeneralUtil.getCopyOf(addProduct,product);
        
        return productRepository.save(product);
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getById(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PRODUCT_NOT_FOUND"));
    }
    public Slice<Product> searchByOwnerId(SearchProduct searchProduct, Paging paging){
        Integer ownerId = null;

        if(searchProduct.getOwnerId() != null && !searchProduct.getOwnerId().equals("")){
            ownerId = searchProduct.getOwnerId();
        }

        Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by("price").ascending());
        return productRepository.searchByOwnerId(ownerId, pageable);
    }

    public Slice<Product> searchByProductType(SearchProduct searchProduct, Paging paging){
        String productType = null;

        if(searchProduct.getProductType() != null && !searchProduct.getProductType().equals("")){
            productType = "%" + searchProduct.getProductType() + "%";
        }

        Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by("price").ascending());
        return productRepository.searchByProductType(productType, pageable);
    }
    public Slice<Product> searchByProductName(SearchProduct searchProduct, Paging paging){
        String productName = null;

        if(searchProduct.getProductName() != null && !searchProduct.getProductName().equals("")){
            productName = "%" + searchProduct.getProductName() + "%";
        }

        Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by("price").ascending());
        return productRepository.searchByProductName(productName, pageable);
    }

    public Slice<Product> searchByPriceLower(SearchProduct searchProduct, Paging paging){
        Integer price = null;

        if(searchProduct.getPrice() != null && !searchProduct.getPrice().equals("")){
            price =  searchProduct.getPrice();
        }

        Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by("price").descending());
        return productRepository.searchByPriceLower(price, pageable);
    }

    public Slice<Product> searchByPriceHiger(SearchProduct searchProduct, Paging paging){
        Integer price = null;

        if(searchProduct.getPrice() != null && !searchProduct.getPrice().equals("")){
            price = searchProduct.getPrice();
        }

        Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by("price").ascending());
        return productRepository.searchByPriceHigher(price, pageable);
    }

    @Transactional
    public Product edit(Long id, AddProduct addProduct) throws Exception{
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
        GeneralUtil.getCopyOf(addProduct,product);

        return productRepository.save(product);
    }
}
