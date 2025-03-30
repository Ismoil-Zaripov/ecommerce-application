package uz.ecommerce.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.ecommerce.commons.constant.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "image_url")
    private String imageUrl;
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedAt;
}