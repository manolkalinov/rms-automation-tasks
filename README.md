# Principal Automation QA Assignment

This project contains three submodules corresponding to three tasks:

1. Counting Duplicates
2. Selenium Automation
3. Performance Test

## Requirements
- Java 17
- Maven 3.x+

## Notes

- Maven versions and dependencies need optimization and centralization of version definitions.

## Modules

### 1. Counting Duplicates

This module contains the implementation for counting non-unique values in an array of integers.

_Note: The implementation lacks many necessary details, such as appropriate interface (GUID or Ascii, etc)_

Note: Unit test coverage should be added and a limit set.

#### Build and run

`mvn clean install -P counting-duplicates,run`

### 2. Selenium Automation

This module contains the implementation for automating tasks using Selenium.

**Important!** Allure is used for reporting. At the end of the run, the report is automatically open. If Allure is missing
(did not
have time to set it up to be downloaded as part of the build), it can be installed from https://allurereport.org/docs/install/

_Note: There is no architecture or structure, as I would have done in a real project. The current one is not scalable._

_Note: More stable CSS paths should be found._

_Note: Expected values are currently hardcoded. They would be loaded from a resource file_

_Note: Boilerplate code should be pushed away from the actual test code, so that as much as possible is injected into
class fields (e.g. WebElement factory). Test class code should remain as clean as possible_

#### Build and run

`mvn clean install -P selenium-automation,run`

### 3. Performance Test

This module contains the implementation for performance testing with mock services.

#### Build and run

`mvn clean install -P performance-testing,run`

