# Story 1.3: Calculer vitesse et accélération

Status: ready-for-dev

## Story

As a système audio,
I want calculer vitesse et accélération à chaque frame,
So that ces données peuvent être utilisées pour la musique.

## Acceptance Criteria

1. [ ] (AC-1) La vitesse est calculée en px/s avec lissage EMA
2. [ ] (AC-2) L'accélération est calculée en px/s²
3. [ ] (AC-3) Les valeurs sont disponibles pour le mappage audio

## Tasks / Subtasks

- [ ] Task 1: Analyser MotionParameterExtractor
  - [ ] Subtask 1.1: Vérifier computeVelocity avec EMA (smoothing 0.1)
  - [ ] Subtask 1.2: Vérifier computeAcceleration
  - [ ] Subtask 1.3: Vérifier l'intégration avec MotionToMusicEngine.onPositionUpdate()
- [ ] Task 2: Tests
  - [ ] Subtask 2.1: Tester extraction de paramètres motion
  - [ ] Subtask 2.2: Tester EMA smoothing

## Dev Notes

### Existing Implementation Analysis

- **MotionParameterExtractor.kt** - extraction des paramètres motion
- **MotionToMusicEngine.onPositionUpdate()** - reçoit posXPx, posYPx, tsMs

### Files Being Analyzed

- sources/MotionParameterExtractor.kt
- sources/MotionToMusicEngine.kt (lines 84-89)

### Technical Requirements

- EMA smoothing avec facteur configurable
- Division par dt pour vitesse (px/s)
- Calcul sur delta-vitesse pour accélération

### References

- [Source: docs/openspec/changes/archive/2026-05-23-object-motion-to-music/design.md#velocity-source]

## Dev Agent Record

### Agent Model Used

kilo-auto/free

### Completion Notes List

### File List