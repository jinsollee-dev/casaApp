package com.example.casaapp.domain;


import com.example.casaapp.type.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String memberid;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String birthday;

    @Enumerated(EnumType.STRING)
    private com.example.casaapp.type.Role Role;

}
