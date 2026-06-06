## ADDED Requirements

### Requirement: Sélection d'instrument via configuration
The system SHALL allow users to select an instrument timbre through the `instrument` field in `music-config.json`.

#### Scenario: Chargement de l'instrument par défaut
- **WHEN** app starts with default config (no instrument specified)
- **THEN** system uses triangle wave samples (current behavior)

#### Scenario: Instrument violon sélectionné
- **WHEN** `music-config.json` contains `"instrument": "violin"`
- **THEN** system loads `violin_c4.ogg`, `violin_d4.ogg`, etc. from `res/raw/`

### Requirement: Instruments disponibles
The system SHALL support violin, flute, and piano instruments.

#### Scenario: Validation de l'instrument
- **WHEN** config specifies `"instrument": "saxophone"`
- **THEN** system falls back to default triangle samples with a warning log

### Requirement: Rechargement à chaud des changements d'instrument
The system SHALL load new instrument samples when config is reloaded without app restart.

#### Scenario: Changement d'instrument en temps réel
- **WHEN** user edits `music-config.json` to change `"instrument"` value
- **THEN** `reloadConfig()` loads the new instrument samples and plays them immediately

## ADDED Requirements

### Requirement: Convention de nommage des échantillons
The system SHALL expect instrument samples named `<instrument>_<notename>.ogg` format.

#### Scenario: Noms d'échantillons corrects
- **WHEN** instrument is "violin" and note is "c4"
- **THEN** system looks for `violin_c4.ogg` in `res/raw/`