---
title: Motion-to-Music Cerf-Volant
status: draft
created: 2026-06-06
updated: 2026-06-06
---

# Product Brief: Motion-to-Music Cerf-Volant

## Executive Summary

Une application Android transforme le vol d'un cerf-volant en une expérience musicale immersive. La caméra suit en temps réel la position, la vitesse et la trajectoire du cerf-volant, traduisant ces mouvements en une bande-son électronique dynamique qui évolue avec l'intensité du vol. Cette application crée un pont sensoriel entre le mouvement visuel et l'audition, transformant chaque vol en une composition unique.

## The Problem

Les passionnés de cerf-volant, les artistes et les familles cherchent des façons interactives et créatives de célébrer le mouvement. Les apps existantes se limitent à la visualisation ou à la simple détection. L'absence de feedback audio signifie que l'expressivité du vol - sa grâce, son énergie, ses changements de direction - est perdue. Les utilisateurs manquent d'une expérience immersives qui rend le mouvement tangible à travers la musique.

## The Solution

L'application utilise la caméra et le machine learning pour suivre un cerf-volant en temps réel. Les paramètres de mouvement (position X/Y, vitesse, accélération) sont mappés vers des paramètres musicaux via un moteur de synthèse procédurale:
- **Position X** → Hauteur de la note (échelle harmonique configurable)
- **Position Y** → Timbre (coupe-filter, forme d'onde)
- **Vitesse** → Volume et intensité musicale
- **Accélération/Direction** → Transitions musicales (risers, drops, impacts)

Le système génère des sons synthwave dynamiques avec des couches multiples (pads, basses, percussions, leads) qui s'activent selon l'intensité du vol.

## What Makes This Different

- **Sonorisation temps réel**: La plupart des apps visuelles ne traduisent pas le mouvement en audio immersif
- **Synthèse procédurale native**: Pas de dépendances externes, tout est généré par le moteur Android AudioTrack
- **Échelles harmoniques configurables**: Le son reste toujours musical grâce aux configurations JSON (pentatonique, majeur, etc.)
- **Adaptatif et immersif**: La musique évolue naturellement avec la dynamique du vol, pas de simple lecture de samples fixes

## Who This Serves

**Utilisateurs primaires**:
- Passionnés de cerf-volant cherchant une expérience créative nouvelle
- Artistes et musiciens explorant de nouveaux contrôles gestuels
- Familles et enfants découvrant la physique et la musique de façon ludique

**Utilisateurs secondaires**:
- Éducateurs en science/expérience pour l'art et la technologie
- Créateurs de contenu cherchant des expériences visuelles/audio originales

## Success Criteria

- Détection et suivi fiable d'un cerf-volant en plein jour (précision > 80%)
- Latence audio < 50ms pour une réponse temps réel perçue
- Mapping musical harmonieux qui reste musical à toute vitesse
- Consommation CPU raisonnable (pas de dégradation de l'expérience visuelle)

## Scope

**Version 1**:
- Détection unique objet (cerf-volant)
- Synthèse procédurale avec échelles pentatoniques
- Mapping: X→pitch, Y→timbre, vitesse→volume
- Transitions basées sur accélération

**Explicitement exclu**:
- Multi-objet sonification
- Export audio/enregistrement
- Interface de configuration avancée (config JSON externe)
- Analyse spectrumale temps réel

## Vision

Une plateforme où n'importe quel mouvement devient une partition interactive - sports, danse, météo, ou encore objets volants. L'application pourrait intégrer des modes collaboratifs multi-utilisateurs, des partages de compositions, et des expériences AR où la musique guide la trajectoire du cerf-volant.