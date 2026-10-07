//package com.example.model;
//
//import javax.persistence.*;
//import javax.validation.constraints.*;
//
//@Entity
//@Table(name = "salles")
//public class Salle {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank(message = "Le nom est obligatoire")
//    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
//    @Column(nullable = false)
//    private String nom;
//
//    @NotNull(message = "La capacité est obligatoire")
//    @Min(value = 1, message = "La capacité minimum est de 1 personne")
//    @Max(value = 1000, message = "La capacité maximum est de 1000 personnes")
//    @Column(nullable = false)
//    private Integer capacite;
//
//    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
//    @Column(length = 500)
//    private String description;
//
//    @NotNull(message = "Le statut est obligatoire")
//    @Column(nullable = false)
//    private Boolean disponible = true;
//
//    @Min(value = 0, message = "L'étage ne peut pas être négatif")
//    private Integer etage;
//
//    // Constructeur par défaut requis par JPA
//    public Salle() {
//    }
//
//    public Salle(String nom, Integer capacite) {
//        this.nom = nom;
//        this.capacite = capacite;
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
//    public Integer getCapacite() {
//        return capacite;
//    }
//
//    public void setCapacite(Integer capacite) {
//        this.capacite = capacite;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Boolean getDisponible() {
//        return disponible;
//    }
//
//    public void setDisponible(Boolean disponible) {
//        this.disponible = disponible;
//    }
//
//    public Integer getEtage() {
//        return etage;
//    }
//
//    public void setEtage(Integer etage) {
//        this.etage = etage;
//    }
//
//    @Override
//    public String toString() {
//        return "Salle{" +
//                "id=" + id +
//                ", nom='" + nom + '\'' +
//                ", capacite=" + capacite +
//                ", description='" + description + '\'' +
//                ", disponible=" + disponible +
//                ", etage=" + etage +
//                '}';
//    }
//}

package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String nom;

    @NotNull(message = "La capacité est obligatoire")
    @Min(value = 1, message = "La capacité minimum est de 1 personne")
    @Max(value = 1000, message = "La capacité maximum est de 1000 personnes")
    @Column(nullable = false)
    private Integer capacite;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Le statut est obligatoire")
    @Column(nullable = false)
    private Boolean disponible = true;

    @Min(value = 0, message = "L'étage ne peut pas être négatif")
    private Integer etage;

    // Nouveaux champs
    @Size(max = 50, message = "Le bâtiment ne peut pas dépasser 50 caractères")
    @Column(length = 50)
    private String batiment;

    @Column(name = "possede_projecteur")
    private Boolean possedeProjecteur = false;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    // Constructeur par défaut requis par JPA
    public Salle() {
    }

    public Salle(String nom, Integer capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    @PrePersist
    protected void avantInsertion() {
        this.dateCreation = LocalDateTime.now();
    }

    // Méthodes utilitaires
    public boolean peutAccueillir(int nombrePersonnes) {
        return Boolean.TRUE.equals(disponible)
                && capacite != null
                && nombrePersonnes > 0
                && nombrePersonnes <= capacite;
    }

    public void reserver() {
        this.disponible = false;
    }

    public void liberer() {
        this.disponible = true;
    }

    public String getEmplacement() {
        String lieu = (batiment != null) ? batiment : "Bâtiment non défini";
        return etage != null ? lieu + ", étage " + etage : lieu;
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

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getEtage() {
        return etage;
    }

    public void setEtage(Integer etage) {
        this.etage = etage;
    }

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public Boolean getPossedeProjecteur() {
        return possedeProjecteur;
    }

    public void setPossedeProjecteur(Boolean possedeProjecteur) {
        this.possedeProjecteur = possedeProjecteur;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Salle)) return false;
        Salle autre = (Salle) obj;
        return id != null && Objects.equals(id, autre.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Salle [id=" + id
                + ", nom=" + nom
                + ", capacite=" + capacite
                + ", emplacement=" + getEmplacement()
                + ", projecteur=" + possedeProjecteur
                + ", disponible=" + disponible + "]";
    }
}