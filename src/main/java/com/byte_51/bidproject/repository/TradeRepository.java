package com.byte_51.bidproject.repository;

import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Trade;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade,Long> {

    @EntityGraph(attributePaths = {"seller","buyer","product"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select t " +
            "from Trade t " +
            "where t.seller=:member " +
            "and t.tradeState<>4")
    List<Trade> getTradeListBySeller(@Param("member") Member member);

    @EntityGraph(attributePaths = {"seller","buyer","product"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select t " +
            "from Trade t " +
            "where t.buyer=:member " +
            "and t.tradeState<>4")
    List<Trade> getTradeListByBuyer(@Param("member") Member member);

    @EntityGraph(attributePaths = {"seller","buyer","product"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select t " +
            "from Trade t " +
            "where (t.buyer=:member " +
            "or t.seller=:member) " +
            "and t.tradeState=4")
    List<Trade> getSucTradeList(@Param("member") Member member);

}
