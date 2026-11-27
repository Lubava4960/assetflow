package ru.assetflow.db.assetfiow.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
@Builder
public class AssetTechnicalInfo {
    private String manufacturer;

    private String model;

    private String serialNumber;

    private String passportNumber;

    private LocalDate passportDate;

    private String technicalDescription;

}
