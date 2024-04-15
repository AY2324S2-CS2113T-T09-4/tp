# L5-Z - Project Portfolio Page

## Overview

**PulsePilot** is a desktop application designed for **efficiently tracking health-related information** through a **Command Line Interface (CLI)**. For users who can type quickly, the CLI allows for faster data entry compared to traditional Graphical User Interface (GUI) applications on phones or computers.


### Summary of Contributions

The code I have written is documented [here](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=l5-z&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other).

Below is the breakdown of what I have done.

#### Features Added

- Implemented the main points of the `Handler` class, to help direct user inputs to all other classes and support creation of all objects.
  - Performs substring extraction to pass as parameters to the relevant classes and successfully execute commands

- Implemented the `DataFile` class, to help allow for persistence through a stored and loaded `.txt` file.
  - File is highly resistant to tampering through a hashing function and hash comparison.

- Implemented all outputs methods relating to UI such as ASCII Art within the `Output` class.


#### Enhancements Implemented

- Implemented the `Parser.extractSubstringFromSpecificIndex()` method, which allows for flexible use case and implementation across all classes that require substring attached to relevant flags in order to function. Allows for flags and substrings to not be declared in order.  

- Implemented the `LogFile.getInstance()` allowing the team to instantiate a singular instance of LogFile to be used across all classes.

- Refactored Constants into smaller, specific classes to unify all similar constant usages.
  - Supports readability and searching of constants specific to a class
  - Common constants used across all classes in `ErrorConstant` and `UiConstant`.

- Worked with [@JustinSoh](https://github.com/JustinSoh) to easily integrate the saving/loading of Gym classes.

- Wrote the skeletal structure of various classes for other team members to fill, including `Appointment`.

- Refactored long methods where applicable, and moved author tags around.

- Wrote Integration Test cases for the bulk of `Handler` and `Datafile` classes.

#### Documentation Written


##### User Guide

- Reviewed and improved clarity of the language used in UG and ensured consistency throughout the document.
- Replaced expected output with images.
- Wrote UG portion for `Help`, `Exit`, `DataFile`(Storage), `Logging` and **FAQ**.


##### Developer Guide

- Wrote DG portion for `DataFile`(Storage) and `Handler`.
- Worked with [@rouvinerh](https://github.com/rouvinerh) to create sequence diagrams for Handler and DataFile.


#### Other Contributions

##### Project Management

- Helped to ensure that the dashboard icons for our team members and our team is green.
  - Worked with our TA and [@JustinSoh](https://github.com/JustinSoh) to resolve an issue with the dashboard's weekly requirement.
- Liaised between [@JustinSoh](https://github.com/JustinSoh) and [@rouvinerh](https://github.com/rouvinerh) to integrate the `Handler` and `DataFile` class with the `Run` and `Gym` classes.
- Liaised between [@syj02](https://github.com/syj02) and [@j013n3](https://github.com/j013n3) to integrate the `Handler` and `DataFile` class with the `Bmi`, `Period` and `Appointment` classes. 