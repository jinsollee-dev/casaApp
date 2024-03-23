package com.example.casaapp.dto;

import com.example.casaapp.type.Sellstatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestProductDTO {

    private Long id;

    @NotNull
    private String pname;
    @NotNull
    private int price;
    @NotNull
    private int stockNumber;
    @NotNull
    private String productDetail;
    @NotNull
    private Sellstatus sellStatus;
    @NotNull
    private String category;

    private List<ProductImgDTO> productImgDTOList = new ArrayList<>();

    private List<Long> productImgIds = new ArrayList<>();




}
