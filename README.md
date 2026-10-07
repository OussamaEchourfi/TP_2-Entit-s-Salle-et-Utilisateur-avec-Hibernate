# TP 2 : Gestion des salles avec Hibernate, H2 et tests JUnit

**Réalisé par :** Oussama Ech-Chourfi
**Module :** JEE
**Filière :** Génie Informatique et Réseaux, EMSI Marrakech

---

## 1. Objectif du TP

L'objectif de ce TP est de développer une application de **gestion de salles** en Java avec :

- un projet **Maven** ;
- **Hibernate (JPA)** pour le mapping objet-relationnel ;
- la base de données **H2** en mémoire ;
- la validation des données avec **Bean Validation** ;
- une couche **service** réalisant les opérations CRUD ;
- des **tests unitaires JUnit** pour vérifier le bon fonctionnement.

## 2. Outils et technologies

| Outil | Rôle |
|-------|------|
| Java 8 | Langage de programmation |
| Maven | Gestion du projet et des dépendances |
| Hibernate ORM 5.6.5.Final | Implémentation de JPA |
| Hibernate Validator 6.2.0.Final | Validation des entités |
| H2 Database | Base de données en mémoire |
| JUnit 4 | Tests unitaires |
| IntelliJ IDEA | Environnement de développement |

## 3. Structure du projet

```
TP_2-gestion-salles/
├── images/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── App.java
│   │   │   ├── model/
│   │   │   │   ├── Utilisateur.java
│   │   │   │   └── Salle.java
│   │   │   └── service/
│   │   │       ├── UtilisateurService.java
│   │   │       └── SalleService.java
│   │   └── resources/META-INF/
│   │       └── persistence.xml
│   └── test/java/com/example/service/
│       ├── UtilisateurServiceTest.java
│       └── SalleServiceTest.java
├── pom.xml
└── README.md
```

## 4. Étapes de réalisation

### 4.1 Configuration de la persistance

Le fichier `persistence.xml` définit l'unité de persistance `gestion-salles` :

- **Driver** : `org.h2.Driver`
- **URL** : `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1`
- **Dialecte** : `org.hibernate.dialect.H2Dialect`
- **Schéma** : créé automatiquement au démarrage et supprimé à la fermeture (`create-drop`)
- **Affichage SQL** : activé pour suivre les requêtes générées

### 4.2 Les entités

**`Utilisateur`** (table `utilisateurs`)

| Champ | Description |
|-------|-------------|
| `id` | Clé primaire auto-générée |
| `nom`, `prenom` | Obligatoires, entre 2 et 50 caractères |
| `email` | Obligatoire, unique, format valide |
| `dateNaissance` | Doit être dans le passé |
| `telephone` | Format validé par expression régulière |
| `actif` | Statut du compte |
| `dateCreation`, `dateModification` | Renseignées automatiquement par les callbacks `@PrePersist` et `@PreUpdate` |

Méthodes utiles : `getNomComplet()`, `calculerAge()`, `estMajeur()`.

**`Salle`** (table `salles`)

| Champ | Description |
|-------|-------------|
| `id` | Clé primaire auto-générée |
| `nom` | Obligatoire, entre 2 et 100 caractères |
| `capacite` | Obligatoire, entre 1 et 1000 |
| `description` | 500 caractères maximum |
| `disponible` | Disponibilité de la salle |
| `etage`, `batiment` | Emplacement de la salle |
| `possedeProjecteur` | Équipement disponible |
| `dateCreation` | Renseignée automatiquement |

Méthodes utiles : `peutAccueillir(int)`, `reserver()`, `liberer()`, `getEmplacement()`.

### 4.3 La couche service

`UtilisateurService` et `SalleService` encapsulent l'accès aux données avec l'`EntityManager` :

- `save`, `update`, `deleteById` ;
- `findAll`, `findById` ;
- `findByEmail` (utilisateurs) ;
- `findByDisponible` et `findByCapaciteMinimum` (salles).

Chaque opération d'écriture est exécutée dans une transaction avec `commit` ou `rollback` en cas d'erreur.

### 4.4 Les tests unitaires

Deux classes de tests JUnit vérifient les services :

- `SalleServiceTest` : 3 tests ;
- `UtilisateurServiceTest` : 3 tests, dont `testCrudOperations` et `testFindByEmail`.

Chaque test démarre avec une base propre grâce à la création et à la suppression du schéma.

## 5. Exécution et résultats

Les tests sont lancés avec la commande :

```bash
mvn test
```
![Screenshot 2026-10-07 232616.png](src/main/java/Screenshot%202026-10-07%20232616.png)
Résultat : **6 tests exécutés, 0 échec, 0 erreur**, et le build se termine par `BUILD SUCCESS`.


## 7. Conclusion

Ce TP a permis de construire une application complète de gestion de salles : entités validées, services CRUD transactionnels et tests unitaires automatisés. Il illustre le cycle de vie d'une entité JPA (callbacks, validation, transactions) et l'intérêt des tests pour détecter rapidement une régression.

## 8. Lancer le projet

```bash
git clone <URL-du-dépôt>
cd TP_2-gestion-salles
mvn test
mvn compile exec:java -Dexec.mainClass="com.example.App"
```
