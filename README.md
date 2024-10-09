# Rick and Morty - Android

## Introduction

Cette application Android est un projet basé sur l'univers de **Rick and Morty**. L'application récupère et affiche des informations sur les personnages et les lieux de l'API Rick and Morty, tout en stockant certaines données en local grâce à **Realm**. L'architecture du projet suit les principes de **clean architecture** , divisant le projet en plusieurs modules pour assurer la séparation des responsabilités et la maintenabilité.

## Architecture

L'architecture de ce projet est organisée autour de plusieurs couches:


- **Module App**
- **Module Data**
- **Module Domain**
- **Module Features**
- **Module UI**

### 1. **Module App**

Le module `app` sert de point d'entrée principal de l'application. Il contient la configuration générale de l'application, tels que l'initialisation de **Koin** pour l'injection de dépendances.


### 2. **Module Data**

Le module `data` gère tout ce qui concerne la récupération des données, que ce soit à partir d'API distantes ou de bases de données locales. Il est divisé en deux sous-couches :

- **Remote** : Gère les appels API via **Retrofit**. Les `DTOs` (Data Transfer Objects) sont utilisés pour recevoir les réponses JSON de l'API.
- **Local** : Utilise **Realm** pour stocker les entités localement. Chaque entité est représentée à l'aide de classes `RealmObject` adaptées pour la persistance en base de données.
  
Le `Repository` dans cette couche est responsable de la gestion de la logique de récupération des données (locale ou distante). Il fait appel aux sources de données et retourne les données au `ViewModel`.

### 3. **Module Domain**

Le module `domain` contient les modèles d'entités de l'application et définit les interfaces des repositories. Voici les principales responsabilités de ce module :

- **Modèles** : Ce sont les entités métiers telles que `Character` et `Location` qui représentent les concepts utilisés dans l'application.
- **Interfaces des repositories** : Définit les contrats pour l'accès aux données.

### 4. **Module Features**

Le module `features` contient les différentes **features** (fonctionnalités) de l'application. Chaque fonctionnalité est généralement composée d'une interface utilisateur (UI), de ViewModels, et de la gestion des états. Par exemple, le module `features/locations` contient :

- Un `Screen` pour afficher les détails des localisations.
- Un `ViewModels` responsables de la logique de présentation et des interactions avec le repository.

### 5. **Module UI**

Le module `UI` contient les éléments réutilisables qui peuvent être utilisés dans plusieurs fonctionnalités de l'application, comme :

- **Thèmes** : La gestion des thèmes, des couleurs, et des styles utilisés dans l'interface utilisateur.
- **Composables génériques** : Composants UI réutilisables à travers l'application, tels que des boutons, des cartes, etc.
- **Navigation** : Class permettant de gérer la navigation entre différents écran.

## Technologies Utilisées

- **Kotlin** : Langage principal utilisé pour le développement.
- **Jetpack Compose** : Utilisé pour la construction de l'interface utilisateur basée sur la programmation déclarative.
- **Retrofit** : Bibliothèque utilisée pour effectuer les appels API.
- **Koin** : Framework d'injection de dépendances.
- **Realm** : Base de données locale pour stocker les données hors ligne.
