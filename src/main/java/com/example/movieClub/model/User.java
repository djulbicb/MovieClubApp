package com.example.movieClub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Obicno iz mog iskustva, User je relativno rezervisana rec u programskim jezicima,
// neke baze prestanu da rade ako dodas tabelu koja se zove User
// takodje spring boot i frejmvorci generalno uvek imaju klasu koja se zove User
// Umesto toga moze Member, Customer...
// Probaj da ubacis SpringBoot Security autentifikaciju i da sacuvas u bazu pa ces videti problem
// Nije zvanicno rezervisana rec koliko ja znam ali obicno pravi problem
// https://www.tutorialspoint.com/can-we-use-the-word-user-for-a-mysql-table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    // Kada budes dodavala security, dodaj i enkripciju sifre
    private String password;
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovieCopy> rentedCopies;
}
