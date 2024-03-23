package com.example.casaapp.repository;

import com.example.casaapp.domain.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findByProductIdOrderByIdAsc(Long productId);
}
