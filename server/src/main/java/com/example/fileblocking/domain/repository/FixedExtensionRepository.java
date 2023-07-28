package com.example.fileblocking.domain.repository;

import com.example.fileblocking.domain.entity.FixedExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FixedExtensionRepository extends JpaRepository<FixedExtensionEntity, Long> {
    Optional<FixedExtensionEntity> findByExtension(String extension);
}
