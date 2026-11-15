# Spec Delta

## Purpose

Defines the buildable Android application shell that every later feature of the sheet-music practice app builds on, including supported platform levels, the launch screen, theme modes, and the localization baseline.

## ADDED Requirements

### Requirement: Supported Android platform range
The application SHALL declare a minimum supported Android version of API 26 (Android 8.0) and a target version of API 36 (Android 16), and SHALL compile against API 36.

#### Scenario: Installation on a supported device
- **WHEN** the debug APK is installed on a device running Android 8.0 (API 26) or newer
- **THEN** the installation is accepted by the operating system

#### Scenario: Installation on an unsupported device
- **WHEN** the debug APK is installed on a device running Android 7.1 (API 25) or older
- **THEN** the operating system rejects the installation

### Requirement: Launch screen
The application SHALL open to a single Compose-rendered screen when launched from the launcher, showing the application name as its title.

#### Scenario: Cold start
- **WHEN** the user launches the application from the launcher
- **THEN** the main screen is displayed with the localized application name as its title

#### Scenario: Return to application
- **WHEN** the user returns to the application from the recent apps list
- **THEN** the main screen is displayed without restarting the application state

### Requirement: Theme mode selection
The application SHALL support three theme modes: White, Dark, and System Default. System Default SHALL follow the device's current light or dark setting.

#### Scenario: Follow system theme
- **WHEN** the theme mode is System Default and the device is set to dark mode
- **THEN** the application renders with the dark theme

#### Scenario: Explicit white theme
- **WHEN** the theme mode is set to White
- **THEN** the application renders with the white theme regardless of the device setting

#### Scenario: Explicit dark theme
- **WHEN** the theme mode is set to Dark
- **THEN** the application renders with the dark theme regardless of the device setting

### Requirement: Localized user interface text
All user-visible text SHALL be provided in German and English. English SHALL be the fallback language for any device locale other than German.

#### Scenario: German device locale
- **WHEN** the device language is German
- **THEN** all user-visible text is displayed in German

#### Scenario: English device locale
- **WHEN** the device language is English
- **THEN** all user-visible text is displayed in English

#### Scenario: Unsupported device locale
- **WHEN** the device language is neither German nor English
- **THEN** all user-visible text is displayed in English

### Requirement: Responsive layout foundation
The launch screen SHALL render without clipped or overlapping content in both portrait and landscape orientation on phones and tablets.

#### Scenario: Orientation change
- **WHEN** the device is rotated between portrait and landscape
- **THEN** the screen content is laid out for the new orientation without clipping or overlapping elements

#### Scenario: Tablet layout
- **WHEN** the application runs on a tablet-sized screen
- **THEN** the content remains usable and is not stretched to the full width of the screen
