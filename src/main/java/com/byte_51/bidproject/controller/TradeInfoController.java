package com.byte_51.bidproject.controller;

import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.dto.TradeInfoDTO;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import com.byte_51.bidproject.service.BiddingService;
import com.byte_51.bidproject.service.ProductService;
import com.byte_51.bidproject.service.TradeInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class TradeInfoController {

    private final ProductService productService;
    private final BiddingService biddingService;
    private final TradeInfoService tradeInfoService;

    @GetMapping("/tradeinfo/reglist")
    public void regList(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, PageRequestDTO requestDTO, Model model){
        log.info("등록내역/입찰내역 화면 open");
        requestDTO.setSize(10);
        model.addAttribute("resultProduct",productService.getListAllByMember(requestDTO, bidAuthMemberDTO.getEmail()));
    }

    @GetMapping("/tradeinfo/bidlist")
    public void bidList(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, PageRequestDTO requestDTO, Model model){
        log.info("등록내역/입찰내역 화면 open");
        requestDTO.setSize(10);
        model.addAttribute("resultBid",biddingService.getBidListByMember(bidAuthMemberDTO.getEmail(),requestDTO));
    }

    @GetMapping("/tradeinfo/selllist")
    public void sellList(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, Model model){
        List<TradeInfoDTO> result = tradeInfoService.getSellList(bidAuthMemberDTO);

        model.addAttribute("tradeDTO",result);
    }

    @GetMapping("/tradeinfo/buylist")
    public void buyList(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, Model model){
        List<TradeInfoDTO> result = tradeInfoService.getBuyList(bidAuthMemberDTO);

        model.addAttribute("tradeDTO",result);
    }

    @GetMapping("/tradeinfo/tradesuclist")
    public void tradeSucList(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, Model model){
        List<TradeInfoDTO> result = tradeInfoService.getSucList(bidAuthMemberDTO);

        model.addAttribute("tradeDTO",result);
    }

    @GetMapping("/tradeinfo/sendmoney")
    public String sendMoney(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, Long tradeID, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("msg",tradeInfoService.sendMoney(tradeID,bidAuthMemberDTO));

        return "redirect:/tradeinfo/buylist";
    }

    @GetMapping("/tradeinfo/checkinge")
    public String checkinge(Long tradeID, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("msg",tradeInfoService.checkinge(tradeID));

        return "redirect:/tradeinfo/selllist";
    }

    @GetMapping("/tradeinfo/checkinsu")
    public String checkinsu(Long tradeID, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("msg",tradeInfoService.checkinsu(tradeID));

        return "redirect:/tradeinfo/tradesuclist";
    }

}















