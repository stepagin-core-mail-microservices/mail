package ru.stepagin.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.mail.entity.ImageEntity;
import ru.stepagin.mail.repository.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final NotificationService notificationService;

    @Transactional
    public void save(ImageEntity image) {
        imageRepository.save(image);
    }

    @Transactional
    public void handleImageMessage(ImageEntity imageEntity) {
        if (imageEntity == null) {
            return;
        }
        if (imageEntity.getOwner() == null)
            return;
        notificationService.sendImageCreatedMessage(imageEntity, imageEntity.getOwner());
        save(imageEntity);
    }
}
