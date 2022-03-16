package com.byte_51.bidproject.repository;

import com.byte_51.bidproject.entity.Bidding;
import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;


@SpringBootTest
public class BiddingRepositoryTest {

    @Autowired
    private BiddingRepository biddingRepository;

    @Test
    public void getBiddingByMemberTest(){
        Product product = Product.builder().pdNum(3L).build();
        Member member = Member.builder().email("user95@zerock.org").build();
        Bidding result = biddingRepository.getBiddingByMember(product,member);
        System.out.println(result);
    }

    @Test
    public void getBiddingByMember2Test(){
        Member member = Member.builder()
                .email("user95@zerock.org")
                .build();

        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"bidNum"));

        Page<Bidding> result = biddingRepository.getBiddingByMember2(member,pageRequest);

        result.stream().forEach(bid->{
            System.out.println(bid);
            System.out.println(bid.getProduct());
        });
    }

    @Test
    public void getBiddingByProductTest() {
        Product product = Product.builder().pdNum(1L).build();
        List<Bidding> bidList = biddingRepository.getBiddingByProduct(product);

        if (bidList.isEmpty()) {
            System.out.println("no bid");
        } else {
            for (Bidding bid : bidList) {
                System.out.println(bid);
                System.out.println(bid.getMember());
            }
        }
    }


}
