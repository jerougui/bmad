## 1. Configuration Layer

- [x] 1.1 Modifier `MappingConfig.kt` — ajouter le champ `instrument` avec valeur par défaut
- [x] 1.2 Modifier `music-config.json` — ajouter le champ `\"instrument\": \"default\"`

## 2. Échantillons d'instruments

- [x] 2.1 Générer les échantillons OGG violon dans `res/raw/` — `violin_c4.ogg`, `violin_d4.ogg`, `violin_e4.ogg`, `violin_g4.ogg`, `violin_a4.ogg`
- [x] 2.2 Générer les échantillons OGG flûte dans `res/raw/` — `flute_c4.ogg`, `flute_d4.ogg`, `flute_e4.ogg`, `flute_g4.ogg`, `flute_a4.ogg`
- [x] 2.3 Générer les échantillons OGG piano dans `res/raw/` — `piano_c4.ogg`, `piano_d4.ogg`, `piano_e4.ogg`, `piano_g4.ogg`, `piano_a4.ogg`

## 3. Chargement des échantillons

- [x] 3.1 Modifier `NoteResourceLoader.kt` — ajouter le paramètre `instrument` pour filtrer les échantillons par préfixe

## 4. Intégration du moteur

- [x] 4.1 Modifier `MotionToMusicEngine.kt` — lire `instrument` depuis la config, charger les échantillons avec préfixe

## 5. Vérification

- [x] 5.1 `./gradlew assembleDebug` — vérifier la compilation avec les nouveaux échantillons
- [x] 5.2 Tester chaque instrument en modifiant la config et en rechargeant