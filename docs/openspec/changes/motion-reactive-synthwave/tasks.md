## 1. Core Synthesis Engine

- [x] 1.1 Créer SynthEngine.kt avec AudioTrack streaming
- [x] 1.2 Implémenter génération de formes d'onde (sine, square, triangle, noise)
- [x] 1.3 Ajouter filtre low-pass contrôlé par cutoff frequency

## 2. Motion Music Mapping

- [x] 2.1 Créer MotionMusicMapper.kt pour mapper vitesse → paramètres
- [x] 2.2 Implémenter seuils d'intensité (lent/moelleux → 0.2, modéré → 0.6, rapide → 0.6+)
- [x] 2.3 Détecter changements de direction (> 45°)
- [x] 2.4 Détecter accélérations soudaines

## 3. Musical Transitions

- [x] 3.1 Créer TransitionGenerator.kt pour riser/drop/impact
- [x] 3.2 Implémenter riser (balayage fréquence ascendant 1-2s)
- [x] 3.3 Implémenter drop (silence 100ms + impact)
- [x] 3.4 Implémenter impact (bruit blanc burst)

## 4. Layer System

- [x] 4.1 Implémenter système de couches (pad, bass, percussion, lead)
- [x] 4.2 Gérer activation/désactivation progressive des couches

## 5. Integration

- [x] 5.1 Intégrer SynthEngine dans MotionToMusicEngine
- [x] 5.2 Modifier MappingConfig avec paramètres de synthèse
- [x] 5.3 Tester compilation ./gradlew assembleDebug