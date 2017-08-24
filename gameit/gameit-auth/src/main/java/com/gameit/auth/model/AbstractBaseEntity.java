package com.gameit.auth.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@MappedSuperclass
@EntityListeners({AbstractBaseEntity.AbstractEntityListener.class})
public abstract class AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(updatable = false, nullable = false, length = 36)
    private String id;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    //Hibernate supports uuid but we should still use it here so we do not
    //have to rely on Hibernate

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id != null) {
            this.id = id;
        } else {
            this.id = UUID.randomUUID().toString();
        }
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractBaseEntity that = (AbstractBaseEntity) o;

        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public static class AbstractEntityListener {
        @PrePersist
        protected void onPrePersist(AbstractBaseEntity abstractEntity) {
            if (abstractEntity.getId() == null) {
                abstractEntity.setId(null);
            }
            abstractEntity.setCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            abstractEntity.setUpdatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        }

        @PreUpdate
        protected void onPreUpdate(AbstractBaseEntity abstractEntity) {
            abstractEntity.setUpdatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        }
    }
}
