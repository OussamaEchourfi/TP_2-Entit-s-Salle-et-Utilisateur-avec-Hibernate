//package com.example;
//
//import com.example.model.Salle;
//import com.example.model.Utilisateur;
//import com.example.service.SalleService;
//import com.example.service.UtilisateurService;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public class App {
//    public static void main(String[] args) {
//        // Création de l'EntityManagerFactory
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-salles");
//
//        // Création des services
//        UtilisateurService utilisateurService = new UtilisateurService(emf);
//        SalleService salleService = new SalleService(emf);
//
//        try {
//            // Test des opérations CRUD pour Utilisateur
//            System.out.println("\n=== Test CRUD Utilisateur ===");
//            testCrudUtilisateur(utilisateurService);
//
//            // Test des opérations CRUD pour Salle
//            System.out.println("\n=== Test CRUD Salle ===");
//            testCrudSalle(salleService);
//
//        } finally {
//            // Fermeture de l'EntityManagerFactory
//            emf.close();
//        }
//    }
//
//    private static void testCrudUtilisateur(UtilisateurService service) {
//        // Création (Create)
//        System.out.println("Création d'utilisateurs...");
//        Utilisateur u1 = new Utilisateur("Dupont", "Jean", "jean.dupont@example.com");
//        u1.setDateNaissance(LocalDate.of(1985, 5, 15));
//        u1.setTelephone("+33612345678");
//
//        Utilisateur u2 = new Utilisateur("Martin", "Sophie", "sophie.martin@example.com");
//        u2.setDateNaissance(LocalDate.of(1990, 10, 20));
//        u2.setTelephone("+33687654321");
//
//        service.save(u1);
//        service.save(u2);
//
//        // Lecture (Read)
//        System.out.println("\nLecture de tous les utilisateurs :");
//        List<Utilisateur> utilisateurs = service.findAll();
//        utilisateurs.forEach(System.out::println);
//
//        System.out.println("\nRecherche d'un utilisateur par ID :");
//        Optional<Utilisateur> utilisateurOpt = service.findById(1L);
//        utilisateurOpt.ifPresent(System.out::println);
//
//        System.out.println("\nRecherche d'un utilisateur par email :");
//        Optional<Utilisateur> utilisateurParEmail = service.findByEmail("sophie.martin@example.com");
//        utilisateurParEmail.ifPresent(System.out::println);
//
//        // Mise à jour (Update)
//        System.out.println("\nMise à jour d'un utilisateur :");
//        utilisateurOpt.ifPresent(utilisateur -> {
//            utilisateur.setTelephone("+33699887766");
//            service.update(utilisateur);
//            System.out.println("Utilisateur mis à jour : " + utilisateur);
//        });
//
//        // Suppression (Delete)
//        System.out.println("\nSuppression d'un utilisateur :");
//        service.deleteById(2L);
//        System.out.println("Utilisateur avec ID=2 supprimé");
//
//        System.out.println("\nListe des utilisateurs après suppression :");
//        service.findAll().forEach(System.out::println);
//    }
//
//    private static void testCrudSalle(SalleService service) {
//        // Création (Create)
//        System.out.println("Création de salles...");
//        Salle s1 = new Salle("Salle A101", 30);
//        s1.setDescription("Salle de réunion équipée d'un projecteur");
//        s1.setEtage(1);
//
//        Salle s2 = new Salle("Amphithéâtre B201", 150);
//        s2.setDescription("Grand amphithéâtre pour conférences");
//        s2.setEtage(2);
//
//        Salle s3 = new Salle("Salle C305", 10);
//        s3.setDescription("Petite salle pour entretiens");
//        s3.setEtage(3);
//        s3.setDisponible(false);
//
//        service.save(s1);
//        service.save(s2);
//        service.save(s3);
//
//        // Lecture (Read)
//        System.out.println("\nLecture de toutes les salles :");
//        List<Salle> salles = service.findAll();
//        salles.forEach(System.out::println);
//
//        System.out.println("\nRecherche d'une salle par ID :");
//        Optional<Salle> salleOpt = service.findById(2L);
//        salleOpt.ifPresent(System.out::println);
//
//        System.out.println("\nRecherche des salles disponibles :");
//        List<Salle> sallesDisponibles = service.findByDisponible(true);
//        sallesDisponibles.forEach(System.out::println);
//
//        System.out.println("\nRecherche des salles avec capacité minimum de 50 :");
//        List<Salle> sallesGrandes = service.findByCapaciteMinimum(50);
//        sallesGrandes.forEach(System.out::println);
//
//        // Mise à jour (Update)
//        System.out.println("\nMise à jour d'une salle :");
//        salleOpt.ifPresent(salle -> {
//            salle.setCapacite(200);
//            service.update(salle);
//            System.out.println("Salle mise à jour : " + salle);
//        });
//
//        // Suppression (Delete)
//        System.out.println("\nSuppression d'une salle :");
//        service.deleteById(3L);
//        System.out.println("Salle avec ID=3 supprimée");
//
//        System.out.println("\nListe des salles après suppression :");
//        service.findAll().forEach(System.out::println);
//    }
//}
//
package com.example;

