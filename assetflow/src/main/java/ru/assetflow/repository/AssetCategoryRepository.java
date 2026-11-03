package ru.assetflow.repository;

import ru.assetflow.db.assetfiow.entity.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {
    Optional<AssetCategory> findByCode(String code);

    boolean existsByCode(String code);

    List<AssetCategory> findAllByActiveTrueOrderByNameAsc();
}

