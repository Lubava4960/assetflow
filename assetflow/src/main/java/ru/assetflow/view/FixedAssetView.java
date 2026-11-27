package ru.assetflow.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.assetflow.dto.assetCategory.AssetCategoryResponse;
import ru.assetflow.dto.fixedAsset.AssetTechnicalInfoRequest;
import ru.assetflow.dto.fixedAsset.AssetTechnicalInfoResponse;
import ru.assetflow.dto.fixedAsset.FixedAssetRequest;
import ru.assetflow.service.AssetCategory.AssetCategoryService;
import ru.assetflow.service.fixedAsset.FixedAssetService;
import ru.assetflow.dto.fixedAsset.FixedAssetResponse;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.datepicker.DatePicker;

import java.math.BigDecimal;


@Route("fixed-asset")
@PageTitle("Основные средства")

public class FixedAssetView extends VerticalLayout {

    private final FixedAssetService fixedAssetService;
    private final AssetCategoryService assetCategoryService;

    private final Grid<FixedAssetResponse> grid =
            new Grid<>(FixedAssetResponse.class, false);


    public FixedAssetView(FixedAssetService fixedAssetService, AssetCategoryService assetCategoryService) {
        this.fixedAssetService = fixedAssetService;
        this.assetCategoryService = assetCategoryService;

        H1 title = new H1("Основные средства");

        Button addButton = new Button(
                "Добавить основное средство",
                event -> openAssetDialog(null)
        );
        Button refreshButton = new Button(
                "Обновить",
                event -> refreshGrid()
        );
        configureGrid();
        HorizontalLayout header =
                new HorizontalLayout(title, addButton, refreshButton);

        header.setAlignItems(Alignment.CENTER);

        add(header, grid);

        refreshGrid();

        setSizeFull();

    }

    // Настройка таблицы
    private void configureGrid() {

        grid.addColumn(FixedAssetResponse::getId)
                .setHeader("ID");

        grid.addColumn(FixedAssetResponse::getName)
                .setHeader("Наименование");
        grid.addColumn(FixedAssetResponse::getInventoryNumber)
                .setHeader("Инвентарный номер");
        grid.addColumn(FixedAssetResponse::getCategoryName)
                .setHeader("Категория");
        grid.addColumn(FixedAssetResponse::getInitialCost)
                .setHeader("Первоначальная стоимость");
        grid.addColumn(FixedAssetResponse::getCommissioningDate)
                .setHeader("Дата ввода в эксплуатацию");

        grid.setSizeFull();
        grid.addItemDoubleClickListener(event -> {
            FixedAssetResponse selectedAsset = event.getItem();
            openAssetDialog(selectedAsset);
        });
    }

