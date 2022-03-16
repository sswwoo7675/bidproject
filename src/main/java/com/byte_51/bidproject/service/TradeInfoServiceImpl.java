package com.byte_51.bidproject.service;

import com.byte_51.bidproject.dto.TradeInfoDTO;
import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Trade;
import com.byte_51.bidproject.repository.MemberRepository;
import com.byte_51.bidproject.repository.TradeRepository;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TradeInfoServiceImpl implements TradeInfoService{

    private final MemberRepository memberRepository;
    private final TradeRepository tradeRepository;

    @Override
    public List<TradeInfoDTO> getSellList(BidAuthMemberDTO bidAuthMemberDTO){
        Member member = Member.builder().email(bidAuthMemberDTO.getEmail()).build();

        List<Trade> tradeList = tradeRepository.getTradeListBySeller(member);

        List<TradeInfoDTO> TradeInfoDTOList = tradeList.stream().map(trade -> {
            return entityToDTO(trade);
        }).collect(Collectors.toList());

        return TradeInfoDTOList;
    }

    @Override
    public List<TradeInfoDTO> getBuyList(BidAuthMemberDTO bidAuthMemberDTO){
        Member member = Member.builder().email(bidAuthMemberDTO.getEmail()).build();

        List<Trade> tradeList = tradeRepository.getTradeListByBuyer(member);

        List<TradeInfoDTO> TradeInfoDTOList = tradeList.stream().map(trade -> {
            return entityToDTO(trade);
        }).collect(Collectors.toList());

        return TradeInfoDTOList;
    }

    @Override
    public List<TradeInfoDTO> getSucList(BidAuthMemberDTO bidAuthMemberDTO) {
        Member member = Member.builder().email(bidAuthMemberDTO.getEmail()).build();

        List<Trade> tradeList = tradeRepository.getSucTradeList(member);

        List<TradeInfoDTO> TradeInfoDTOList = tradeList.stream().map(trade -> {
            return entityToDTO(trade);
        }).collect(Collectors.toList());

        return TradeInfoDTOList;
    }

    @Override
    @Transactional
    public String sendMoney(Long tradeID, BidAuthMemberDTO bidAuthMemberDTO){
        Member admin = memberRepository.findById("user99@zerock.org").get();
        Member my = memberRepository.findById(bidAuthMemberDTO.getEmail()).get();
        Trade trade = tradeRepository.findById(tradeID).get();

        my.changePoint(-trade.getPrice());
        admin.changePoint(trade.getPrice());
        trade.changeState(2); //물품인계전으로 거래상태 변경

        memberRepository.save(my);
        memberRepository.save(admin);
        tradeRepository.save(trade);

        return "송금이 완료되었습니다.";
    }

    @Override
    @Transactional
    public String checkinge(Long tradeID){
        Trade trade = tradeRepository.findById(tradeID).get();
        trade.changeState(3);
        tradeRepository.save(trade);

        return "물품인계확인이 완료되었습니다.";
    }

    @Override
    @Transactional
    public String checkinsu(Long tradeID){
        Trade trade = tradeRepository.findById(tradeID).get();
        Member admin = memberRepository.findById("user99@zerock.org").get();
        Member seller = trade.getSeller();

        admin.changePoint(-trade.getPrice());
        seller.changePoint(trade.getPrice());

        trade.changeState(4); //거래완료상태로 돌림

        return "거래가 완료되었습니다. 감사합니다.";
    }
}
