package com.byte_51.bidproject.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {
    private Long pdNum;
    private int category;
    private String pdName;
    private String pdContent;
    private int minBidPrice;
    private int bidPlus;
    
    private Long bidCount;//입찰개수
    private int maxBid; //최고입찰가격
    
    private LocalDateTime endDate;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    

    private String email;
    private String name;

    private List<ProductImageDTO> imageDTOList;
}
