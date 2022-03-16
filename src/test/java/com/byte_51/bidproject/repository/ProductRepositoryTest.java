package com.byte_51.bidproject.repository;

import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Product;
import com.byte_51.bidproject.entity.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getProductTest(){
        List<Object[]> ProductInfo = productRepository.getProduct(1L);
        for(Object[] arr: ProductInfo){
            System.out.println((Product)arr[0]);
            System.out.println((ProductImage)arr[1]);
            System.out.println((Member)arr[2]);
            System.out.println((Long) arr[3]);
            System.out.println((Integer) arr[4]);
        }
    }

    @Test
    public void getProductListAllTest(){
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"pdNum"));

        Page<Object[]> result =productRepository.getProductListAll(pageRequest);

        for(Object[] objects: result.getContent()){
            System.out.println(objects[3]);
        }
    }

    @Test
    public void getProductListByMemberTest(){
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"pdNum"));

        Page<Product> result = productRepository.getProductListByMember(pageRequest, Member.builder().email("user95@zerock.org").build());

        for(Product p: result.getContent()){
            System.out.println(p);
        }
    }

    @Test
    public void getProductByTime(){
        LocalDateTime targetTime = LocalDateTime.now().withMinute(59).withSecond(59).withNano(999999999);

        for(Product p: productRepository.getProductByTime(targetTime)){
            System.out.println(p);
            System.out.println(p.getMember());
        }
    }

}
