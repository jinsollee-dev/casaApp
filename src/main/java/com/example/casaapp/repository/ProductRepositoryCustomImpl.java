package com.example.casaapp.repository;

import com.example.casaapp.domain.Product;
import com.example.casaapp.domain.QProduct;
import com.example.casaapp.dto.ProductSearchDTO;
import com.example.casaapp.type.Sellstatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory((em));
    }

    //where절 조건 : 판매상태 "판매중","품절"
    private BooleanExpression searchSellStatusEq(Sellstatus searchSellStatus) {
        return searchSellStatus == null ? null : QProduct.product.sellStatus.eq(searchSellStatus);
    }

    //where절 조건 : 시간 : 1일전, 일주일전, 한달전, 6개월전
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return QProduct.product.regDate.after(dateTime);
    }


    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("pname", searchBy)) {
            return QProduct.product.pname.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Product> getAdminProductPage(ProductSearchDTO productSearchDTO, Pageable pageable) {
        QueryResults<Product> results = queryFactory
                .selectFrom(QProduct.product)
                .where(regDtsAfter(productSearchDTO.getSearchDateType()),
                        searchSellStatusEq(productSearchDTO.getSearchSellStatus()),
                        searchByLike(productSearchDTO.getSearchBy(),
                                productSearchDTO.getSearchQuery()))
                .orderBy(QProduct.product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Product> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
