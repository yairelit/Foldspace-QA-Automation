# Foldspace QA Automation Assignment

This repository contains the automated testing solution for the Foldspace.ai home assignment,
using **Playwright** with **Java**.

## 🛠 Tech Stack

* **Language:** Java (JDK 17+)
* **IDE:** IntelliJ IDEA
* **AI Assistance:** GitHub Copilot
* **Framework:** Playwright
* **Test Runner:** JUnit 5
* **Build Tool:** Maven

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

## ⚙️ Configuration
Currently, the test credentials are defined within the test files for simplicity.
To run the tests with your own user:
1.  Open `src/test/java/ai/foldspace/tests/Step2AgenticFlowTest.java`.
2.  Update the `login` method arguments with your valid email and password inside the `testAgenticFlowExact` method.

## ▶️ Running the Tests

### Option 1: Via Command Line (Terminal)
To execute all tests:
```bash
mvn test
```
To run only the Agentic UX flow test:
```bash
mvn -Dtest=Step2AgenticFlowTest test
```
### Option 2: Via IDE (IntelliJ / VS Code)
* Navigate to `src/test/java/ai/foldspace/tests/Step2AgenticFlowTest.java`.
* Click the **Run** icon (Green triangle) next to the class name or the `@Test` method.

