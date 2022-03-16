package com.byte_51.bidproject.controller;

import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.dto.ProductDTO;
import com.byte_51.bidproject.repository.ProductRepository;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import com.byte_51.bidproject.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/product/register")
    public void register(@ModelAttribute("requestDTO") PageRequestDTO requestDTO){
        log.info("register페이지 로드");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/product/register")
    public String register(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, ProductDTO productDTO){
        log.info("=====================================");
        log.info(bidAuthMemberDTO);

        productDTO.setEmail(bidAuthMemberDTO.getEmail());
        productDTO.setEndDate(LocalDateTime.now().plusDays(productDTO.getBidPlus()));

        log.info(productDTO);
        Long pdnum = productService.register(productDTO);

        return "redirect:/product/read?pdNum=" + pdnum;
    }

    @GetMapping("/product/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list page오픈");
        model.addAttribute("result",productService.getListAll(pageRequestDTO));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/product/read")
    public void read(Long pdNum, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info("read page오픈");
        ProductDTO productDTO = productService.getProduct(pdNum);
        model.addAttribute("productDTO",productDTO);
    }
}
