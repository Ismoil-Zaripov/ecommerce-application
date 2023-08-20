package uz.ecommerce.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ecommerce.fileservice.entity.FileEntity;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findByName(String name);
}
