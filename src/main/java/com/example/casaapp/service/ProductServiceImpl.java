package com.example.casaapp.service;


import com.example.casaapp.domain.Product;
import com.example.casaapp.domain.ProductImg;
import com.example.casaapp.dto.ProductImgDTO;
import com.example.casaapp.dto.ProductSearchDTO;
import com.example.casaapp.dto.RequestProductDTO;
import com.example.casaapp.repository.ProductImgRepository;
import com.example.casaapp.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImgService productImgService;
    private final ProductImgRepository productImgRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long saveProduct(RequestProductDTO productDTO, List<MultipartFile> productImgFileList) throws Exception {

        //상품 등록
        Product product = modelMapper.map(productDTO, Product.class);
        productRepository.save(product);

        //이미지 등록
        for (int i = 0; i < productImgFileList.size(); i++) {
            ProductImg productImg = new ProductImg();
            productImg.setProduct(product);
            if (i == 0)
                productImg.setRepimgYn("Y");
            else
                productImg.setRepimgYn("N");
            log.info(productImgFileList.get(i));
            productImgService.saveProductImg(productImg, productImgFileList.get(i));
        }
        return product.getId();
    }

    //상품, 상품 이미지 정보 가져오기
    public RequestProductDTO getProductDtl(Long productId) {
        List<ProductImg> productImgList = productImgRepository.findByProductIdOrderByIdAsc(productId);
        List<ProductImgDTO> productImgDTOList = productImgList.stream()
                .map(productImg -> modelMapper.map(productImg, ProductImgDTO.class))
                .collect(Collectors.toList());

        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        RequestProductDTO productDTO = modelMapper.map(product, RequestProductDTO.class);
        productDTO.setProductImgDTOList(productImgDTOList);
        return productDTO;


    }

    @Override
    public Long updateProduct(RequestProductDTO productDTO,
                              List<MultipartFile> productImgFileList) throws Exception {

        //상품 수정
        log.info(productDTO.getId());
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        product.updateItem(productDTO);

        List<Long> productImgIds = productDTO.getProductImgIds();

        for (int i = 0; i < productImgFileList.size(); i++) {
            productImgService.updateProductImg(productImgIds.get(i),
                    productImgFileList.get(i));
        }
        return product.getId();
    }

    @Transactional(readOnly = true)
    public Page<Product> getAdminProductPage(ProductSearchDTO productSearchDTO,
                                             Pageable pageable) {
        return productRepository.getAdminProductPage(productSearchDTO, pageable);
    }

}
