package ru.stepagin.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepagin.mail.entity.ImageEntity;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {


}
