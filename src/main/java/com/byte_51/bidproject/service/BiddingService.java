package com.byte_51.bidproject.service;


import com.byte_51.bidproject.dto.BiddingDTO;
import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.dto.PageResultDTO;
import com.byte_51.bidproject.entity.Bidding;
import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Product;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface BiddingService {

    String register(BiddingDTO biddingDTO, BidAuthMemberDTO bidAuthMemberDTO);

    List<BiddingDTO> getBidListWithMember(Long pdNum);

    PageResultDTO<BiddingDTO,Bidding> getBidListByMember(String email, PageRequestDTO pageRequestDTO);

    default Bidding dtoToEntity(BiddingDTO biddingDTO){
        Bidding bidding = Bidding.builder()
                .bidPrice(biddingDTO.getBidPrice())
                .member(Member.builder().email(biddingDTO.getBidMember()).build())
                .product(Product.builder().pdNum(biddingDTO.getPdNum()).build())
                .build();

        return bidding;

    }

    default BiddingDTO entityToDTO(Bidding bidding){
        BiddingDTO biddingDTO = BiddingDTO.builder()
                .bidNum(bidding.getBidNum())
                .bidMember(bidding.getMember().getEmail())
                .bidDate(bidding.getRegDate())
                .bidPrice(bidding.getBidPrice())
                .pdNum(bidding.getProduct().getPdNum())
                .pdName(bidding.getProduct().getPdName())
                .build();

        return biddingDTO;
    }
}
