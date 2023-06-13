package BackendProject.repositories;

import BackendProject.entities.Product;
import BackendProject.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from products where owner_id = :ownerId",
            countQuery = "select count(*) from products where owner_id = :ownerId",
            nativeQuery = true)
    Slice<Product> searchByOwnerId(@Param("ownerId") Integer ownerId, Pageable pageable);

    @Query(value = "select * from products (:productType is null or product_type like :productType)",
            countQuery = "select count(*) from products (:productType is null or like :productType)",
            nativeQuery = true)
    Slice<Product> searchByProductType(@Param("productType") String productType, Pageable pageable);

    @Query(value = "select * from products (:productName is null or product_name like :productName)",
            countQuery = "select count(*) from products (:searchText is null or like :searchText)",
            nativeQuery = true)
    Slice<Product> searchByProductName(@Param("productName") String productName, Pageable pageable);

    @Query(value = "select * from products where price <= :price",
            countQuery = "select count(*) from products where price <= :price",
            nativeQuery = true)
    Slice<Product> searchByPriceLower(@Param("price") Integer price, Pageable pageable);

    @Query(value = "select * from products where price >= :price",
            countQuery = "select count(*) from products where price >= :price",
            nativeQuery = true)
    Slice<Product> searchByPriceHigher(@Param("price") Integer price, Pageable pageable);

}
