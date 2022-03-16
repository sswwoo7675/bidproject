package com.byte_51.bidproject.service;

import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void getProductTest(){
        ProductDTO productDTO = productService.getProduct(7L);
        System.out.println(productDTO);
    }

    @Test
    public void getProductListAllTest(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        System.out.println(productService.getListAll(pageRequestDTO));
    }

    @Test
    public void getListAllByMemberTest(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setSize(10);
        System.out.println(productService.getListAllByMember(pageRequestDTO,"user90@zerock.org"));
    }

}
