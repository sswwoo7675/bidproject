package com.byte_51.bidproject.scheduler;

import com.byte_51.bidproject.entity.Bidding;
import com.byte_51.bidproject.entity.Product;
import com.byte_51.bidproject.entity.Trade;
import com.byte_51.bidproject.repository.BiddingRepository;
import com.byte_51.bidproject.repository.ProductImageRepository;
import com.byte_51.bidproject.repository.ProductRepository;
import com.byte_51.bidproject.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class testscheduler {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final BiddingRepository biddingRepository;
    private final TradeRepository tradeRepository;

/*    @Scheduled(cron = "0 0/1 * * * *")
    @Transactional
    public void testsc1(){
        LocalDateTime targetTime = LocalDateTime.now().withMinute(59).withSecond(59).withNano(999999999);

        List<Product> targetProduct = productRepository.getProductByTime(targetTime);

        for(Product p : targetProduct){
            List<Bidding> biddingList = biddingRepository.getBiddingByProduct(p);

            if(!biddingList.isEmpty()){ //입찰내역시 존재할 시 trade 테이블에 정보를 삽입.
                Trade trade = Trade.builder()
                        .seller(p.getMember())
                        .buyer(biddingList.get(0).getMember())//가장 상위 입찰자
                        .product(p)
                        .price(biddingList.get(0).getBidPrice())//가장 상위 입찰가격
                        .tradeState(1) //낙찰가 송금전
                        .build();
                
                tradeRepository.save(trade);
                //biddingRepository.deleteByProduct(p);
            }
            //productImageRepository.deleteByProduct(p);
            //productRepository.deleteByMyID(p.getPdNum());
            p.changeVisible(0);
            productRepository.save(p);
        }
        System.out.println("작업완료");
    }*/

}
