package com.stevy.contratti.models;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditMetadata {
    /*
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private User createdBy;
     */

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdDate;

    /*
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private User lastModifiedBy;
    */
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
}
