## ADDED Requirements

### Requirement: Maintain tracking until manual stop
The system SHALL keep tracking mode active after an object is selected, even when that object temporarily disappears from the camera view, until the user explicitly stops tracking via the stop button.

#### Scenario: Tracking remains active when object leaves view
- **WHEN** the user has selected an object and is tracking it
- **AND** the selected object moves out of the camera frame
- **THEN** the system SHALL remain in tracking mode with tracking state preserved
- **AND** the system SHALL continue searching for the object in subsequent frames

#### Scenario: Manual stop ends tracking
- **WHEN** tracking mode is active (object visible or lost)
- **AND** the user presses the stop tracking button
- **THEN** the system SHALL immediately end tracking mode
- **AND** the system SHALL clear the selection and hide tracking data

### Requirement: Automatic object re-acquisition
The system SHALL automatically resume tracking when a previously lost object reappears by matching it against stored target features (class label, color histogram, aspect ratio).

#### Scenario: Re-acquire object with matching class
- **WHEN** tracking mode is active and the selected object is lost
- **AND** a new detection appears with the same class label as the target
- **AND** the appearance similarity score exceeds 0.65
- **THEN** the system SHALL resume tracking that object
- **AND** the system SHALL update the displayed bounding box to the new detection's position
- **AND** the system SHALL set isLost to false

#### Scenario: Ignore non-matching detections while lost
- **WHEN** tracking mode is active and the selected object is lost
- **AND** a new detection appears with a different class label than the target
- **THEN** the system SHALL NOT resume tracking
- **AND** the tracked object SHALL remain in lost state

#### Scenario: Appearance similarity threshold prevents false positives
- **WHEN** tracking mode is active and the selected object is lost
- **AND** a new detection appears with matching class but low appearance similarity (< 0.65)
- **THEN** the system SHALL continue searching without resuming tracking

### Requirement: Exclusive tracking display
The system SHALL display only the currently tracked object's bounding box when tracking mode is active, hiding all other detected objects from the UI overlay.

#### Scenario: Only tracked object shown during active tracking
- **WHEN** tracking mode is active and the selected object is currently detected (not lost)
- **THEN** the system SHALL render the tracked object's bounding box in green
- **AND** the system SHALL NOT render bounding boxes for any other detected objects

#### Scenario: No bounding boxes shown when object is lost (optional refinement)
- **WHEN** tracking mode is active and the selected object is lost
- **THEN** the system MAY hide all bounding boxes OR SHALL display the last known position with a dashed outline
- **AND** the system SHALL NOT render bounding boxes for non-tracked detections

#### Scenario: Resume display when object re-acquired
- **WHEN** tracking mode is active and the object was previously lost
- **AND** the system re-acquires the object
- **THEN** the system SHALL immediately render the tracked object's bounding box in green
- **AND** the system SHALL continue to hide all other detections

### Requirement: Tracking state persistence
The system SHALL maintain tracking state (target features, target class, active flag, lost flag) across consecutive detection frames until tracking is manually stopped or the application exits.

#### Scenario: State survives frame processing gaps
- **WHEN** an object is selected and tracking begins
- **AND** subsequent frames are processed (with or without the object present)
- **THEN** the tracking state SHALL remain intact across all frames

#### Scenario: State cleared on manual stop
- **WHEN** tracking is active
- **AND** the user stops tracking
- **THEN** the system SHALL clear all tracking state (features, class, flags)
