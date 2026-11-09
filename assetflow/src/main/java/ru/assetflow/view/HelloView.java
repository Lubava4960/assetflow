package ru.assetflow.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("hello")
public class HelloView extends VerticalLayout {
    public HelloView() {
        H1 title = new H1("Приветствую на своей странице");
        Button button = new Button("нажми меня");

        button.addClickListener(event -> {
            Notification.show("Добро пожаловать в AssetFlow!");
        });

        Button secondButton = new Button("Информация");


        secondButton.addClickListener(event -> {
            Notification.show("AssetFlow — система учёта основных средств");
        });
        add(title, button,secondButton);
    }
}