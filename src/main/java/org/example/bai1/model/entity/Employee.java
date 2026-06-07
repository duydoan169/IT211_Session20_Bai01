package org.example.bai1.model.entity;

import jakarta.persistence.OneToOne;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    // id, username, password, trạng thái hoạt động, và quyền của người đùng

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Column(name = "fullname")
    private String fullName;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "role_id") // khóa ngoại
    private Role role;

    // 1 employee có nhiều token
    @OneToMany(mappedBy = "employee")
    private List<Token> tokens;
}
