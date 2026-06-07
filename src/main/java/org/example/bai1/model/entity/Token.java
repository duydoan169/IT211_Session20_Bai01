package org.example.bai1.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_value", columnDefinition = "TEXT")
    private String tokenValue;

    @Column(name = "token_type")
    private String tokenType;

    private boolean revoked;

    private boolean expired;

    // nhiều token thuộc về 1 employee
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}