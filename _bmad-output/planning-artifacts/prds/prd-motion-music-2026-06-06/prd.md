---
title: Motion-to-Music Cerf-Volant
status: draft
created: 2026-06-06
updated: 2026-06-06
---

# PRD: Motion-to-Music Cerf-Volant

## 1. Vision

Transformer le vol d'un cerf-volant en une expérience musicale immersive temps réel via caméra Android. L'utilisateur pointe sa caméra vers le cerf-volant, le système le suit et traduit position/vitesse/accélération en sons harmoniques synthwave. Cible: démontrer le concept, pas une appli produit.

## 2. Exigences Fonctionnelles

### 2.1 Détection et Suivi

**[FR-1]** L'application détecte un cerf-volant via caméra arrière (ML Kit/MediaPipe Object Detection)

**[FR-2]** Le système maintient un suivi persistant de l'objet avec position (x,y) et état "lost"

**[FR-3]** La vitesse et accélération sont calculées en px/s et px/s² depuis les positions consécutives

### 2.2 Synthèse Sonore

**[FR-4]** `SynthEngine` génère des formes d'onde procédurales (SINE, SAWTOOTH, SUPERSAW)

**[FR-5]** Mapping position X (0-1) → note dans échelle pentatonique C4-D4-E4-G4-A4

**[FR-6]** Mapping position Y (0-1) → coupe du filtre 200-2000Hz

**[FR-7]** Mapping vitesse → volume avec seuil 1.0 px/s et atténuation progressive

**[FR-8]** Couche sonore adaptative:
- Vitesse < 0.2: pad atmosphérique (SINE, filter 800Hz)
- Vitesse 0.2-0.6: pad + bass (SUPERSAW, filter 1200Hz)
- Vitesse > 0.8: pad + bass + percussion + lead (SUPERSAW, filter 2200Hz)

**[FR-9]** Transitions musicales sur accélération (riser 1.5s, impact 50ms)

### 2.3 Configuration

**[FR-10]** Configuration JSON valide depuis `assets/music-config.json`

**[FR-11]** Hot-reload de la config sans redémarrage de l'engine

**[FR-12]** Instrument "disabled" option pour désactiver la sonorisation

## 3. Exigences Non-Fonctionnelles

**NFR-1** Latence audio < 50ms (AudioTrack streaming mode)

**NFR-2** CPU < 30% sur appareil test (Pixel 4 class)

**NFR-3** Fonctionnement offline (pas de requête réseau)

**NFR-4** Pas de nouvelles dépendances Gradle externes

## 4. Contraintes Techniques

- **Plateforme**: Android API 21+ (minimum supporté par AudioTrack)
- **Caméra**: Caméra arrière en 30fps
- **Audio**: AudioTrack 44.1kHz, buffer 1024, stereo
- **Modèle ML**: EfficientDet-Lite0/Lite2 (déjà présent)

## 5. Success Criteria

- Détection et suivi fonctionnels en plein jour
- Son musical harmonieux à toutes les vitesses
- Pas de fissures audio ni coupures brusques

## 6. Open Questions

1. Calibration des seuils de vitesse selon environnement lumineux
2. Gestion du multi-cerfs-volant (délibéré comme hors-scope)
3. Mapping accélération → transitions: seuils à affiner