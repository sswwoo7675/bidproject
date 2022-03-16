package com.byte_51.bidproject.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BiddingDTO {
    private Long bidNum; //입찰번호
    private String bidMember; //입찰자 EMAIL
    private LocalDateTime bidDate; //입찰일자
    private int bidPrice; //신청입찰가
    private Long pdNum; //입찰대상상품번호
    private String pdName; //상품명
}
