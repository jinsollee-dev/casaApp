package com.example.casaapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 모든 테이블에 칼럼 지정 클래스
@EntityListeners(value = { AuditingEntityListener.class })  //엔티티가 데이터베이스에 추가되거나 수정될때 자동으로 시간값 설정
@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="mod_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modDate;

}