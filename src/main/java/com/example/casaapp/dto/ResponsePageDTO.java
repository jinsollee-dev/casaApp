package com.example.casaapp.dto;

import com.example.casaapp.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;


@Getter
@Setter
@ToString
@Builder
public class ResponsePageDTO {
    private Page<Product> productPage;
    private ProductSearchDTO productSearchDTO;
    private int maxPage;
}
