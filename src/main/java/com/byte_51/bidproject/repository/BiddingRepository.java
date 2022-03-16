package com.byte_51.bidproject.repository;

import com.byte_51.bidproject.entity.Bidding;
import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BiddingRepository extends JpaRepository<Bidding, Long> {


    @Query("select bid"+
            " from Bidding bid" +
            " where bid.product=:product and bid.member=:member")
    Bidding getBiddingByMember(@Param("product") Product product,
                                         @Param("member") Member member); //상품별 특정회원 입찰정보 조회

    @EntityGraph(attributePaths = {"product","member"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select bid" +
            " from Bidding bid" +
            " where bid.product=:product" +
            " order by bid.bidPrice desc")
    List<Bidding> getBiddingByProduct(@Param("product") Product product); //제품별 입찰내역 조회 쿼리

    @EntityGraph(attributePaths = {"product","member"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select bid" +
            " from Bidding bid" +
            " where bid.member=:member")
    Page<Bidding> getBiddingByMember2(@Param("member") Member member, Pageable pageable); //특정회원이 입찰한 상품 조회 쿼리

    @Modifying
    @Query("delete from Bidding bid where bid.product=:product")
    void deleteByProduct(@Param("product") Product product);
}
