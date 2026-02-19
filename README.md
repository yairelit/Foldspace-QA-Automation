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

## 📂 Project Structure

The project follows a standard Maven directory structure, organized by the Page Object Model (POM) design pattern:

```text
src
├── main/java/ai/foldspace
│   ├── pages             # Page Objects representing UI components
│   └── utils             # Helper classes (Dotenv loader, Playwright factory)
└── test/java/ai/foldspace
    └── tests             # Test suites and execution flows
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

## ⚙️ Configuration

The project uses environment variables to manage credentials securely and prevent sensitive data from being exposed in the source code.

1. **Create a `.env` file** in the project root directory (this file is excluded from Git via `.gitignore`).
2. **Copy the template** from `.env.example` into your new `.env` file:
   ```env
   USER_EMAIL=your_email@example.com
   USER_PASSWORD=your_password
   ```
3. **The automation framework** will automatically pull these credentials during test execution.

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

