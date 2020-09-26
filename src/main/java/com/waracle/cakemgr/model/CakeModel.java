package com.waracle.cakemgr.model;


import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@DynamicUpdate
@Table(name = "cake", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class CakeModel implements Serializable {

    private static final long serialVersionUID = -2417760290457013668L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", unique = true, nullable = false)
    @NotEmpty
    private String title;

    @Column(name = "desc", nullable = false)
    @NotEmpty
    private String desc;

    @Column(name = "image", nullable = false)
    @NotEmpty
    private String image;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CakeModel cakeModel = (CakeModel) o;

        return Objects.equals(id, cakeModel.id) &&
                Objects.equals(title, cakeModel.title) &&
                Objects.equals(desc, cakeModel.desc) &&
                Objects.equals(image, cakeModel.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, desc, image);
    }
}
