## ADDED Requirements

### Requirement: Transitions musicales basées sur le mouvement
The system SHALL trigger riser, drop, and impact effects based on motion events.

#### Scenario: Riser sur accélération
- **WHEN** acceleration suddenly increases (2x threshold)
- **THEN** generate upward frequency sweep over 1-2 seconds

#### Scenario: Drop sur arrêt soudain
- **WHEN** object stops abruptly (velocity → 0 in one frame)
- **THEN** momentary silence (100ms) followed by impact

#### Scenario: Impact sur changement de direction
- **WHEN** direction change > 45° detected
- **THEN** trigger noise burst with quick decay

## ADDED Requirements

### Requirement: Système de couches musicales
The system SHALL maintain independent synth layers that can be activated based on intensity.

#### Scenario: Activation des couches progressives
- **WHEN** speed increases from low to high
- **THEN** pad layer → pad+bass → pad+bass+percussion+lead

#### Scenario: Désactivation douce des couches
- **WHEN** energy decreases
- **THEN** fade out layers in reverse order over 500ms