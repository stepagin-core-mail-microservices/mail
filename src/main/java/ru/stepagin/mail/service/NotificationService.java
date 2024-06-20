package ru.stepagin.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.stepagin.mail.entity.ImageEntity;
import ru.stepagin.mail.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmailService emailService;
    @Value(value = "${app.endpoints.core-url}")
    private String coreUrl;


    public void sendGreetingMessage(UserEntity userEntity) {
        if (userEntity.isBlocked())
            return;
        String subject = "Добро пожаловать! Тестовое задание в компанию Аболъ";
        String content = "<p>Это приветственное сообщение.<br><br>" +
                "Задание выполнил: Степан Понамарев<br>" +
                "GitHub: https://github.ru/stepagin<br>" +
                "Проект: https://github.com/stepagin-core-mail-microservices<br></p>";

        emailService.sendEmail(userEntity.getEmail(), subject, content);
    }

    public void sendImageCreatedMessage(ImageEntity imageEntity, UserEntity owner) {
        String subject = "Загружено новое изображение: \"" + imageEntity.getName() + "\"";
        StringBuilder content = new StringBuilder("<p>");
        content.append("На сайте появилось новое изображение: \"").append(imageEntity.getName()).append("\"<br>");
        content.append("Его размер: ").append(imageEntity.getSize()).append(" байт<br><br>");
        content.append("Вы можете скачать его по ссылке: <br>")
                .append(coreUrl)
                .append("/images/")
                .append(imageEntity.getId())
                .append("<br><br>");
        content.append("--<br>С уважением,<br>Разработчик Степан Понамарев<br>");
        content.append("GitHub: https://github.com/stepagin<br>");
        content.append("Проект: https://github.com/orgs/stepagin-core-mail-microservices/repositories<br>");
        content.append("</p>\n");

        emailService.sendEmail(owner.getEmail(), subject, content.toString());
    }
}
