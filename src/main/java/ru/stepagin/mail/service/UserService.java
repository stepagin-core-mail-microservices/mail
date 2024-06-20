package ru.stepagin.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.mail.entity.UserEntity;
import ru.stepagin.mail.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Transactional
    public void handleUserMessage(UserEntity userEntity) {
        UserEntity existingUser = userRepository.findById(userEntity.getId()).orElse(null);
        if (existingUser == null || existingUser.getEmail() == null) {
            if (userEntity.getEmail() != null && !userEntity.getEmail().isEmpty()) {
                notificationService.sendGreetingMessage(userEntity);
            }
        }
        save(userEntity);
    }
}
