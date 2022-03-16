package com.byte_51.bidproject.controller;

import com.byte_51.bidproject.dto.BiddingDTO;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import com.byte_51.bidproject.service.BiddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class BiddingController {

    private final BiddingService biddingService;

    @PostMapping("/bidding/{pdNum}")
    public ResponseEntity<String> regBidding(@AuthenticationPrincipal BidAuthMemberDTO bidAuthMemberDTO, @RequestBody BiddingDTO biddingDTO){


        String result = biddingService.register(biddingDTO,bidAuthMemberDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/bidding/pdBidList")
    public ResponseEntity<List<BiddingDTO>> getPdBidList(Long pdNum) {
        List<BiddingDTO> biddingDTOS = biddingService.getBidListWithMember(pdNum);
        return new ResponseEntity<>(biddingDTOS,HttpStatus.OK);
    }
}
