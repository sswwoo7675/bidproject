package com.byte_51.bidproject.controller;

import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import com.byte_51.bidproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String mainPageRedirect() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public void mainPageLoad(Model model) {
        log.info("main page Load");
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(1);
        pageRequestDTO.setSize(6);
        model.addAttribute("result",productService.getListAll(pageRequestDTO));
    }

    @GetMapping("/login")
    public String loginPage(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, @RequestParam(value="error", required = false, defaultValue = "0") Boolean error, Model model) {
        if(bidAuthMemberDTO != null){
            return "redirect:/main";
        }
        if (error) {
            model.addAttribute("failed",1);
        }
        log.info("Login page Load");
        return "login";
    }
}
