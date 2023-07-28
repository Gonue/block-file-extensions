package com.example.fileblocking.domain.repository;

import com.example.fileblocking.domain.entity.CustomExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomExtensionRepository extends JpaRepository<CustomExtensionEntity, Long> {
    Optional<CustomExtensionEntity> findByExtension(String extension);
}
