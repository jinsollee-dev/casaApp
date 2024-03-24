package com.example.casaapp.controller;

import com.example.casaapp.domain.Product;
import com.example.casaapp.dto.ProductSearchDTO;
import com.example.casaapp.dto.RequestProductDTO;
import com.example.casaapp.dto.ResponsePageDTO;
import com.example.casaapp.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    //상품 등록
    @PostMapping("/product/addNew")
    public ResponseEntity<String> addNew(@ModelAttribute RequestProductDTO productDTO,
                                         @RequestParam("file") List<MultipartFile> productImgFileList) {
        log.info(productDTO);
        for (MultipartFile file : productImgFileList) {
            log.info("File Name: " + file.getOriginalFilename());
            log.info("Content Type: " + file.getContentType());
            log.info("File Size: " + file.getSize());
            // 필요한 다른 정보들도 필요에 따라 출력
        }

        if (productImgFileList.get(0).isEmpty() && productDTO.getPname() == null) {
            return new ResponseEntity<String>("bad", HttpStatus.BAD_REQUEST);
        }
        try {
            productService.saveProduct(productDTO, productImgFileList);
        } catch (Exception e) {
            return new ResponseEntity<String>("상품 등록중 에러가 발생함", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Success", HttpStatus.OK);

    }

    //상품 정보 가져오기
    @GetMapping("/product/detail/{productId}")
    public RequestProductDTO productDtl(@PathVariable("productId") Long productId) {
        try {
            RequestProductDTO productDTO = productService.getProductDtl(productId);
            return productDTO;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @GetMapping("/images")
    public ResponseEntity<Resource> getImage(@RequestParam("imageName") String imageName) {
        // 이미지가 저장된 경로
        String imagePath = "C:/casa/product/" + imageName; // 이미지 경로를 업로드 경로와 이미지 이름을 조합하여 설정합니다.

        try {
            // 파일을 읽어서 Resource 객체 생성
            Path file = Paths.get(imagePath);
            Resource resource = new UrlResource(file.toUri());

            // 이미지가 존재하는지 확인
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // 이미지의 MediaType을 설정합니다. JPEG이라면 image/jpeg으로 설정합니다.
                        .body(resource); // 이미지를 응답의 바디에 포함시킵니다.
            } else {
                return ResponseEntity.notFound().build(); // 이미지를 찾을 수 없는 경우 404 응답을 반환합니다.
            }
        } catch (Exception e) {
            // 에러 발생시 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 내부 서버 오류가 발생한 경우 500 응답을 반환합니다.
        }
    }

    @PostMapping("/admin/product/{productId}")
    public String productUpdate(@ModelAttribute RequestProductDTO productDTO,
                                @RequestParam("file") List<MultipartFile> productImgFileList) {
        if (productImgFileList.get(0).isEmpty() && productDTO.getId() == null) {
            return "첫번째 상품 이미지는 필수 입력 값입니다.";
        }
        try {
            log.info(productDTO.toString());
            log.info(productImgFileList.toString());
            productService.updateProduct(productDTO, productImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            return "오류입니다";
        }
        return "변경 성공";
    }

    @GetMapping(value = {"/admin/products/", "/admin/products/{page}"})
    public ResponsePageDTO productManage(ProductSearchDTO productSearchDTO, @PathVariable("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 1);
        log.info(productSearchDTO.toString());
        Page<Product> products = productService.getAdminProductPage(productSearchDTO, pageable);
        ResponsePageDTO responsePageDTO = ResponsePageDTO.builder()
                .productPage(products)
                .productSearchDTO(productSearchDTO)
                .maxPage(5)
                .build();
        return responsePageDTO;
    }
}