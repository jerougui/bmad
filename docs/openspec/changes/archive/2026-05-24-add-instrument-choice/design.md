## Context

Le système actuel utilise des échantillons OGG triangle fixes pour toutes les notes. Le `NoteResourceLoader` découvre les échantillons via réflexion sur `R.raw` en correspondant les noms de fichiers aux noms de notes.

## Goals / Non-Goals

**Goals:**
- Permettre aux utilisateurs de sélectionner entre 3 timbres d'instruments (violon, flûte, piano)
- Charger dynamiquement les ensembles d'échantillons spécifiques à chaque instrument
- Maintenir la compatibilité avec la configuration existante

**Non-Goals:**
- Synthèse en temps réel (échantillons uniquement)
- Plus de 3 instruments dans la version initiale
- Téléchargements d'échantillons personnalisés

## Decisions

### 1. Convention de nommage des échantillons

**Decision:** Utiliser le format `instrument_note.ogg` (ex: `violin_c4.ogg`, `flute_d4.ogg`)

**Rationale:** Mapping clair, permet à `NoteResourceLoader` de trouver les échantillons en filtrant sur le préfixe.

### 2. Sélection d'instrument dans la config

**Decision:** Ajouter un champ `instrument` dans `music-config.json` avec des valeurs correspondant aux préfixes de fichiers

**Rationale:** Source de vérité unique, rechargement sans redémarrage de l'app.

### 3. Organisation des échantillons

**Decision:** Tous les échantillons dans `res/raw/` avec préfixe instrument, pas de sous-répertoires

**Rationale:** Android `R.raw` aplati tous les fichiers `res/raw/`; les sous-répertoires ne sont pas supportés.

## Risks / Trade-offs

| Risque | Mitigation |
|--------|------------|
| La taille de l'APK augmente avec plusieurs instruments | Se limiter aux notes essentielles (gamme pentatonique : 5 notes × N instruments) |
| Conflits de nommage des échantillons | Utiliser un format de préfixe cohérent, documentation claire |