    private void openAssetDialog(FixedAssetResponse asset) {

        // 1. Создаём поле выбора категории
        ComboBox<AssetCategoryResponse> categoryComboBox =
                new ComboBox<>("Категория");

        categoryComboBox.setItems(assetCategoryService.getAllActive());
        categoryComboBox.setItemLabelGenerator(
                AssetCategoryResponse::getName
        );
        categoryComboBox.setWidthFull();

        // 2. Создаём остальные поля
        TextField inventoryNumberField =
                new TextField("Инвентарный номер");

        TextField nameField =
                new TextField("Наименование");

        TextField okofCodeField =
                new TextField("Код ОКОФ");

        IntegerField taxGroupField =
                new IntegerField("Амортизационная группа");

        BigDecimalField initialCostField =
                new BigDecimalField("Первоначальная стоимость");

        DatePicker acquisitionDateField =
                new DatePicker("Дата приобретения");

        DatePicker commissioningDateField =
                new DatePicker("Дата ввода в эксплуатацию");

        IntegerField accountingLifeField =
                new IntegerField("Срок полезного использования (бухгалтерский), мес.");

        IntegerField taxLifeField =
                new IntegerField("Срок полезного использования (налоговый), мес.");
        TextField manufacturerField =
                new TextField("Производитель");

        TextField modelField =
                new TextField("Модель");

        TextField serialNumberField =
                new TextField("Серийный номер");

        TextField passportNumberField =
                new TextField("Номер технического паспорта");

        DatePicker passportDateField =
                new DatePicker("Дата технического паспорта");

        TextArea technicalDescriptionField =
                new TextArea("Техническое описание");
        // 3. Создание диалога
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(
                asset == null
                        ? "Добавить основное средство"
                        : "Редактировать основное средство");
        if (asset != null) {

            inventoryNumberField.setValue(asset.getInventoryNumber());
            inventoryNumberField.setReadOnly(true);

            nameField.setValue(asset.getName());

            okofCodeField.setValue(asset.getOkofCode());

            taxGroupField.setValue(asset.getTaxDepreciationGroup());

            initialCostField.setValue(asset.getInitialCost());

            acquisitionDateField.setValue(asset.getAcquisitionDate());

            commissioningDateField.setValue(asset.getCommissioningDate());

            accountingLifeField.setValue(
                    asset.getAccountingUsefulLifeMonths()
            );

            taxLifeField.setValue(asset.getTaxUsefulLifeMonths());

            // Выбираем категорию
            categoryComboBox.setValue(
                    assetCategoryService.getAllActive().stream()
                            .filter(category ->
                                    category.getId().equals(asset.getCategoryId()))
                            .findFirst()
                            .orElse(null)
            );
            if (asset.getTechnicalInfo() != null) {

                AssetTechnicalInfoResponse technicalInfo =
                        asset.getTechnicalInfo();

                manufacturerField.setValue(
                        technicalInfo.getManufacturer() != null
                                ? technicalInfo.getManufacturer() : ""
                );

                modelField.setValue(
                        technicalInfo.getModel() != null
                                ? technicalInfo.getModel() : ""
                );

                serialNumberField.setValue(
                        technicalInfo.getSerialNumber() != null
                                ? technicalInfo.getSerialNumber() : ""
                );

                passportNumberField.setValue(
                        technicalInfo.getPassportNumber() != null
                                ? technicalInfo.getPassportNumber() : ""
                );

                passportDateField.setValue(technicalInfo.getPassportDate());

                technicalDescriptionField.setValue(
                        technicalInfo.getTechnicalDescription() != null
                                ? technicalInfo.getTechnicalDescription() : ""
                );
            }
        }

        // 4. Создание кнопки сохранения
        Button saveButton = new Button(
                asset == null ? "Сохранить" : "Сохранить изменения",
                event -> {

                    AssetCategoryResponse selectedCategory = categoryComboBox.getValue();

                    if (selectedCategory == null) {
                        Notification.show("Выберите категорию");
                        return;
                    }
                    if (inventoryNumberField.isEmpty()) {
                        Notification.show("Введите инвентарный номер");
                        return;
                    }

                    if (nameField.isEmpty()) {
                        Notification.show("Введите наименование");
                        return;
                    }

                    if (initialCostField.isEmpty() ||
                            initialCostField.getValue().compareTo(BigDecimal.ZERO) <= 0) {
                        Notification.show("Введите первоначальную стоимость больше нуля");
                        return;
                    }
                    if (okofCodeField.isEmpty()) {
                        Notification.show("Введите код ОКОФ");
                        return;
                    }
                    if (taxGroupField.isEmpty()) {
                        Notification.show("Введите амортизационную группу");
                        return;
                    }
                    if (commissioningDateField.isEmpty()) {
                        Notification.show("Введите дату ввода в эксплуатацию");
                        return;
                    }

                    if (accountingLifeField.isEmpty()) {
                        Notification.show("Введите срок полезного использования по бухгалтерскому учёту");
                        return;
                    }
                    if (taxLifeField.isEmpty()) {
                        Notification.show("Введите срок полезного использования по налоговому учёту");
                        return;
                    }


                    // Создание DTO
                    FixedAssetRequest request = new FixedAssetRequest();

                    request.setInventoryNumber(inventoryNumberField.getValue());
                    request.setName(nameField.getValue());
                    request.setCategoryId(selectedCategory.getId());
                    request.setOkofCode(okofCodeField.getValue());
                    request.setTaxDepreciationGroup(taxGroupField.getValue());
                    request.setInitialCost(initialCostField.getValue());
                    request.setAcquisitionDate(acquisitionDateField.getValue());
                    request.setCommissioningDate(commissioningDateField.getValue());
                    request.setAccountingUsefulLifeMonths(accountingLifeField.getValue());
                    request.setTaxUsefulLifeMonths(taxLifeField.getValue());

                    // Сохранение основного средства

                    AssetTechnicalInfoRequest technicalInfo =
                            new AssetTechnicalInfoRequest();

                    technicalInfo.setManufacturer(manufacturerField.getValue());
                    technicalInfo.setModel(modelField.getValue());
                    technicalInfo.setSerialNumber(serialNumberField.getValue());
                    technicalInfo.setPassportNumber(passportNumberField.getValue());
                    technicalInfo.setPassportDate(passportDateField.getValue());
                    technicalInfo.setTechnicalDescription(technicalDescriptionField.getValue());

                    request.setTechnicalInfo(technicalInfo);
                    if (asset == null) {
                        fixedAssetService.create(request);
                    } else {
                        fixedAssetService.update(asset.getId(), request);
                    }

                    // Обновление таблицы и закрытие диалога
                    refreshGrid();
                    dialog.close();

                    Notification.show(
                            asset == null
                                    ? "Основное средство успешно добавлено"
                                    : "Основное средство успешно обновлено"
                    );
                });

        // 5. Добавление поля и кнопки в диалог
        dialog.add(
                inventoryNumberField,
                nameField,
                categoryComboBox,
                okofCodeField,
                taxGroupField,
                initialCostField,
                acquisitionDateField,
                commissioningDateField,
                accountingLifeField,
                taxLifeField,
                // Технические характеристики
                manufacturerField,
                modelField,
                serialNumberField,
                passportNumberField,
                passportDateField,
                technicalDescriptionField
        );

        dialog.getFooter().add(saveButton);

        // 6. Открытие диалога
        dialog.open();
    }

    private void refreshGrid() {
        grid.setItems(
                fixedAssetService.getAllByOrderByInventoryNumberAsc()
        );
    }
}


