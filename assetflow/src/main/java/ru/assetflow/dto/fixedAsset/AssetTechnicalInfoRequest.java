package ru.assetflow.dto.fixedAsset;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AssetTechnicalInfoRequest {
    @Size(max = 150)
    private String manufacturer;

    @Size(max = 100)
    private String model;

    @Size(max = 100)
    private String serialNumber;

    @Size(max = 100)
    private String passportNumber;

    private LocalDate passportDate;

    @Size(max = 500)
    private String technicalDescription;
}
