package com.project.skincare.entity;

import com.project.skincare.model.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "receipt")
@Getter
@Setter
public class Receipt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    private String time;

    @Column(name = "level")
    private String level;

    @ManyToMany(fetch = FetchType.EAGER , cascade = ALL)
    private List<Category> categories = new ArrayList<>();

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private List<ReceiptInformation> ingredients;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private List<ReceiptInformation> instructions;

}
