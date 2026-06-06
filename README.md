# Motion-to-Music Cerf-Volant

Transformez le vol de votre cerf-volant en musique immersive en temps réel via caméra Android.

## Installation

### BMad Method Installation

Ce projet utilise [BMad Method](https://github.com/bmad-code-org/bmad-method) pour la gestion de projet.

**Quick Start:**
```bash
# Installation globale
npx bmad-method install

# Ou initialisation dans ce projet
npx bmad-method install --directory . --modules bmm --tools claude-code --yes
```

Documentation officielle: https://docs.bmad-method.org/fr/tutorials/getting-started/

### Prérequis
- Android Studio Arctic Fox+ ou Android SDK
- Gradle 7.x
- Android SDK API 24+ (minSdk 24, targetSdk 34)

### Setup du projet
```bash
git clone https://github.com/jerougui/bmad.git
cd bmad
```

### Structure
```
bmad/
├── sources/                    # Code Android source
│   ├── app/src/main/java/...     # Kotlin source files
│   └── app/src/androidTest/...   # Tests instrumentés
├── _bmad-output/                 # Artifacts BMad
│   ├── planning-artifacts/       # PRD, briefs, epics
│   └── implementation-artifacts/ # Stories, sprint status
├── docs/openspec/               # Spécifications OpenSpec archivées
└── .bmad-viewer/               # Configuration viewer
```

## Workflow BMad - Commandes de base

### 1. Aide BMad
```bash
/bmad-help
```
Analyse votre position dans le workflow et propose les étapes suivantes.

### 2. Créer un Product Brief
```bash
/bmad-product-brief
```
Définit la vision produit, le problème et la solution.

### 3. Créer un PRD
```bash
/bmad-prd
```
Documente les exigences fonctionnelles et non-fonctionnelles.

### 4. Créer les Epics et Stories
```bash
/bmad-create-epics-and-stories
```
Décompose le PRD en épics et stories développables.

### 5. Développer une Story
```bash
/bmad-dev-story
```
Implémente une story spécifique avec tests.

### 6. Code Review
```bash
/bmad-code-review
```
Passe en revue le code implémenté.

## Progression & Visualisation

### Board GitHub Project
Suivez la progression en temps réel:  
https://github.com/users/jerougui/projects/1/views/1?layout=board

Le board synchronise automatiquement avec `sprint-status.yaml` :
- **Backlog** → stories à venir
- **Ready** → stories prêtes (`ready-for-dev`)
- **In Progress** → stories en cours (`in-progress`)
- **Review** → stories terminées (`review`)
- **Done** → stories validées (`done`)

### Fichiers clés
- `_bmad-output/implementation-artifacts/sprint-status.yaml` - Status des stories
- `_bmad-output/planning-artifacts/epics.md` - Décomposition complète
- `_bmad-output/planning-artifacts/prds/*.md` - PRD détaillé

## Architecture Technique

- **Camera**: CameraX API 1.4.2, 30fps
- **ML**: MediaPipe Tasks Vision 0.10.29, EfficientDet-Lite
- **Audio**: SynthEngine avec AudioTrack (44.1kHz, buffer 1024)
- **Mapping**: Position X/Y → pitch/timbre, vitesse → volume/intensité

## Développement

### Tests
```bash
./gradlew connectedAndroidTest
```

### Build
```bash
./gradlew assembleDebug
```

---
*Dernière mise à jour: 2026-06-06*