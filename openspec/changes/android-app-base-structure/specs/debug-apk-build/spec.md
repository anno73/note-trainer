# Spec Delta

## Purpose

Defines the automated pipeline that builds, tests, and publishes a debug APK for the sheet-music practice app, since local builds are not permitted and CI is the only verification path.

## ADDED Requirements

### Requirement: Automatic debug build on code changes
The CI pipeline SHALL build the debug APK automatically on every push to any branch and on every pull request, without manual intervention.

#### Scenario: Push to a feature branch
- **WHEN** a commit is pushed to a branch
- **THEN** the pipeline builds the debug APK

#### Scenario: Pull request update
- **WHEN** a pull request is opened or its head commit is updated
- **THEN** the pipeline builds the debug APK for the pull request's head commit

### Requirement: Unit tests run before publishing
The pipeline SHALL run the JVM unit tests before publishing the APK, and SHALL fail without publishing an artifact if any unit test fails.

#### Scenario: All unit tests pass
- **WHEN** the unit tests succeed
- **THEN** the debug APK is published as a workflow artifact

#### Scenario: A unit test fails
- **WHEN** at least one unit test fails
- **THEN** the pipeline run is marked as failed and no APK artifact is published

### Requirement: Debug APK published as artifact
The pipeline SHALL upload the built debug APK as a downloadable workflow artifact with a name that identifies the commit it was built from.

#### Scenario: Successful build
- **WHEN** the debug APK is built successfully
- **THEN** the APK is available for download from the workflow run's artifacts

#### Scenario: Artifact name identifies commit
- **WHEN** an APK artifact is uploaded
- **THEN** the artifact name contains the short commit SHA of the built revision

### Requirement: Build failure reporting
The pipeline SHALL report a failed Gradle build as a failed workflow run and SHALL retain the build logs for inspection.

#### Scenario: Compilation error
- **WHEN** the Kotlin or resource compilation fails
- **THEN** the workflow run is marked as failed and the build log shows the failing task

### Requirement: No signing secrets required for debug builds
The pipeline SHALL build the debug APK without requiring release signing keys or any repository secret.

#### Scenario: Fork or secret-less run
- **WHEN** the pipeline runs in a context without access to repository secrets
- **THEN** the debug APK is still built and published

### Requirement: Read-only repository permissions
The pipeline SHALL request only read access to repository contents.

#### Scenario: Workflow permissions
- **WHEN** the pipeline runs
- **THEN** its token has read-only access to repository contents
