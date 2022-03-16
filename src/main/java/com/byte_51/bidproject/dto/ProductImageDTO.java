package com.byte_51.bidproject.dto;

import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductImageDTO {
    private String uuid;
    private String imgName;
    private String path;

    public String getImageURL(){
        try{
            return URLEncoder.encode(path + "/" + uuid + "_" + imgName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnail110URL(){
        try{
            return URLEncoder.encode(path + "/s110_" + uuid + "_" + imgName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnail150URL(){
        try{
            return URLEncoder.encode(path + "/s150_" + uuid + "_" + imgName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnail330URL(){
        try{
            return URLEncoder.encode(path + "/s330_" + uuid + "_" + imgName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
