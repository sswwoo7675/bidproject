package com.byte_51.bidproject.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pdNum;

    private String pdName; //상품이름

    private String pdContent; //상품설명

    private LocalDateTime endDate; //입찰 종료 날짜

    private int minBidPrice; //최소입찰가

    private int catagory; //categort

    @Column(columnDefinition = "integer default 1")
    private int visible; //0: 숨김 1: 보임

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //상품 판매자

    public void changeVisible(int visible){
        this.visible = visible;
    }
}
