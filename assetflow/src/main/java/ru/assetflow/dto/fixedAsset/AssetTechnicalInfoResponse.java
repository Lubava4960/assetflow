package ru.assetflow.dto.fixedAsset;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AssetTechnicalInfoResponse {
    private String manufacturer;

    private String model;

    private String serialNumber;

    private String passportNumber;

    private LocalDate passportDate;

    private String technicalDescription;
}
