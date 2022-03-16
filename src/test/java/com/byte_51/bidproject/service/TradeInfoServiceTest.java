package com.byte_51.bidproject.service;

import com.byte_51.bidproject.dto.TradeInfoDTO;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class TradeInfoServiceTest {

    @Autowired
    private TradeInfoService tradeInfoService;

    @Test
    public void getSellListTest(){
        BidAuthMemberDTO bidAuthMemberDTO = new BidAuthMemberDTO("user95@zerock.org","1111",false, new HashSet<>());

        List<TradeInfoDTO> tradeInfoDTOList =tradeInfoService.getSellList(bidAuthMemberDTO);

        for(TradeInfoDTO tradeInfoDTO: tradeInfoDTOList){
            System.out.println(tradeInfoDTO);
        }
    }

    @Test
    public void getBuyListTest(){
        BidAuthMemberDTO bidAuthMemberDTO = new BidAuthMemberDTO("user93@zerock.org","1111",false, new HashSet<>());

        List<TradeInfoDTO> tradeInfoDTOList =tradeInfoService.getBuyList(bidAuthMemberDTO);

        for(TradeInfoDTO tradeInfoDTO: tradeInfoDTOList){
            System.out.println(tradeInfoDTO);
        }
    }

    @Test
    public void getSucListTest(){
        BidAuthMemberDTO bidAuthMemberDTO = new BidAuthMemberDTO("user93@zerock.org","1111",false, new HashSet<>());

        List<TradeInfoDTO> tradeInfoDTOList =tradeInfoService.getSucList(bidAuthMemberDTO);

        for(TradeInfoDTO tradeInfoDTO: tradeInfoDTOList){
            System.out.println(tradeInfoDTO);
        }
    }

    @Test
    public void sendMoneyTest(){
        BidAuthMemberDTO bidAuthMemberDTO = new BidAuthMemberDTO("user95@zerock.org","1111",false, new HashSet<>());
        System.out.println(tradeInfoService.sendMoney(23L,bidAuthMemberDTO));

    }

}
