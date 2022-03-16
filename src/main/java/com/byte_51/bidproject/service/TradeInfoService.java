package com.byte_51.bidproject.service;

import com.byte_51.bidproject.dto.TradeInfoDTO;
import com.byte_51.bidproject.entity.Trade;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;

import java.util.List;

public interface TradeInfoService {

    List<TradeInfoDTO> getSellList(BidAuthMemberDTO bidAuthMemberDTO);  //판매 리스트

    List<TradeInfoDTO> getBuyList(BidAuthMemberDTO bidAuthMemberDTO);  //구매 리스트

    List<TradeInfoDTO> getSucList(BidAuthMemberDTO bidAuthMemberDTO); //거래완료 리스트

    String sendMoney(Long tradeID, BidAuthMemberDTO bidAuthMemberDTO); //금액송금

    String checkinge(Long tradeID); //물품 인계
    
    String checkinsu(Long tradeID); //물품 인수

    default TradeInfoDTO entityToDTO(Trade trade){
        TradeInfoDTO tradeInfoDTO = TradeInfoDTO.builder()
                .tradeID(trade.getTradeID())
                .pdName(trade.getProduct().getPdName())
                .seller(trade.getSeller().getEmail())
                .buyer(trade.getBuyer().getEmail())
                .price(trade.getPrice())
                .state(trade.getTradeState())
                .build();

        return tradeInfoDTO;
    }
}
