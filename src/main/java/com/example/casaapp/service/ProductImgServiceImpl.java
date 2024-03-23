package com.example.casaapp.service;

import com.example.casaapp.domain.ProductImg;
import com.example.casaapp.repository.ProductImgRepository;
import com.querydsl.core.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class ProductImgServiceImpl implements ProductImgService {

    @Value("${productImgLocation}")
    private String productImgLocation;

    private final ProductImgRepository productImgRepository;

    private final FileService fileService;


    @Override
    @Transactional
    public void saveProductImg(ProductImg productImg, MultipartFile productImgFile) throws Exception {
        String oriImgName = productImgFile.getOriginalFilename();
        log.info("originImgName : " + oriImgName);
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if (oriImgName != null && !oriImgName.isEmpty()) {
            imgName = fileService.uploadFile(productImgLocation, oriImgName, productImgFile.getBytes());
            imgUrl = "/images/product/" + imgName;
            log.info("imgName" + imgName);
            log.info("imgUrl" + imgUrl);
        }

        //상품 이미지 정보 저장
        productImg.updateProductImg(oriImgName, imgName, imgUrl);
        productImgRepository.save(productImg);
    }


    public void updateProductImg(Long productImgId, MultipartFile productImgFile) throws Exception {
        if (!productImgFile.isEmpty()) {
            ProductImg saveProductImg = productImgRepository.findById(productImgId)
                    .orElseThrow(EntityNotFoundException::new);
            //기존 이미지 파일 삭제
            if (!StringUtils.isNullOrEmpty(saveProductImg.getImgName())) {
                fileService.deleteFile(productImgLocation + "/" + saveProductImg.getImgName());
            }
            String oriImgName = productImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(productImgLocation, oriImgName, productImgFile.getBytes());
            String imgUrl = "/images/product/" + imgName;
            saveProductImg.updateProductImg(oriImgName, imgName, imgUrl);
        }
    }
}