import com.example.model.Salle;
import com.example.model.Utilisateur;
import com.example.service.SalleService;
import com.example.service.UtilisateurService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class App {

    private static final String UNITE_PERSISTANCE = "gestion-salles";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(UNITE_PERSISTANCE);

        UtilisateurService utilisateurService = new UtilisateurService(emf);
        SalleService salleService = new SalleService(emf);

        try {
            afficherTitre("Test CRUD Utilisateur");
            testCrudUtilisateur(utilisateurService);

            afficherTitre("Test CRUD Salle");
            testCrudSalle(salleService);

            afficherTitre("Bilan final");
            System.out.println("Utilisateurs en base : " + utilisateurService.findAll().size());
            System.out.println("Salles en base       : " + salleService.findAll().size());
        } catch (RuntimeException e) {
            System.out.println("Erreur pendant l'exécution : " + e.getMessage());
            e.printStackTrace();
        } finally {
            emf.close();
        }
    }

    private static void afficherTitre(String titre) {
        System.out.println("\n==============================");
        System.out.println("  " + titre);
        System.out.println("==============================");
    }

    private static void testCrudUtilisateur(UtilisateurService service) {
        // Création (Create)
        System.out.println("Création d'utilisateurs...");
        Utilisateur u1 = new Utilisateur("Dupont", "Jean", "jean.dupont@example.com");
        u1.setDateNaissance(LocalDate.of(1985, 5, 15));
        u1.setTelephone("+33612345678");

        Utilisateur u2 = new Utilisateur("Martin", "Sophie", "sophie.martin@example.com");
        u2.setDateNaissance(LocalDate.of(1990, 10, 20));
        u2.setTelephone("+33687654321");

        // Utilisateur supplémentaire (mineur) pour tester estMajeur()
        Utilisateur u3 = new Utilisateur("Alaoui", "Yassine", "yassine.alaoui@example.com");
        u3.setDateNaissance(LocalDate.of(2012, 3, 8));
        u3.setTelephone("+212612345678");

        service.save(u1);
        service.save(u2);
        service.save(u3);

        // Lecture (Read)
        System.out.println("\nLecture de tous les utilisateurs :");
        List<Utilisateur> utilisateurs = service.findAll();
        utilisateurs.forEach(System.out::println);

        System.out.println("\nÂge et statut de chaque utilisateur :");
        for (Utilisateur u : utilisateurs) {
            System.out.println("- " + u.getNomComplet()
                    + " : " + u.calculerAge() + " ans"
                    + (u.estMajeur() ? " (majeur)" : " (mineur)"));
        }

        System.out.println("\nRecherche d'un utilisateur par ID :");
        Optional<Utilisateur> utilisateurOpt = service.findById(1L);
        utilisateurOpt.ifPresent(System.out::println);

        System.out.println("\nRecherche d'un utilisateur par email :");
        Optional<Utilisateur> utilisateurParEmail = service.findByEmail("sophie.martin@example.com");
        if (utilisateurParEmail.isPresent()) {
            System.out.println(utilisateurParEmail.get());
        } else {
            System.out.println("Aucun utilisateur avec cet email.");
        }

        // Mise à jour (Update)
        System.out.println("\nMise à jour d'un utilisateur :");
        utilisateurOpt.ifPresent(utilisateur -> {
            utilisateur.setTelephone("+33699887766");
            service.update(utilisateur);
        });
        // Relecture pour vérifier ce qui est réellement enregistré
        service.findById(1L).ifPresent(u ->
                System.out.println("Après mise à jour : " + u));

        // Désactivation d'un compte
        System.out.println("\nDésactivation du compte de l'utilisateur ID=3 :");
        service.findById(3L).ifPresent(u -> {
            u.setActif(false);
            service.update(u);
        });
        service.findById(3L).ifPresent(u ->
                System.out.println("Compte actif ? " + u.getActif()));

        // Suppression (Delete)
        System.out.println("\nSuppression d'un utilisateur :");
        service.deleteById(2L);
        System.out.println("Utilisateur avec ID=2 supprimé");

        System.out.println("\nListe des utilisateurs après suppression :");
        service.findAll().forEach(System.out::println);
    }

    private static void testCrudSalle(SalleService service) {
        // Création (Create)
        System.out.println("Création de salles...");
        Salle s1 = new Salle("Salle A101", 30);
        s1.setDescription("Salle de réunion équipée d'un projecteur");
        s1.setEtage(1);
        s1.setBatiment("Bloc A");
        s1.setPossedeProjecteur(true);

        Salle s2 = new Salle("Amphithéâtre B201", 150);
        s2.setDescription("Grand amphithéâtre pour conférences");
        s2.setEtage(2);
        s2.setBatiment("Bloc B");
        s2.setPossedeProjecteur(true);

        Salle s3 = new Salle("Salle C305", 10);
        s3.setDescription("Petite salle pour entretiens");
        s3.setEtage(3);
        s3.setBatiment("Bloc C");
        s3.setDisponible(false);

        service.save(s1);
        service.save(s2);
        service.save(s3);

        // Lecture (Read)
        System.out.println("\nLecture de toutes les salles :");
        List<Salle> salles = service.findAll();
        salles.forEach(System.out::println);

        System.out.println("\nRecherche d'une salle par ID :");
        Optional<Salle> salleOpt = service.findById(2L);
        salleOpt.ifPresent(System.out::println);

        System.out.println("\nRecherche des salles disponibles :");
        List<Salle> sallesDisponibles = service.findByDisponible(true);
        sallesDisponibles.forEach(System.out::println);

        System.out.println("\nRecherche des salles avec capacité minimum de 50 :");
        List<Salle> sallesGrandes = service.findByCapaciteMinimum(50);
        sallesGrandes.forEach(System.out::println);

        // Test de la capacité d'accueil
        System.out.println("\nCapacité d'accueil pour un groupe de 40 personnes :");
        for (Salle s : salles) {
            System.out.println("- " + s.getNom() + " : "
                    + (s.peutAccueillir(40) ? "oui" : "non"));
        }

        // Mise à jour (Update)
        System.out.println("\nMise à jour d'une salle :");
        salleOpt.ifPresent(salle -> {
            salle.setCapacite(200);
            service.update(salle);
        });
        service.findById(2L).ifPresent(s ->
                System.out.println("Après mise à jour : " + s));

        // Réservation d'une salle
        System.out.println("\nRéservation de la salle ID=1 :");
        service.findById(1L).ifPresent(s -> {
            s.reserver();
            service.update(s);
        });
        service.findById(1L).ifPresent(s ->
                System.out.println("Disponible après réservation ? " + s.getDisponible()));

        // Suppression (Delete)
        System.out.println("\nSuppression d'une salle :");
        service.deleteById(3L);
        System.out.println("Salle avec ID=3 supprimée");

        System.out.println("\nListe des salles après suppression :");
        service.findAll().forEach(System.out::println);
    }
}