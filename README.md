<div align="center">
<img width="1200" height="475" alt="GHBanner" src="https://ai.google.dev/static/site-assets/images/share-ais-513315318.png" />
</div>

# Run and deploy your AI Studio app

This contains everything you need to run your app locally.

View your app in AI Studio: https://ai.studio/apps/8a4d40a5-3145-4793-b987-6185c04f31d3

## Run Locally

**Prerequisites:**  [Android Studio](https://developer.android.com/studio)


1. Open Android Studio
2. Select **Open** and choose the directory containing this project
3. Allow Android Studio to fix any incompatibilities as it imports the project.
4. Create a file named `.env` in the project directory and set `GEMINI_API_KEY` in that file to your Gemini API key (see `.env.example` for an example)
5. Remove this line from the app's `build.gradle.kts` file: `signingConfig = signingConfigs.getByName("debugConfig")`
6. Run the app on an emulator or physical device



# Suivi Hydratation

Application Android de suivi d'hydratation quotidienne — Jetpack Compose, Material 3, thème sombre avec accents turquoise.

## Présentation

Ce projet a été généré initialement avec Google AI Studio (mode Build), puis corrigé et mis en fonctionnement dans Android Studio. Il permet de suivre sa consommation d'eau quotidienne par rapport à un objectif de 2 litres.

## Fonctionnalités

- Cercle de progression animé vers l'objectif de 2 L, dessiné en Canvas avec effet de lueur turquoise
- Bouton « + 250 ml » pour enregistrer une gorgée
- Bouton « Reset » avec boîte de dialogue de confirmation
- Message d'encouragement dynamique selon la progression
- Historique du jour : liste des gorgées, suppression individuelle possible
- Persistance des données via une base Room (SQLite) — les données survivent à la fermeture de l'app
- État géré via un ViewModel (WaterViewModel) avec StateFlow, séparé de l'UI

## Architecture du projet

```
app/src/main/java/com/example/
├── MainActivity.kt          (Activité principale + toute l'UI Compose)
├── data/
│   ├── WaterDatabase.kt      (Base de données Room)
│   ├── WaterRecord.kt        (Entité Room : une gorgée = un enregistrement)
│   └── WaterRepository.kt    (Couche d'accès aux données)
└── ui/
    ├── WaterViewModel.kt     (StateFlow de l'état d'hydratation)
    └── theme/                (Thème Material 3 : couleurs, typographie)
```

## Stack technique

- Kotlin : 2.2.0
- Android Gradle Plugin (AGP) : 8.10.1
- Gradle : 8.10+
- Jetpack Compose BOM : 2024.10.01
- KSP (annotation processing Room) : 2.3.5
- Room : 2.7.0
- compileSdk / targetSdk : 36
- minSdk : 24
- Java target : 11

## Prérequis

- Android Studio (version récente)
- JDK 17 ou supérieur (fourni avec Android Studio)
- Connexion internet lors du premier Gradle Sync

## Étapes pour lancer le projet

1. Ouvrir le dossier du projet dans Android Studio (File → Open)
2. Laisser le Gradle Sync se terminer
3. Créer un fichier `.env` à la racine (voir `.env.example`) — non utilisé fonctionnellement actuellement, mais requis par le scaffold Google AI Studio
4. Créer ou démarrer un émulateur via Device Manager
5. Si l'émulateur ne s'affiche pas dans le panneau intégré « Running Devices », aller dans Settings → Tools → Emulator et décocher « Launch in the Running Devices tool window » pour le lancer en fenêtre séparée
6. Cliquer sur ▶ Run

## Note sur Firebase et Gemini

Le projet contient des dépendances Firebase (firebase-bom, firebase-ai, firebase-appcheck-recaptcha) et un fichier `.env` attendant une clé `GEMINI_API_KEY`. Après vérification (recherche de « Gemini » et « firebase » dans tout le code source), aucune de ces dépendances n'est utilisée dans le code Kotlin réel — c'est un scaffold générique laissé par défaut par Google AI Studio. L'app fonctionne normalement sans clé API valide.

## Documentation complémentaire

- `docs/Corrections.docx` — détail de toutes les erreurs rencontrées et leurs corrections, dans l'ordre chronologique
- `docs/Documentation_Projet.docx` — documentation approfondie de l'architecture et des choix techniques