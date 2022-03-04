package com.example.payment.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Setter
@Getter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(name="create_at",updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updateAt;
//
//    @Column(name ="status")
//    private int status;
}
