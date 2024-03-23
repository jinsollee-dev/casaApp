package com.example.casaapp.repository;

import com.example.casaapp.domain.Product;
import com.example.casaapp.dto.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getAdminProductPage(ProductSearchDTO productSearchDTO, Pageable pageable);
}
