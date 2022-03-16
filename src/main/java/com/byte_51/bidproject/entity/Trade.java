package com.byte_51.bidproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"seller","buyer","product"})

public class Trade extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeID;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member seller; //판매자

    @ManyToOne(fetch = FetchType.LAZY)
    private Member buyer; //구매자

    @OneToOne(fetch = FetchType.LAZY)
    private Product product; //거래제품

    private int price; //거래가격

    private int tradeState; //판매상태 1:금액 송금전 2: 물품인계전 3: 물품인수확인전 4:거래완료

    public void changeState(int state){
        this.tradeState = state;
    }
}
