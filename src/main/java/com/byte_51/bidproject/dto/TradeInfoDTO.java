package com.byte_51.bidproject.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TradeInfoDTO {
    private Long tradeID; //거래번호
    private String pdName; //상품명
    private String seller; //판매자
    private String buyer; //구매자
    private int price; //가격
    private int state; //거래상태
}
