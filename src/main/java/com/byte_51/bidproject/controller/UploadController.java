package com.byte_51.bidproject.controller;


import com.byte_51.bidproject.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile: uploadFiles){
            if(uploadFile.getContentType().startsWith("image") == false){ //업로드 파일이 이미지가 아니면 에러 반환
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String orgFileName = uploadFile.getOriginalFilename();
            String fileName = orgFileName.substring(orgFileName.lastIndexOf("\\") + 1); //파일이름만 추출하기
            
            String folderPath = makeFolder(); //업로드 폴더 생성
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;//풀 경로

            Path savePath = Paths.get(saveName);

            try{
                uploadFile.transferTo(savePath); //업로드 된 파일 저장

                ///////썸네일 파일 생성////////////
                String thumbSaveName110 = uploadPath + File.separator + folderPath + File.separator + "s110_" + uuid + "_" + fileName;//110크기 썸네일 풀 경로
                String thumbSaveName150 = uploadPath + File.separator + folderPath + File.separator + "s150_" + uuid + "_" + fileName;//150크기 썸네일 풀 경로
                String thumbSaveName330 = uploadPath + File.separator + folderPath + File.separator + "s330_" + uuid + "_" + fileName;//330크기 썸네일 풀 경로
                File thumbnailFile110 = new File(thumbSaveName110);
                File thumbnailFile150 = new File(thumbSaveName150);
                File thumbnailFile330 = new File(thumbSaveName330);

                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile110, 110, 110);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile150, 150, 150);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile330, 330, 330);

                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDTOList,HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){
        ResponseEntity<byte[]> result = null;

        try{
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);

            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }
    
    private String makeFolder(){
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);
        
        File uploadPathFolder = new File(uploadPath, folderPath);
        
        if(!uploadPathFolder.exists()) {//폴더가 존재하지 않는 경우
            uploadPathFolder.mkdirs(); //폴더생성
        }
        
        return folderPath; //폴더 경로 반환
    }

}
