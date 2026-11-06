package by.kolesnik.aston_2_homework4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "age", nullable = false)
    Integer age;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDate created_at;
}
