package ru.assetflow.db.assetfiow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Embedded;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "fixed_assets")
public class FixedAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_number",
            nullable = false, unique = true, length = 50)
    private String inventoryNumber;

    @Column(nullable = false, length = 255)
    private String name;

    // Связь с видом основного средства
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private AssetCategory category;

    @Column(name = "okof_code", length = 20)
    private String okofCode;

    private Integer taxDepreciationGroup;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal initialCost;

    private LocalDate acquisitionDate;

    private LocalDate commissioningDate;

    private Integer accountingUsefulLifeMonths;

    private Integer taxUsefulLifeMonths;

    @Embedded
    private AssetTechnicalInfo technicalInfo;

    @Builder
    public FixedAsset(
            String inventoryNumber,
            String name,
            AssetCategory category,
            String okofCode,
            Integer taxDepreciationGroup,
            BigDecimal initialCost,
            LocalDate acquisitionDate,
            LocalDate commissioningDate,
            Integer accountingUsefulLifeMonths,
            Integer taxUsefulLifeMonths,
            AssetTechnicalInfo technicalInfo
    ) {
        this.inventoryNumber = inventoryNumber;
        this.name = name;
        this.category = category;
        this.okofCode = okofCode;
        this.taxDepreciationGroup = taxDepreciationGroup;
        this.initialCost = initialCost;
        this.acquisitionDate = acquisitionDate;
        this.commissioningDate = commissioningDate;
        this.accountingUsefulLifeMonths = accountingUsefulLifeMonths;
        this.taxUsefulLifeMonths = taxUsefulLifeMonths;
        this.technicalInfo = technicalInfo;
    }
}

