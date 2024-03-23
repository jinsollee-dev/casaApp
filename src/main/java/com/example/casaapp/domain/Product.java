package com.example.casaapp.domain;

import com.example.casaapp.dto.RequestProductDTO;
import com.example.casaapp.type.Sellstatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String pname;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String productDetail;

    @Enumerated(EnumType.STRING)
    private Sellstatus sellStatus;

    private String category;

    public void updateItem(RequestProductDTO productDTO){
        this.pname = productDTO.getPname();
        this.price = productDTO.getPrice();
        this.stockNumber = productDTO.getStockNumber();
        this.productDetail = productDTO.getProductDetail();
        this.sellStatus = productDTO.getSellStatus();
    }

}
