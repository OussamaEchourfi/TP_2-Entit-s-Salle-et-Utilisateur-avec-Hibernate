//package com.example.model;
//
//import javax.persistence.*;
//import javax.validation.constraints.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "utilisateurs")
//public class Utilisateur {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank(message = "Le nom est obligatoire")
//    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
//    @Column(nullable = false)
//    private String nom;
//
//    @NotBlank(message = "Le prénom est obligatoire")
//    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
//    @Column(nullable = false)
//    private String prenom;
//
//    @NotBlank(message = "L'email est obligatoire")
//    @Email(message = "Format d'email invalide")
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Past(message = "La date de naissance doit être dans le passé")
//    private LocalDate dateNaissance;
//
//    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Format de téléphone invalide")
//    private String telephone;
//
//    // Constructeur par défaut requis par JPA
//    public Utilisateur() {
//    }
//
//    public Utilisateur(String nom, String prenom, String email) {
//        this.nom = nom;
//        this.prenom = prenom;
//        this.email = email;
//    }
//
//    // Getters et Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNom() {
//        return nom;
//    }
//
//    public void setNom(String nom) {
//        this.nom = nom;
//    }
//
//    public String getPrenom() {
//        return prenom;
//    }
//
//    public void setPrenom(String prenom) {
//        this.prenom = prenom;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public LocalDate getDateNaissance() {
//        return dateNaissance;
//    }
//
//    public void setDateNaissance(LocalDate dateNaissance) {
//        this.dateNaissance = dateNaissance;
//    }
//
//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    @Override
//    public String toString() {
//        return "Utilisateur{" +
//                "id=" + id +
//                ", nom='" + nom + '\'' +
//                ", prenom='" + prenom + '\'' +
//                ", email='" + email + '\'' +
//                ", dateNaissance=" + dateNaissance +
//                ", telephone='" + telephone + '\'' +
//                '}';
//    }
//}
package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false, length = 50)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false, length = 50)
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Column(unique = true, nullable = false, length = 120)
    private String email;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Format de téléphone invalide")
    private String telephone;

    // Nouveaux champs
    @Column(nullable = false)
    private Boolean actif = true;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    // Constructeur par défaut requis par JPA
    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    // Callbacks JPA
    @PrePersist
    protected void avantInsertion() {
        this.dateCreation = LocalDateTime.now();
        normaliser();
    }

    @PreUpdate
    protected void avantMiseAJour() {
        this.dateModification = LocalDateTime.now();
        normaliser();
    }

    private void normaliser() {
        if (email != null) {
            email = email.trim().toLowerCase();
        }
    }

    // Méthodes utilitaires
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public Integer calculerAge() {
        if (dateNaissance == null) {
            return null;
        }
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    public boolean estMajeur() {
        Integer age = calculerAge();
        return age != null && age >= 18;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Utilisateur)) return false;
        Utilisateur autre = (Utilisateur) obj;
        return email != null && Objects.equals(email, autre.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Utilisateur [id=" + id
                + ", nomComplet=" + getNomComplet()
                + ", email=" + email
                + ", dateNaissance=" + dateNaissance
                + ", telephone=" + telephone
                + ", actif=" + actif
                + ", créé le=" + dateCreation + "]";
    }
}