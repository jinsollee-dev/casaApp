package com.example.casaapp.service;

import com.example.casaapp.domain.ProductImg;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImgService {

    void saveProductImg(ProductImg productImg, MultipartFile multipartFile) throws Exception;

    void updateProductImg(Long productImgId, MultipartFile productImgFile) throws Exception;
}
