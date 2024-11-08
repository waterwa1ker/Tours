package com.example.tours.model;

import com.example.tours.constant.PersonRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phone;

    private String email;

    private String address;

    private String password;

    @Enumerated(EnumType.STRING)
    private PersonRole role;

    @OneToMany(mappedBy = "person")
    private List<Reservation> reservations;

}