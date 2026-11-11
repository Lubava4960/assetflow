package ru.assetflow.mapper;

import org.springframework.stereotype.Component;
import ru.assetflow.db.assetfiow.entity.AssetTechnicalInfo;
import ru.assetflow.dto.fixedAsset.AssetTechnicalInfoRequest;
import ru.assetflow.dto.fixedAsset.AssetTechnicalInfoResponse;

@Component
public class AssetTechnicalInfoMapper {
    public AssetTechnicalInfo toEntity(AssetTechnicalInfoRequest request) {
        if (request == null) {
            return null;
        }
        return AssetTechnicalInfo.builder()
                .manufacturer(request.getManufacturer())
                .model(request.getModel())
                .serialNumber(request.getSerialNumber())
                .passportNumber(request.getPassportNumber())
                .passportDate(request.getPassportDate())
                .technicalDescription(request.getTechnicalDescription())
                .build();

    }
    public AssetTechnicalInfoResponse toResponse(AssetTechnicalInfo technicalInfo) {
        if (technicalInfo == null) {
            return null;
        }
        return AssetTechnicalInfoResponse.builder()
                .manufacturer(technicalInfo.getManufacturer())
                .model(technicalInfo.getModel())
                .serialNumber(technicalInfo.getSerialNumber())
                .passportNumber(technicalInfo.getPassportNumber())
                .passportDate(technicalInfo.getPassportDate())
                .technicalDescription(technicalInfo.getTechnicalDescription())
                .build();
    }
}
