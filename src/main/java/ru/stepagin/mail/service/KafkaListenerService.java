package ru.stepagin.mail.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.stepagin.mail.entity.ImageEntity;
import ru.stepagin.mail.entity.UserEntity;

@Component
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class KafkaListenerService {
    private final UserService userService;
    private final ImageService imageService;

    @KafkaListener(topics = "${app.kafka.topic.user.name}",
            groupId = "spring.kafka.consumer.group-id", containerFactory = "userListenerContainerFactory")
    public void usersListener(@Payload UserEntity userEntity) {
        log.info("received message: " + userEntity);
        userService.handleUserMessage(userEntity);
    }

    @KafkaListener(topics = "${app.kafka.topics.image.name}",
            groupId = "spring.kafka.consumer.group-id", containerFactory = "imageListenerContainerFactory")
    public void imagesListener(@Payload ImageEntity imageEntity) {
        log.info("received message: " + imageEntity);
        imageService.handleImageMessage(imageEntity);
    }
}
