package com.byte_51.bidproject.service;

import com.byte_51.bidproject.dto.PageRequestDTO;
import com.byte_51.bidproject.dto.PageResultDTO;
import com.byte_51.bidproject.dto.ProductDTO;
import com.byte_51.bidproject.dto.ProductImageDTO;
import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.Product;
import com.byte_51.bidproject.entity.ProductImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ProductService {
    Long register(ProductDTO productDTO);

    ProductDTO getProduct(Long pdNum);

    PageResultDTO<ProductDTO,Object[]> getListAll(PageRequestDTO requestDTO);

    PageResultDTO<ProductDTO,Product> getListAllByMember(PageRequestDTO requestDTO, String email);

    default ProductDTO entitiesToDTO(Product product, List<ProductImage> productImages, Member member, Long bidCount){
        ProductDTO productDTO = ProductDTO.builder()
                .pdNum(product.getPdNum())
                .email(member.getEmail())
                .name(member.getName())
                .pdName(product.getPdName())
                .pdContent(product.getPdContent())
                .endDate(product.getEndDate())
                .minBidPrice(product.getMinBidPrice())
                .bidCount(bidCount)

                .category(product.getCatagory())
                .regDate(product.getRegDate())
                .modDate(product.getModDate())
                .build();

        List<ProductImageDTO> productImageDTOList = productImages.stream().map(imgdata->{
            return ProductImageDTO.builder()
                    .imgName(imgdata.getImgName())
                    .path(imgdata.getPath())
                    .uuid(imgdata.getUuid())
                    .build();
        }).collect(Collectors.toList());

        productDTO.setImageDTOList(productImageDTOList);

        return productDTO;
    }

    default Map<String,Object> dtoToEntity(ProductDTO productDTO){
        Map<String,Object> entMap = new HashMap<>();

        Product product = Product.builder()
                .catagory(productDTO.getCategory())
                .pdName(productDTO.getPdName())
                .pdContent(productDTO.getPdContent())
                .minBidPrice(productDTO.getMinBidPrice())
                .endDate(productDTO.getEndDate())
                .member(Member.builder().email(productDTO.getEmail()).build())
                .build();

        List<ProductImageDTO> imageDTOList = productDTO.getImageDTOList();
        List<ProductImage> productImageList = imageDTOList.stream().map(dto->{
            ProductImage productImage = ProductImage.builder()
                    .path(dto.getPath())
                    .imgName(dto.getImgName())
                    .uuid(dto.getUuid())
                    .product(product)
                    .build();
            return productImage;
        }).collect(Collectors.toList());

        entMap.put("product",product);
        entMap.put("productImageList", productImageList);

        return entMap;

    }
}
