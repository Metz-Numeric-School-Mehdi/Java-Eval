# TodoList Application

Une application web de gestion de tâches développée avec **Spring Boot**, **JPA/Hibernate**, **H2** et **Thymeleaf**.

## 📋 Description

Cette application permet de gérer des utilisateurs et leurs tâches associées. Elle offre une interface web intuitive pour créer, consulter, modifier et supprimer des utilisateurs et des tâches.

## ✨ Fonctionnalités

### ✅ Critères minimum requis
- ✅ **L'application démarre correctement**
- ✅ **Connexion à une base de données H2 en mémoire**
- ✅ **Deux modèles de données avec jointure** :
  - `User` (Utilisateur) : id, nom, email
  - `Task` (Tâche) : id, titre, description, statut, dates, user_id (relation ManyToOne)
- ✅ **Données initiales** : 3 utilisateurs et 7 tâches pré-chargées
- ✅ **URLs spécifiques fonctionnelles** :
  - `/` - Page d'accueil avec statistiques
  - `/users` - Liste des utilisateurs
  - `/users/{id}` - Détails d'un utilisateur
  - `/tasks` - Liste des tâches
  - `/tasks/{id}` - Détails d'une tâche

### 🎨 Fonctionnalités bonus implémentées
- ✅ **Interface moderne** avec Bootstrap 5 et icons
- ✅ **CRUD complet** : création, modification, suppression
- ✅ **Fonctionnalités avancées** :
  - Filtrage des tâches (toutes, en cours, terminées)
  - Basculement rapide du statut des tâches
  - Validation des données côté serveur
  - Messages flash d'information
  - Design responsive
  - Statistiques et tableaux de bord

## 🛠️ Technologies utilisées

- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA**
- **H2 Database** (base de données en mémoire)
- **Thymeleaf** (moteur de templates)
- **Bootstrap 5.3.2** (framework CSS)
- **Bootstrap Icons**
- **Maven** (gestionnaire de dépendances)

## 🚀 Installation et démarrage

### Prérequis
- Java 17 ou supérieur
- Maven 3.6+ (ou utiliser le wrapper Maven inclus)

### Étapes d'installation

1. **Cloner ou télécharger le projet**
   ```bash
   git clone <url-du-repository>
   cd todolist
   ```

2. **Compiler le projet**
   ```bash
   mvn clean compile
   ```

3. **Démarrer l'application**
   ```bash
   mvn spring-boot:run
   ```

4. **Accéder à l'application**
   - Ouvrir votre navigateur web
   - Aller à : [http://localhost:8080](http://localhost:8080)

### Arrêter l'application
- Dans le terminal où l'application fonctionne : `Ctrl + C`

## 🗄️ Base de données

L'application utilise **H2**, une base de données en mémoire qui se recrée à chaque démarrage.

### Accès à la console H2
- URL : [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL : `jdbc:h2:mem:todolistdb`
- Username : `sa`
- Password : `password`

### Données de test
L'application se lance avec des données d'exemple :
- **3 utilisateurs** : Alice Martin, Bob Dupont, Charlie Moreau
- **7 tâches** réparties entre les utilisateurs

## 📱 Utilisation

### Navigation
- **Accueil** (`/`) : Vue d'ensemble avec statistiques
- **Utilisateurs** (`/users`) : Gestion des utilisateurs
- **Tâches** (`/tasks`) : Gestion des tâches

### Fonctionnalités principales

#### Gestion des utilisateurs
- 👀 **Consulter** la liste de tous les utilisateurs
- 🔍 **Voir le détail** d'un utilisateur avec ses tâches
- ➕ **Créer** un nouvel utilisateur
- ✏️ **Modifier** un utilisateur existant
- 🗑️ **Supprimer** un utilisateur (supprime aussi ses tâches)

#### Gestion des tâches
- 👀 **Consulter** toutes les tâches ou filtrer par statut
- 🔍 **Voir le détail** d'une tâche
- ➕ **Créer** une nouvelle tâche
- ✏️ **Modifier** une tâche
- ✅ **Basculer** le statut (terminée ↔ en cours)
- 🗑️ **Supprimer** une tâche

## 🏗️ Structure du projet

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── App.java                    # Classe principale Spring Boot
│   │   ├── controller/
│   │   │   ├── HomeController.java     # Contrôleur page d'accueil
│   │   │   ├── UserController.java     # Contrôleur utilisateurs
│   │   │   └── TaskController.java     # Contrôleur tâches
│   │   ├── model/
│   │   │   ├── User.java               # Entité Utilisateur
│   │   │   └── Task.java               # Entité Tâche
│   │   └── repository/
│   │       ├── UserRepository.java     # Repository utilisateurs
│   │       └── TaskRepository.java     # Repository tâches
│   └── resources/
│       ├── application.properties      # Configuration application
│       ├── data.sql                    # Données initiales
│       └── templates/                  # Templates Thymeleaf
│           ├── home.html
│           ├── users/
│           │   ├── list.html
│           │   ├── detail.html
│           │   └── form.html
│           └── tasks/
│               ├── list.html
│               ├── detail.html
│               └── form.html
└── pom.xml                             # Configuration Maven
```

## 🔧 Configuration

### Base de données
La configuration se trouve dans `src/main/resources/application.properties` :
- Base H2 en mémoire
- Console H2 activée
- Création automatique des tables
- Affichage des requêtes SQL en mode développement

### Données initiales
Le fichier `src/main/resources/data.sql` contient les données de test qui sont chargées au démarrage.

## 🚨 Résolution de problèmes

### L'application ne démarre pas
- Vérifier que Java 17+ est installé : `java -version`
- Vérifier que Maven est installé : `mvn -version`
- Nettoyer et recompiler : `mvn clean compile`

### Port 8080 déjà utilisé
- Changer le port dans `application.properties` : `server.port=8081`
- Ou arrêter l'application qui utilise le port 8080

### Erreurs de compilation
- Vérifier la version de Java (doit être 17+)
- Nettoyer le projet : `mvn clean`
- Recompiler : `mvn compile`

## 📝 Notes techniques

- **Validation** : Validation des données avec les annotations Jakarta Bean Validation
- **Relations JPA** : OneToMany bidirectionnelle entre User et Task
- **Sécurité** : Validation côté serveur, échappement HTML automatique
- **Performance** : Lazy loading des relations, pagination possible
- **Maintenance** : Code organisé en couches (MVC), séparation des responsabilités

## 👤 Auteur

**Mehdi DIAS GOMES DFS-GR1**

---

*Application développée dans le cadre du cours Spring Boot - Octobre 2025*# Java-Eval
