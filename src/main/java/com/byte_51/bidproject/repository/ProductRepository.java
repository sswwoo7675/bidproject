package com.byte_51.bidproject.repository;

import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p, pi, m, count(bid)"+
            " from Product p left outer join  ProductImage pi on pi.product=p" +
            " left outer join Member m on p.member = m" +
            " left outer join Bidding bid on bid.product = p" +
            " where p.pdNum=:pdNum group by pi")
    List<Object[]> getProduct(@Param("pdNum") Long pdNum); //with all

    @Query("select p, pi, m, count(distinct bid)" +
            " from Product p left outer join Bidding bid on bid.product = p" +
            " left outer join Member m on p.member = m" +
            " left outer join ProductImage pi on pi.product=p" +
            " group by p")
    Page<Object[]> getProductListAll(Pageable pageable);


    @Query("select p" +
            " from Product p " +
            " where p.member=:member")
    Page<Product> getProductListByMember(Pageable pageable, @Param("member") Member member);


    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select p" +
            " from Product p " +
            " where p.endDate<=:time " +
            " and p.visible=1")
    List<Product> getProductByTime(@Param("time")LocalDateTime time);


    @Modifying
    @Query("delete from Product p where p.pdNum=:id")
    void deleteByMyID(@Param("id") Long id);
}
