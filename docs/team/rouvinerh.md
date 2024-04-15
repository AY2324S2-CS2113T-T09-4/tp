# rouvinerh - Project Portfolio Page

## Overview

**PulsePilot** is a desktop application designed for **efficiently tracking health-related information** through a **Command Line Interface (CLI)**. For users who can type quickly, the CLI allows for faster data entry compared to traditional Graphical User Interface (GUI) applications on phones or computers.


### Summary of Contributions

The code I have written is documented [here](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=rouvinerh&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other).

Below is the breakdown of what I have done.

#### Features Added

- Implemented the first iteration of the `Run`, `Gym` and `Workout` classes.
    - This allows PulsePilot to track the user's workout information.
- Implemented the `latest`, `delete` and `history` commands.
    - Allows user to view either their latest or all objects added to PulsePilot based on a filter string.
    - Allows user to delete items from PulsePilot.

#### Enhancements Implemented

- Added `Validation` class to handle input validation from both user and data file. This involved refactoring my teammates' code into one central class for data validation, and moving the author tags, and adding other methods.
    - This allows our team to easily handle all input validation, since methods are abstracted in a separate class.
    - The `Validation` class prevents users from entering malformed input and makes our code more defensive against bugs or unintended use.
    - It also allows for different error messages to be printed for the user, ensuring they are clear on what is wrong.

- Wrote a majority of `ValidationTest` test cases.

- Worked [@JustinSoh](https://github.com/JustinSoh) to finalise `Workout` and `WorkoutLists`.
- Wrote test cases for `Validation`, `Handler`, `Parser`, and `Output` to increase coverage.
- Implemented the final version of `Run`.
    - Split user input validation and calculation to prevent doing double work.

#### Documentation Written

##### User Guide

- Wrote the skeletal structure of the UG for other team members to fill.
- Replaced all expected output with images.
- Wrote UG portion for Run, History, Latest and Delete.

##### Developer Guide

- Wrote the skeletal structure of the DG for other team members to fill.
- Worked with [@JustinSoh](https://github.com/JustinSoh) to create sequence diagrams for the Run, Gym, Latest, History, and Handler sections.
- Worked with [@L5-Z](https://github.com/L5-Z) to create sequence diagrams for Storage section.
- Wrote DG for Run, Latest, History and Delete sections.
- Wrote manual testing portion of DG.
- Reduced complexity of diagrams and guide to ensure that it is not over cluttered.

#### Other Contributions

- Editing of Javadocs where needed.
- Adding author tags.
- Writing PlantUML code for diagrams in DG and replacing images where needed.
- Helped teammates with whatever code issues they were facing when needed.
- Refactored magic strings and numbers where needed.

##### Project Management

- Helped to ensure that the dashboard icons for our team members and our team is green.
- Worked with our TA and [@JustinSoh](https://github.com/JustinSoh) to make sure our sequence and class diagrams were implemented properly and were not too complicated.
- Reviewed PRs where needed.
- Tagged issues to teammates, and ensured that it closed before moving on.