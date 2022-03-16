package com.byte_51.bidproject.service;

import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.dto.PageResultDTO;
import com.byte_51.bidproject.dto.ProductDTO;
import com.byte_51.bidproject.entity.Bidding;
import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Product;
import com.byte_51.bidproject.entity.ProductImage;
import com.byte_51.bidproject.repository.ProductImageRepository;
import com.byte_51.bidproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional
    @Override
    public Long register(ProductDTO productDTO){
        Map<String, Object> entMap = dtoToEntity(productDTO);

        Product product = (Product) entMap.get("product");
        List<ProductImage> productImageList= (List<ProductImage>) entMap.get("productImageList");

        productRepository.save(product);

        productImageList.forEach(productImage -> {
            productImageRepository.save(productImage);
        });

        return product.getPdNum();
    }

    @Transactional
    @Override
    public ProductDTO getProduct(Long pdNum){
        List<Object[]> result = productRepository.getProduct(pdNum);
        Product product = (Product) result.get(0)[0];

        List<ProductImage> productImageList = new ArrayList<>();

        result.forEach(arr->{
            ProductImage productImage = (ProductImage) arr[1];
            productImageList.add(productImage);
        });

        Member member = (Member) result.get(0)[2];
        Long bidCount = (Long) result.get(0)[3];

        return entitiesToDTO(product,productImageList,member,bidCount);
    }

    @Transactional
    @Override
    public PageResultDTO<ProductDTO,Object[]> getListAll(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("pdNum").descending());
        Page<Object[]> result = productRepository.getProductListAll(pageable);

        Function<Object[], ProductDTO> fn = (arr->entitiesToDTO(
                (Product) arr[0],
                (List<ProductImage>) (Arrays.asList((ProductImage)arr[1])),
                (Member) arr[2],
                (Long) arr[3] //bidcnt
        ));

        return new PageResultDTO<>(result,fn);
    }


    @Override
    public PageResultDTO<ProductDTO,Product> getListAllByMember(PageRequestDTO requestDTO,String email){
        Pageable pageable = requestDTO.getPageable(Sort.by("pdNum").descending());
        Member member = Member.builder().email(email).build();

        Page<Product> products = productRepository.getProductListByMember(pageable, member);

        Function<Product, ProductDTO> fn = (arr->entitiesToDTO(arr,new ArrayList<>(),member,null));

        return new PageResultDTO<>(products,fn);
    }
}
