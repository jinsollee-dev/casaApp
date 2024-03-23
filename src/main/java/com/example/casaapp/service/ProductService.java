package com.example.casaapp.service;

import com.example.casaapp.domain.Product;
import com.example.casaapp.dto.ProductSearchDTO;
import com.example.casaapp.dto.RequestProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Long saveProduct(RequestProductDTO productDTO, List<MultipartFile> imgFileDTO) throws Exception;

    RequestProductDTO getProductDtl(Long productId);

    Long updateProduct(RequestProductDTO productDTO, List<MultipartFile> productImgFileList) throws Exception;

    public Page<Product> getAdminProductPage(ProductSearchDTO productSearchDTO,
                                             Pageable pageable);
}
