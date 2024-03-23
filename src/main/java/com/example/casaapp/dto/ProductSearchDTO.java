package com.example.casaapp.dto;

import com.example.casaapp.type.Sellstatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductSearchDTO {

    //현재 시간과 상품 등록일을 비교하여 검색
    private String searchDateType;

    //상품 판매 상태 기준
    private Sellstatus searchSellStatus;

    //상품명 or 상품 등록자 아이디
    private String searchBy;

    //검색어를 저장하는 변수
    private String searchQuery= "";



}
