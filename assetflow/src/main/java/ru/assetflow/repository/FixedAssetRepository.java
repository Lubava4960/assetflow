package ru.assetflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.assetflow.db.assetfiow.entity.FixedAsset;

import java.util.List;
import java.util.Optional;

public interface FixedAssetRepository extends JpaRepository<FixedAsset, Long> {
    // Проверка уникальности инвентарного номера
    boolean existsByInventoryNumber(String inventoryNumber);

    // Поиск по инвентарному номеру
    Optional<FixedAsset> findByInventoryNumber(String inventoryNumber);

    // Получить все основные средства по инвентарному номеру
    List<FixedAsset> findAllByOrderByInventoryNumberAsc();
}
