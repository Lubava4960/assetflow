package ru.assetflow.db.assetfiow.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "asset_categories")
public class AssetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    private Integer defaultUsefulLifeMonths;

    @Column(nullable = false)
    private boolean active = true;

    @Builder
    public AssetCategory(String code, String name,
                         String description,
                         Integer defaultUsefulLifeMonths) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.defaultUsefulLifeMonths = defaultUsefulLifeMonths;
    }
}
