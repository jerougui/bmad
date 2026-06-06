---
stepsCompleted: [1, 2, 3]
inputDocuments: ["_bmad-output/planning-artifacts/prds/prd-motion-music-2026-06-06/prd.md"]
---

# Motion-to-Music Cerf-Volant - Epic Breakdown

## Overview

This document provides the complete epic and story breakdown for Motion-to-Music Cerf-Volant, decomposing the requirements from the PRD into implementable stories.

## Requirements Inventory

### Functional Requirements

FR-1: L'application détecte un cerf-volant via caméra arrière (ML Kit/MediaPipe Object Detection)

FR-2: Le système maintient un suivi persistant de l'objet avec position (x,y) et état "lost"

FR-3: La vitesse et accélération sont calculées en px/s et px/s² depuis les positions consécutives

FR-4: SynthEngine génère des formes d'onde procédurales (SINE, SAWTOOTH, SUPERSAW)

FR-5: Mapping position X (0-1) → note dans échelle pentatonique C4-D4-E4-G4-A4

FR-6: Mapping position Y (0-1) → coupe du filtre 200-2000Hz

FR-7: Mapping vitesse → volume avec seuil 1.0 px/s et atténuation progressive

FR-8: Couche sonore adaptative:
- Vitesse < 0.2: pad atmosphérique (SINE, filter 800Hz)
- Vitesse 0.2-0.6: pad + bass (SUPERSAW, filter 1200Hz)
- Vitesse > 0.8: pad + bass + percussion + lead (SUPERSAW, filter 2200Hz)

FR-9: Transitions musicales sur accélération (riser 1.5s, impact 50ms)

FR-10: Configuration JSON valide depuis assets/music-config.json

FR-11: Hot-reload de la config sans redémarrage de l'engine

FR-12: Instrument "disabled" option pour désactiver la sonorisation

### NonFunctional Requirements

NFR-1: Latence audio < 50ms (AudioTrack streaming mode)

NFR-2: CPU < 30% sur appareil test (Pixel 4 class)

NFR-3: Fonctionnement offline (pas de requête réseau)

NFR-4: Pas de nouvelles dépendances Gradle externes

### Additional Requirements

- Plateforme: Android API 21+ (minimum supporté par AudioTrack)
- Caméra: Caméra arrière en 30fps
- Audio: AudioTrack 44.1kHz, buffer 1024, stereo
- Modèle ML: EfficientDet-Lite0/Lite2 (déjà présent)

### UX Design Requirements

(Pas de document UX dédié - POC minimal)

### FR Coverage Map

FR-1: Epic 1 - Détection et suivi caméra
FR-2: Epic 1 - Détection et suivi caméra
FR-3: Epic 1 - Détection et suivi caméra
FR-4: Epic 2 - Synthèse sonore motion-to-music
FR-5: Epic 2 - Synthèse sonore motion-to-music
FR-6: Epic 2 - Synthèse sonore motion-to-music
FR-7: Epic 2 - Synthèse sonore motion-to-music
FR-8: Epic 2 - Synthèse sonore motion-to-music
FR-9: Epic 2 - Synthèse sonore motion-to-music
FR-10: Epic 3 - Configuration et contrôle
FR-11: Epic 3 - Configuration et contrôle
FR-12: Epic 3 - Configuration et contrôle

## Epic List

### Epic 1: Détection et Suivi Caméra
Permet à l'utilisateur de pointer la caméra et de suivre un cerf-volant en temps réel.
**FRs covered:** FR-1, FR-2, FR-3

### Epic 2: Synthèse Sonore Motion-to-Music
Transforme le mouvement du cerf-volant en musique synthwave immersive.
**FRs covered:** FR-4, FR-5, FR-6, FR-7, FR-8, FR-9

### Epic 3: Configuration et Contrôle
Permet de configurer les paramètres musicaux et désactiver la sonorisation si besoin.
**FRs covered:** FR-10, FR-11, FR-12

## Epic 1: Détection et Suivi Caméra

Permet à l'utilisateur de pointer la caméra et de suivre un cerf-volant en temps réel.

### Story 1.1: Initialisation du moteur de détection

As a développeur système,
I want initialiser la caméra et le modèle d'objet dès l'ouverture de l'appli,
So that la détection peut commencer immédiatement.

**Acceptance Criteria:**
- **Given** l'utilisateur ouvre l'application
- **When** la CameraFragment est créée
- **Then** la caméra arrière démarre automatiquement
- **And** le modèle EfficientDet-Lite est chargé dans ObjectDetectorHelper
- **And** le moteur de suivi est initialisé

### Story 1.2: Détecter et suivre un cerf-volant

As a utilisateur,
I want que l'appli détecte automatiquement mon cerf-volant,
So that il est entouré d'un overlay de suivi.

**Acceptance Criteria:**
- **Given** un cerf-volant est en champ de vision
- **When** la caméra traite la frame
- **Then** un rectangle de détection apparaît autour du cerf-volant
- **And** la position (x,y) est mise à jour en temps réel
- **And** l'état "lost" est false tant que l'objet est visible

### Story 1.3: Calculer vitesse et accélération

