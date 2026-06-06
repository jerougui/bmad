## Why

Le système actuel de motion-to-music joue toutes les notes avec les mêmes échantillons OGG triangle. Les utilisateurs souhaitent le pouvoir choisir différents timbres d'instruments (violon, flûte, piano, etc.) pour personnaliser l'expérience de sonification.

## What Changes

- Ajouter un champ `instrument` dans `music-config.json` avec les options : violin, flute, piano, strings, bass
- Ajouter des ensembles d'échantillons OGG spécifiques à chaque instrument dans `res/raw/` (ex: `violin_c4.ogg`, `flute_c4.ogg`, `piano_c4.ogg`)
- Modifier `MappingConfig.kt` pour inclure la sélection d'instrument
- Modifier `MotionToMusicEngine.kt` pour charger les échantillons spécifiques à l'instrument au démarrage/rechargement
- Ajouter un sélecteur d'instruments dans les paramètres de l'app ou le menu overlay

## Capabilities

### New Capabilities
- `instrument-selection`: Configuration et système de lecture pour plusieurs timbres d'instruments avec audio basé sur échantillons

### Modified Capabilities
- `motion-music-config`: Ajouter le champ instrument au schéma JSON pour la sélection du timbre

## Impact

- **New Files**: Échantillons OGG spécifiques (~15 fichiers par instrument × 3 instruments)
- **Modified Files**: `MappingConfig.kt`, `MotionToMusicEngine.kt`, `music-config.json`
- **No new dependencies**: Utilise l'infrastructure SoundPool existante
- **Sample size**: ~150KB supplémentaires par ensemble d'instruments