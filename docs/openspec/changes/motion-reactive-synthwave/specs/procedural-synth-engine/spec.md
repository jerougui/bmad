## ADDED Requirements

### Requirement: Génération de formes d'onde procédurales
The system SHALL generate sine, triangle, and square waveforms programmatically based on musical parameters.

#### Scenario: Forme d'onde sinus pour pads atmosphériques
- **WHEN** motion intensity is low (speed < 0.2)
- **THEN** SynthEngine generates a sine wave with low-pass filter at 800Hz

#### Scenario: Forme d'onde carrée pour basse pulsée
- **WHEN** motion intensity is medium (0.2 < speed < 0.6)
- **THEN** SynthEngine generates a square wave with filter cutoff at 1200Hz

#### Scenario: Bruit blanc pour impacts
- **WHEN** acceleration exceeds threshold
- **THEN** SynthEngine generates noise burst for percussion effect

## ADDED Requirements

### Requirement: Architecture de synthèse en temps réel
The system SHALL use AudioTrack in streaming mode for low-latency audio output.

#### Scenario: Démarrage du moteur de synthèse
- **WHEN** MotionToMusicEngine starts
- **THEN** SynthEngine initializes AudioTrack with 512-sample buffer at 44.1kHz

#### Scenario: Arrêt propre du moteur
- **WHEN** MotionToMusicEngine stops
- **THEN** SynthEngine drains remaining audio and releases AudioTrack