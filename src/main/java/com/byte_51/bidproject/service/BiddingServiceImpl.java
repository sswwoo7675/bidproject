package com.byte_51.bidproject.service;

import com.byte_51.bidproject.dto.BiddingDTO;
import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.dto.PageResultDTO;
import com.byte_51.bidproject.entity.Bidding;
import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.MemberRole;
import com.byte_51.bidproject.entity.Product;
import com.byte_51.bidproject.repository.BiddingRepository;
import com.byte_51.bidproject.repository.MemberRepository;
import com.byte_51.bidproject.repository.ProductRepository;
import com.byte_51.bidproject.security.dto.BidAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BiddingServiceImpl implements BiddingService{

    private final BiddingRepository biddingRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public String register(BiddingDTO biddingDTO, BidAuthMemberDTO bidAuthMemberDTO){

        Optional<Product> result = productRepository.findById(biddingDTO.getPdNum());
        Member member = memberRepository.findById(bidAuthMemberDTO.getEmail()).get();

        int myBidPrice = biddingDTO.getBidPrice(); //설정입찰가
        
        if(result.isPresent()){
            Product product = result.get();

            Bidding alreadyBid = biddingRepository.getBiddingByMember(product,member);  //이미 해당 물품에 입찰기록이 있는지 조사

            if(alreadyBid != null){ //입찰기록이 있으면
                return "alredyBidding";
            }
            
            if(myBidPrice < product.getMinBidPrice()){ //최소입찰가 만족 검사
                return "minBidPriceError";
            }
        }

        if(myBidPrice > member.getPoint()){ //포인트 부족시
            return "lackPoint";
        }

        biddingDTO.setBidMember(bidAuthMemberDTO.getEmail());
        Bidding bidding = dtoToEntity(biddingDTO);
        biddingRepository.save(bidding); //입찰가 등록

        member.changePoint(-bidding.getBidPrice());//포인트감소
        memberRepository.save(member);

        return "success";
    }

    @Override
    public List<BiddingDTO> getBidListWithMember(Long pdNum){ //상품에 대한 입찰현황 조회
        Product product = Product.builder().pdNum(pdNum).build();
        List<Bidding> result = biddingRepository.getBiddingByProduct(product);

        List<BiddingDTO> biddingDTOS = result.stream().map(bidding -> {
            return entityToDTO(bidding);
        }).collect(Collectors.toList());

        return biddingDTOS;
    }
    
    @Override
    public PageResultDTO<BiddingDTO,Bidding> getBidListByMember(String email, PageRequestDTO pageRequestDTO){ //멤버별 입찰현황 조회
        Member member = Member.builder().email(email).build();
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("bidNum").descending());

        Page<Bidding> biddings = biddingRepository.getBiddingByMember2(member,pageable);

        Function<Bidding, BiddingDTO> fn = (bidding -> entityToDTO(bidding));
        return new PageResultDTO<>(biddings,fn);
    }
}
