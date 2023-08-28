package uz.ecommerce.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ecommerce.fileservice.entity.FileData;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileData, Integer> {
    Optional<FileData> findByName(String name);
}