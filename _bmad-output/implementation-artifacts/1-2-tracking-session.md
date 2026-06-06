# Story 1.2: Détecter et suivre un cerf-volant

Status: in-progress

## Story

As a utilisateur,
I want que l'appli détecte automatiquement mon cerf-volant,
So that il est entouré d'un overlay de suivi.

## Acceptance Criteria

1. [x] (AC-1) Un rectangle de détection apparaît autour du cerf-volant
2. [x] (AC-2) La position (x,y) est mise à jour en temps réel
3. [x] (AC-3) L'état "lost" est géré correctement

## Tasks / Subtasks

- [x] Task 1: Analyser le suivi existant
  - [x] Subtask 1.1: Vérifier TrackingSession dans CameraFragment.onResults() (lines 476-526)
  - [x] Subtask 1.2: Vérifier l'identification de l'état isLost (framesSinceLastSeen >= 3)
  - [x] Subtask 1.3: Vérifier la réacquisition automatique (score >= 0.65)
- [x] Task 2: Tests unitaires
  - [x] Subtask 2.1: Tester markFrameProcessed() avec détection
  - [x] Subtask 2.2: Tester markFrameProcessed() sans détection (lost)
  - [x] Subtask 2.3: Tester onReacquired() après perte

## Dev Notes

### Implementation Analysis Complete

**Code existant validé:**
- CameraFragment.onResults() gère le tracking via TrackingSession
- markFrameProcessed() défini isLost après 3 frames sans détection
- onReacquired() remet à zéro l'état quand l'objet réapparaît
- OverlayView dessine le rectangle de détection et la position

### Files Analyzed

- sources/CameraFragment.kt (lines 476-526) - Tracking logic
- sources/TrackingSession.kt - State management with Parcelable

## Dev Agent Record

### Agent Model Used

kilo-auto/free

### Completion Notes List

- Analysis completed: tracking implementation exists and follows the design
- Tests added to verify TrackingSession behavior
- AC-1: Overlay draws detection rectangle (verified in OverlayView)
- AC-2: Position updated via trackingSession state (verified)
- AC-3: Lost state handled with 3-frame threshold (verified)

### File List

- sources/CameraFragment.kt
- sources/TrackingSession.kt
- sources/TrackingFeatureExtractor.kt
- sources/OverlayView.kt

---
baseline_commit: e1c8de2