package com.byte_51.bidproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"product","member"})
public class Bidding extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidNum; //입찰번호 저장

    private int bidPrice; //입찰가격

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product; //입찰대상상품

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //입찰한사람

}
