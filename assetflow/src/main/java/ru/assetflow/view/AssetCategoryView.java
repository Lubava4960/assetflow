package ru.assetflow.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;


import ru.assetflow.dto.assetCategory.AssetCategoryRequest;
import ru.assetflow.dto.assetCategory.AssetCategoryResponse;
import ru.assetflow.dto.assetCategory.AssetCategoryUpdateRequest;
import ru.assetflow.service.AssetCategory.AssetCategoryService;

import java.util.NoSuchElementException;


@Route("asset-categories")
@PageTitle("Виды основных средств")
public class AssetCategoryView  extends VerticalLayout {
    private final AssetCategoryService service;

    private final Grid<AssetCategoryResponse> grid =
            new Grid<>(AssetCategoryResponse.class, false);


    public AssetCategoryView(AssetCategoryService service) {
        this.service = service;

        H1 title = new H1("Виды основных средств");

        Button addButton = new Button(
                "Добавить категорию",
                event -> openCreateDialog()
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

        grid.addColumn(AssetCategoryResponse::getId)
                .setHeader("ID");

        grid.addColumn(AssetCategoryResponse::getCode)
                .setHeader("Код");

        grid.addColumn(AssetCategoryResponse::getName)
                .setHeader("Наименование");

        grid.addColumn(AssetCategoryResponse::getDescription)
                .setHeader("Описание");


        grid.addColumn(AssetCategoryResponse::isActive)
                .setHeader("Активна");
        grid.addComponentColumn(category -> {

            Button editButton = new Button(
                    "Изменить",
                    event -> openEditDialog(category)
            );

            Button deactivateButton = new Button(
                    "Деактивировать",
                    event -> confirmDeactivation(category)
            );

            HorizontalLayout actions =
                    new HorizontalLayout(editButton, deactivateButton);

            return actions;

        }).setHeader("Действия").setAutoWidth(true);
        grid.setSizeFull();
    }

    // Создание диалогового окна
    private void openCreateDialog() {

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Добавить категорию");
        dialog.setWidth("500px");

        TextField codeField = new TextField("Код");
        codeField.setMaxLength(30);
        codeField.setWidthFull();

        TextField nameField = new TextField("Наименование");
        nameField.setMaxLength(150);
        nameField.setWidthFull();

        TextArea descriptionField = new TextArea("Описание");
        descriptionField.setMaxLength(500);
        descriptionField.setWidthFull();


        // Связь поля формы с DTO
        Binder<AssetCategoryRequest> binder =
                new Binder<>(AssetCategoryRequest.class);

        binder.forField(codeField)
                .asRequired("Укажите код")
                .withValidator(
                        value -> !value.isBlank(),
                        "Код не должен состоять только из пробелов"
                )
                .bind(
                        AssetCategoryRequest::getCode,
                        AssetCategoryRequest::setCode
                );

        binder.forField(nameField)
                .asRequired("Укажите наименование")
                .withValidator(
                        value -> !value.isBlank(),
                        "Наименование не должно состоять только из пробелов"
                )
                .bind(
                        AssetCategoryRequest::getName,
                        AssetCategoryRequest::setName
                );

        binder.forField(descriptionField)
                .bind(
                        AssetCategoryRequest::getDescription,
                        AssetCategoryRequest::setDescription
                );


        VerticalLayout form = new VerticalLayout(
                codeField,
                nameField,
                descriptionField

        );

        form.setPadding(false);

        dialog.add(form);

        Button cancelButton = new Button(
                "Отмена",
                event -> dialog.close()
        );

        Button saveButton = new Button(
                "Сохранить",
                event -> saveCategory(dialog, binder)
        );

        dialog.getFooter().add(cancelButton, saveButton);

        dialog.open();
    }

    // Сохранение категории
    private void saveCategory(
            Dialog dialog,
            Binder<AssetCategoryRequest> binder
    ) {

        AssetCategoryRequest request =
                new AssetCategoryRequest();

        try {
            binder.writeBean(request);

            AssetCategoryResponse created =
                    service.create(request);

            dialog.close();

            refreshGrid();

            Notification.show(
                    "Категория добавлена: " + created.getName(),
                    3000,
                    Notification.Position.BOTTOM_START
            );

        } catch (ValidationException e) {

            Notification.show(
                    "Проверьте заполнение полей",
                    3000,
                    Notification.Position.MIDDLE
            );

        } catch (IllegalArgumentException e) {

            Notification.show(
                    e.getMessage(),
                    4000,
                    Notification.Position.MIDDLE
            );
        }
    }

    // Обновление таблицы
    private void refreshGrid() {
        grid.setItems(service.getAllActive());
    }
    private void openEditDialog(AssetCategoryResponse category) {

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Изменить категорию");
        dialog.setWidth("500px");

        TextField nameField = new TextField("Наименование");
        nameField.setMaxLength(150);
        nameField.setWidthFull();
        nameField.setValue(category.getName());

        TextArea descriptionField = new TextArea("Описание");
        descriptionField.setMaxLength(500);
        descriptionField.setWidthFull();

        descriptionField.setValue(
                category.getDescription() == null
                        ? ""
                        : category.getDescription()
        );

        Binder<AssetCategoryUpdateRequest> binder = new Binder<>();

        binder.forField(nameField)
                .asRequired("Укажите наименование")
                .withValidator(
                        value -> !value.isBlank(),
                        "Наименование не должно состоять из пробелов"
                )
                .bind(
                        AssetCategoryUpdateRequest::getName,
                        AssetCategoryUpdateRequest::setName
                );

        binder.forField(descriptionField)
                .bind(
                        AssetCategoryUpdateRequest::getDescription,
                        AssetCategoryUpdateRequest::setDescription
                );

        VerticalLayout form = new VerticalLayout(
                nameField,
                descriptionField
        );

        form.setPadding(false);
        dialog.add(form);

        Button cancelButton = new Button(
                "Отмена",
                event -> dialog.close()
        );

        Button saveButton = new Button(
                "Сохранить",
                event -> updateCategory(category.getId(), dialog, binder)
        );

        dialog.getFooter().add(cancelButton, saveButton);

        dialog.open();
    }
    private void updateCategory(
            Long id,
            Dialog dialog,
            Binder<AssetCategoryUpdateRequest> binder) {

        AssetCategoryUpdateRequest request =
                new AssetCategoryUpdateRequest();

        try {
            binder.writeBean(request);

            service.update(id, request);

            dialog.close();
            refreshGrid();

            Notification.show("Категория успешно изменена");

        } catch (ValidationException e) {

            Notification.show("Проверьте заполнение полей");

        } catch (NoSuchElementException e) {

            Notification.show(e.getMessage());
        }
    }
    // Подтверждение деактивации категории
    private void confirmDeactivation(AssetCategoryResponse category) {

        ConfirmDialog dialog = new ConfirmDialog();

        dialog.setHeader("Деактивация категории");

        dialog.setText(
                "Вы действительно хотите деактивировать категорию \""
                        + category.getName() + "\"?"
        );

        dialog.setCancelable(true);
        dialog.setCancelText("Отмена");

        dialog.setConfirmText("Деактивировать");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(event -> {

            try {
                service.deactivate(category.getId());

                refreshGrid();

                Notification.show("Категория успешно деактивирована");

            } catch (NoSuchElementException e) {

                Notification.show(e.getMessage());
            }
        });

        dialog.open();
    }
}