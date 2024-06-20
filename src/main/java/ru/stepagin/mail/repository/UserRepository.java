package ru.stepagin.mail.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepagin.mail.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
