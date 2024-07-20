package com.project.skincare.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.skincare.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<Receipt> receipt = new ArrayList<>();

}
