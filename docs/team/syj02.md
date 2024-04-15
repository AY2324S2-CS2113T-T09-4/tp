# syj02 - Project Portfolio Page

## Overview

**PulsePilot** is a desktop application designed for **efficiently tracking health-related information** through a **Command Line Interface (CLI)**. For users who can type quickly, the CLI allows for faster data entry compared to traditional Graphical User Interface (GUI) applications on phones or computers.

### Summary of Contributions

The code I have contributed is [here](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=syj02&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other).

#### Features Implemented

- Implemented Health and HealthList with the help of [@j013n3](https://github.com/j013n3)
  - Health is the superclass of Bmi, Period, and Appointment that is managed by HealthList class.
  - HealhtList consists of methods to add new Health object, delete and print all Health objects recorded.

- Implemented Bmi class and its tests with the help of [@j013n3](https://github.com/j013n3) 
  - For users to obtain their BMI value and which category that falls under (i.e. underweight, overweight, etc), from their height and weight input

- Implemented the first stage of the Period class and its tests.
  - The recording of Period object and the calculation of period length.

- Implemented the Appointment class and its tests.
  - The recording of Appointment object with date, time, and description to be specified.
  - Add parse time method for users to add their time in LocalTime type.

#### Enhancements Implemented

- In health package: Modified all the add health object methods to sort the respective health list according to the given date input.
  - BMI and Period in descending order (i.e. The most recent date at the top) for users to see their most updated record and not just the last record added, just in case, they forgot to add a past record.
  - Appointment in ascending order (i.e. The earliest date and time at the top) for users to have a view of all appointments history.

#### Contributions to the UG

I wrote the UG portion of Appointment and Period prediction, including the description of each parameter, example commands, expected output, warnings and tips for users.

#### Contributions to the DG

- Overview of components and the Architecture diagram.
- Design of Health package and HealthList class diagram.
- Add descriptions of add Appointment command.

#### Contributions beyond the project team

- Reported a high number of bugs in PE-D (Top 10%).

#### Project management

- Interfaced between [@l5-z](https://github.com/l5-z) and [@j013n3](https://github.com/j013n3) to integrate the `Handler` and `DataFile` class with the `Bmi`, `Period` and `Appointment` classes. 
