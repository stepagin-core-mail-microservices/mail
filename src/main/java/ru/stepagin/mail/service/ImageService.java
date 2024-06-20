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

    @Transactional
    public void save(ImageEntity image) {
        imageRepository.save(image);
    }
}
