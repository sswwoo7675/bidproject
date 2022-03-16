package com.byte_51.bidproject.repository;

import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void getTradeListBySellerTest(){
        Member member  = Member.builder().email("user95@zerock.org").build();

        List<Trade> tradeList = tradeRepository.getTradeListBySeller(member);

        for(Trade trade: tradeList){
            System.out.println(trade);
        }
    }

    @Test
    public void getTradeListByBuyerTest(){
        Member member  = Member.builder().email("user93@zerock.org").build();

        List<Trade> tradeList = tradeRepository.getTradeListByBuyer(member);

        for(Trade trade: tradeList){
            System.out.println(trade);
        }
    }

    @Test
    public void getSucTradeListTest(){
        Member member  = Member.builder().email("user93@zerock.org").build();

        List<Trade> tradeList = tradeRepository.getSucTradeList(member);

        for(Trade trade: tradeList){
            System.out.println(trade);
        }
    }
}
