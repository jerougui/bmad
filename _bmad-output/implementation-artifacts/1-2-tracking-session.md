# Story 1.2: Détecter et suivre un cerf-volant

Status: ready-for-dev

## Story

As a utilisateur,
I want que l'appli détecte automatiquement mon cerf-volant,
So that il est entouré d'un overlay de suivi.

## Acceptance Criteria

1. [ ] (AC-1) Un rectangle de détection apparaît autour du cerf-volant
2. [ ] (AC-2) La position (x,y) est mise à jour en temps réel
3. [ ] (AC-3) L'état "lost" est géré correctement

## Tasks / Subtasks

- [ ] Task 1: Analyser le suivi existant
  - [ ] Subtask 1.1: Vérifier TrackingSession dans CameraFragment.onResults()
  - [ ] Subtask 1.2: Vérifier l'identification de l'état isLost
  - [ ] Subtask 1.3: Vérifier la réacquisition automatique (score >= 0.65)
- [ ] Task 2: Tests unitaires
  - [ ] Subtask 2.1: Tester markFrameProcessed() avec détection
  - [ ] Subtask 2.2: Tester markFrameProcessed() sans détection (lost)
  - [ ] Subtask 2.3: Tester onReacquired() après perte

## Dev Notes

### Existing Implementation Analysis (CameraFragment.kt)

- **Lignes 476-526**: Logique de suivi existante
- `trackingSession?.markFrameProcessed(targetFound)` - gère l'état lost après 3 frames
- `isLost` défini quand framesSinceLastSeen >= 3
- Réacquisition automatique via `computeSimilarity()` avec seuil 0.65

### Files Being Analyzed

- sources/CameraFragment.kt - Lines 476-526 (tracking logic)
- sources/TrackingSession.kt - State management
- sources/TrackingFeatureExtractor.kt - Feature extraction

### Technical Requirements

- Android API 24+ (minSdk dans build.gradle)
- MediaPipe Tasks Vision 0.10.29
- Pas de modification de code - review de l'implémentation existante

### References

- [Source: sources/CameraFragment.kt:476-526] Tracking logic existante
- [Source: sources/TrackingSession.kt] Implémentation complete

## Dev Agent Record

### Agent Model Used

kilo-auto/free

### Completion Notes List

### File List