## ADDED Requirements

### Requirement: Mapping intensité mouvement → paramètres musicaux
The system SHALL map motion speed to synthesis parameters (filter cutoff, waveform type, layer activation).

#### Scenario: Mouvement lent - atmosphère douce
- **WHEN** speed < 0.2 and stable
- **THEN** active pad layer with sine wave, cutoff 800Hz, reverb high

#### Scenario: Mouvement modéré - énergie moyenne
- **WHEN** 0.2 < speed < 0.6 and fluid motion
- **THEN** activate pad + bass layers with square wave bassline

#### Scenario: Mouvement rapide - énergie maximale
- **WHEN** speed > 0.6 with bursts
- **THEN** activate all layers: pads, bass, percussion, lead synth

## ADDED Requirements

### Requirement: Détection des changements de direction
The system SHALL detect direction changes > 45° and trigger musical transitions.

#### Scenario: Changement de direction marqué
- **WHEN** direction change exceeds 45° in one frame
- **THEN** trigger synth modulation (filter sweep) and riser effect

## ADDED Requirements

### Requirement: Détection d'accélération soudaine
The system SHALL detect acceleration spikes and trigger impact transitions.

#### Scenario: Accélération brusque
- **WHEN** acceleration exceeds 2x the smoothed average
- **THEN** trigger riser effect transitioning to higher energy state