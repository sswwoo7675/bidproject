package com.byte_51.bidproject.service;

import com.byte_51.bidproject.dto.BiddingDTO;
import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.entity.Bidding;
import com.byte_51.bidproject.repository.BiddingRepository;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class BiddingServiceTest {
    @Autowired
    private BiddingService biddingService;

    @Test
    public void registerTest(){
        BiddingDTO biddingDTO = BiddingDTO.builder()
                            .pdNum(6L)
                            .bidPrice(2000)
                            .build();
        BidAuthMemberDTO bidAuthMemberDTO = new BidAuthMemberDTO("user97@zerock.org","1111",false, new HashSet<>());
        bidAuthMemberDTO.setPoint(9999);
        String result = biddingService.register(biddingDTO,bidAuthMemberDTO);
        System.out.println(result);
    }

    @Test
    public void getBidListWithMemberTest(){
        List<BiddingDTO> biddingDTOS = biddingService.getBidListWithMember(7L);
        biddingDTOS.forEach(biddingDTO -> {
            System.out.println(biddingDTO);
        });
    }

    @Test
    public void getBidListByMemberTest(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setSize(10);
        System.out.println(biddingService.getBidListByMember("user95@zerock.org",pageRequestDTO));

    }
}
