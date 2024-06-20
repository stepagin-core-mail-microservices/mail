package ru.stepagin.mail.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.stepagin.mail.entity.ImageEntity;
import ru.stepagin.mail.entity.UserEntity;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class KafkaListenerService {
    private final UserService userService;
    private final ImageService imageService;

    @KafkaListener(topics = "${app.kafka.topic.user.name}",
            groupId = "spring.kafka.consumer.group-id", containerFactory = "userListenerContainerFactory")
    public void usersListener(@Payload UserEntity userEntity) {
        System.out.println("received message: " + userEntity);
        userService.save(userEntity);
    }

    @KafkaListener(topics = "${app.kafka.topics.image.name}",
            groupId = "spring.kafka.consumer.group-id", containerFactory = "imageListenerContainerFactory")
    public void imagesListener(@Payload ImageEntity imageEntity) {
        System.out.println("received message: " + imageEntity);
        imageService.save(imageEntity);
    }
}
