# JustinSoh - Project Portfolio Page

## Overview

**PulsePilot** is a desktop application designed for **efficiently tracking health-related information** through a **Command Line Interface (CLI)**. For users who can type quickly, the CLI allows for faster data entry compared to traditional Graphical User Interface (GUI) applications on phones or computers.

### Summary of Contributions

The code I have written is documented [here](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=JustinSoh&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23).

Below is the breakdown of what I have done.

#### Features Added

- Implemented the `WorkoutList` class, to help track and manage newly created `Run` and `Gym` objects.

- Implemented the final `Workout`, `Gym`, `GymStation`, and `GymSet` classes.

#### Enhancements Implemented

- Implemented the `Parser.parseGymStationInput()` method, taking gym station input from the user.

- Assisted with `Storage` through the implementation of the `Gym.toFileString()` and `Parser.parseGymFileInput()`, allowing [@L5-Z](https://github.com/L5-Z) to easily integrate the saving/loading of Gym classes.

- Refactored static methods into non-static methods where instances are used instead to adopt a more Object-Oriented approach.

- Wrote integration test cases for the `Health` package supported with the `TestHelper` package to simulate end-to-end testing.

- Wrote test cases for the `Gym`, `GymStation`, `WorkoutList`, `Output`, and `Parser` to help increase coverage.

#### Documentation Written

##### User Guide

- Wrote UG portion for the `Gym` and `GymStation` sections.

##### Developer Guide

- Wrote DG portion for the `Gym` and `GymStation` sections.
- Created sequence diagrams for `Gym` and `GymStation`.
- Worked with [@rouvinerh](https://github.com/rouvinerh) to review sequence diagrams for `Run`, `Latest`, `History`, and `Handler` sections.

#### Other Contributions

##### Project Management

- Liaised with [@L5_Z](https://github.com/L5-Z) and [@rouvinerh](https://github.com/rouvinerh) to integrate the `Handler` and `Storage` classes with the `Run` and `Gym` classes.
- Created integration tests to ensure better coverage.
