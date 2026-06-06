## Why

The current object tracking stops when the selected object disappears from view, requiring the user to manually re-select it. This interrupts the tracking experience and makes continuous monitoring difficult. We need enhanced tracking that persists through occlusions and automatically recovers the target when it reappears.

## What Changes

- When an object is selected for tracking, the system will continue monitoring even if the object temporarily disappears from the camera view.
- The tracking mode remains active until the user manually stops it via the existing stop button.
- While the object is lost, the system searches for similar objects based on class and appearance features.
- When a matching object is detected again, tracking automatically resumes without user intervention.
- Only the selected object's bounding box is displayed during tracking (other detections hidden).
- The UI continues showing the selected object's bounding box in the tracking color (green) throughout.

## Capabilities

### New Capabilities
- `persistent-tracking`: Maintains tracking state when selected object disappears and automatically resumes when it reappears, searching for similar objects based on class and features.

### Modified Capabilities
*(None – this change introduces new behavior without altering existing requirement specifications.)*

## Impact

- **Tracking Logic**: Extend the tracking controller to maintain selection state across detection cycles, implement object similarity matching, and handle re-acquisition.
- **UI Overlay**: Modify rendering logic to only display the tracked object's bounding box when in tracking mode, hiding other detections.
- **State Management**: Add persistent tracking state (target object features, tracking active flag, disappearance timestamp) to survive frame processing gaps.
- **Detection Processing**: Integrate similarity matching algorithm to compare lost target against new detections.
- **Systems**: Affects the main tracking module and UI renderer; no changes to core object detection model or Android APIs.
