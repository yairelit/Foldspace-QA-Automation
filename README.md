# Foldspace QA Automation Assignment

This repository contains the automated testing solution for the Foldspace.ai home assignment,
using **Playwright** with **Java**.

## 🛠 Tech Stack
* **Language:** Java (JDK 17+)
* **Framework:** Playwright (for browser automation)
* **Test Runner:** JUnit 5
* **Build Tool:** Maven

## 📂 Project Structure
The project follows the **Page Object Model (POM)** design pattern to ensure separation of concerns and maintainability.

```text
src/
├── main/java/ai/foldspace/pages/   # Page Objects (UI Logic & Locators)
│   ├── AgenticPage.java            # Encapsulates Login, Navigation, and Agent interactions
├── test/java/ai/foldspace/tests/   # Test Classes (Assertions & Execution)
│   ├── AgenticFlowTest.java        # Main E2E test scenarios
notes/                              # Documentation deliverables
├── ai-usage.md                     # AI tools usage report
├── manual-scenarios.md             # Manual QA scenarios & Edge cases
pom.xml                             # Maven dependencies and configuration
```


##  🚀 Getting Started

### Prerequisites
Ensure you have the following installed:
1.  **Java JDK 17** or higher.
2.  **Maven** (check by running `mvn -version`).

### Installation
1.  Clone the repository:
    ```bash
    git clone https://github.com/yairelit/Foldspace-QA-Automation
    cd Foldspace-QA-Automation
    ```
2.  Install dependencies and browsers:
    ```bash
    mvn clean install
    ```
    *Note: Playwright will automatically attempt to download the necessary browser binaries during the first run.*

## ⚙️ Configuration
Currently, the test credentials are defined within the test files for simplicity.
To run the tests with your own user:
1.  Open `src/test/java/ai/foldspace/tests/AgenticFlowTest.java`.
2.  Update the `login` method arguments with your valid email and password inside the `testAgenticFlowExact` method.

## ▶️ Running the Tests

### Option 1: Via Command Line (Terminal)
To execute all tests:
```bash
mvn test
```
To run only the Agentic UX flow test:
```bash
mvn -Dtest=AgenticFlowTest test
```
### Option 2: Via IDE (IntelliJ / VS Code)
* Navigate to `src/test/java/ai/foldspace/tests/AgenticFlowTest.java`.
* Click the **Run** icon (Green triangle) next to the class name or the `@Test` method.

## 🤖 AI Usage
As requested, AI tools were utilized during the development of this assignment.
* **Code Generation:** Used to scaffold the initial Maven project structure and Page Object boilerplate.
* **Optimization:** Assisted in refining the `waitForFunction` logic to accurately detect dynamic text changes in the agent response.
* *Full details can be found in `notes/ai-usage.md`.*

## 📝 Manual Testing & Scenarios
Analysis of edge cases, failure scenarios, and exploratory testing notes are located in the `notes/` directory:
* [Manual Scenarios & Edge Cases](notes/manual-scenarios.md)
