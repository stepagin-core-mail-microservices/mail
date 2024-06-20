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
        String content = "<p>" + "На сайте появилось новое изображение: \"" + imageEntity.getName() + "\"<br>" +
                "Его размер: " + imageEntity.getSize() + " байт<br><br>" +
                "Вы можете скачать его по ссылке: <br>" +
                coreUrl +
                "/images/" +
                imageEntity.getId() +
                "<br><br>" +
                "--<br>С уважением,<br>Разработчик Степан Понамарев<br>" +
                "GitHub: https://github.com/stepagin<br>" +
                "Проект: https://github.com/orgs/stepagin-core-mail-microservices<br>" +
                "</p>\n";

        emailService.sendEmail(owner.getEmail(), subject, content);
    }

    public void sendImageDownloadedMessage(ImageEntity imageEntity, UserEntity owner) {
        String subject = "Скачано изображение: \"" + imageEntity.getName() + "\"";
        String content = "<p>" + "Скачано изображение \"" + imageEntity.getName() + "\"<br>" +
                "По ссылке: <br>" +
                coreUrl +
                "/images/" +
                imageEntity.getId() +
                "<br>" +
                "Его размер: " + imageEntity.getSize() + " байт<br><br>" +
                "--<br>С уважением,<br>Разработчик Степан Понамарев<br>" +
                "GitHub: https://github.com/stepagin<br>" +
                "Проект: https://github.com/orgs/stepagin-core-mail-microservices<br>" +
                "</p>\n";

        emailService.sendEmail(owner.getEmail(), subject, content);
    }
}