As a système audio,
I want calculer vitesse et accélération à chaque frame,
So that ces données peuvent être utilisées pour la musique.

**Acceptance Criteria:**
- **Given** des positions successives (x,y,timestamp)
- **When** MotionParameterExtractor traite les frames
- **Then** la vitesse est calculée en px/s avec lissage EMA
- **And** l'accélération est calculée en px/s²
- **And** les valeurs sont disponibles pour le mappage audio

## Epic 2: Synthèse Sonore Motion-to-Music

Transforme le mouvement du cerf-volant en musique synthwave immersive.

### Story 2.1: Moteur de synthèse procédurale

As a développeur audio,
I want créer SynthEngine avec AudioTrack en mode streaming,
So that des formes d'onde peuvent être générées en temps réel.

**Acceptance Criteria:**
- **Given** SynthEngine.start() est appelé
- **When** le thread de génération tourne
- **Then** AudioTrack est initialisé à 44.1kHz stereo
- **And** les formes SINE, SAWTOOTH, SUPERSAW sont générables
- **And** le moteur peut être arrêté proprement

### Story 2.2: Mapping position X vers pitch

As a utilisateur,
I want que la position horizontale du cerf-volant contrôle la hauteur des notes,
So that je peux "jouer" des notes avec le mouvement.

**Acceptance Criteria:**
- **Given** une position X normalisée (0-1)
- **When** mapXToNote est appelé
- **Then** l'index de note correspond à l'échelle pentatonique (C4-D4-E4-G4-A4)
- **And** le glissando est appliqué si activé
- **And** la note peut être utilisée par SynthEngine

### Story 2.3: Mapping position Y vers timbre

As a utilisateur,
I want que la position verticale du cerf-volant contrôle le timbre sonore,
So that le mouvement haut-bas change la couleur du son.

**Acceptance Criteria:**
- **Given** une position Y normalisée (0-1)
- **When** mapYToFilter est appelé
- **Then** la fréquence de coupe du filtre est interpolée 200-2000Hz
- **And** le filtre Moog est appliqué au signal
- **And** le timbre change de manière audible

### Story 2.4: Mapping vitesse vers volume et intensité

As a système,
I want mapper la vitesse à la couche sonore active,
So that la musique s'intensifie avec le mouvement.

**Acceptance Criteria:**
- **Given** une vitesse normée (0-1) et un état de suivi
- **When** getParamsForSpeed est appelé
- **Then** une couche (pad, bass, percussion, lead) est sélectionnée
- **And** les paramètres filtre/coupe sont ajustés selon l'intensité
- **And** le volume applique le seuil et l'atténuation progressive

### Story 2.5: Transitions musicales sur accélération

As a utilisateur,
I want des effets sonores quand le cerf-volant accélère brusquement,
So that les changements de mouvement sont marqués musicalement.

**Acceptance Criteria:**
- **Given** une accélération détectée > seuil
- **When** TransitionGenerator.shouldTriggerRiser est vrai
- **Then** un riser (balayage fréquence montante) est joué 1.5s
- **And** un impact (bruit blanc) est joué si accélération très forte
- **And** les transitions ne s'empilent pas indéfiniment

### Story 2.6: Intégration engine motion-to-music

As a développeur,
I want intégrer le moteur audio dans CameraFragment.onResults(),
So that la musique joue en synchronisation avec la vidéo.

**Acceptance Criteria:**
- **Given** une frame traitée avec tracking actif
- **When** onResults() est appelé
- **Then** MotionToMusicEngine.onPositionUpdate() est déclenché
- **And** la position, vitesse et timestamp sont passés
- **And** la musique joue sans bloquer le rendu vidéo

## Epic 3: Configuration et Contrôle

Permet de configurer les paramètres musicaux et désactiver la sonorisation si besoin.

### Story 3.1: Configuration JSON

As a développeur,
I want charger la configuration depuis assets/music-config.json,
So that les paramètres sonores sont personnalisables.

**Acceptance Criteria:**
- **Given** l'application démarre
- **When** MappingConfigLoader.load() est appelé
- **Then** la config est lue depuis le fichier assets
- **And** les paramètres d'échelle, filtre, velocity sont parsés
- **And** une config par défaut est utilisée si fichier absent

### Story 3.2: Hot-reload de la configuration

As a créateur de contenu,
I want modifier la config JSON et voir les changements sans redémarrer,
So that j'expérimente facilement avec les paramètres sonores.

**Acceptance Criteria:**
- **Given** le fichier music-config.json est modifié
- **When** reloadConfig() est déclenché
- **Then** la nouvelle config est chargée en arrière-plan
- **And** les nouveaux paramètres sont appliqués immédiatement
- **And** Aucune interruption audio n'est perceptible

### Story 3.3: Option désactivation instrument

As a utilisateur,
I want pouvoir désactiver la sonorisation,
So that l'appli fonctionne silencieusement si besoin.

**Acceptance Criteria:**
- **Given** l'instrument est configuré à "disabled"
- **When** setInstrument() est appelé avec "disabled"
- **Then** SoundPool/SynthEngine ne charge pas de samples
- **And** Aucune sortie audio n'est produite
- **And** le moteur continue de suivre l'objet sans audio