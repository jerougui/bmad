## MODIFIED Requirements

### Requirement: Schéma de configuration JSON
The system SHALL support an optional `instrument` field in `music-config.json` to select timbre.

#### Scenario: Champ instrument présent
- **WHEN** config contains `"instrument": "piano"`
- **THEN** `MappingConfig.instrument` returns "piano"

#### Scenario: Champ instrument absent
- **WHEN** config omits `instrument` field
- **THEN** `MappingConfig.instrument` returns "default"

## ADDED Requirements

### Requirement: Champ instrument dans le data class
The system SHALL include an `instrument` field in `MappingConfig` with options: "default", "violin", "flute", "piano".

#### Scenario: Valeur par défaut de l'instrument
- **WHEN** config is loaded without instrument field
- **THEN** `MappingConfig.instrument` defaults to "default"