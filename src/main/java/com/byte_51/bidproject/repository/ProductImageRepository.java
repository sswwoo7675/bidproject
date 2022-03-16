package com.byte_51.bidproject.repository;

import com.byte_51.bidproject.entity.Product;
import com.byte_51.bidproject.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

    @Modifying
    @Query("delete from ProductImage pi where pi.product=:product")
    void deleteByProduct(@Param("product")Product product);

}
