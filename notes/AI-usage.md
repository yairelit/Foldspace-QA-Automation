# AI Usage Disclosure

## Project Overview
I chose to develop this project, focusing on **QA Automation for Agentic Flows**, using a collaborative approach between myself and AI assistance (Google Gemini & GitHub Copilot). My goal was to leverage these tools for efficiency in boilerplate generation, architectural planning, and solving the complex synchronization challenges inherent in testing AI-driven platforms.

## Areas of AI Assistance

### 1. Test Strategy & Logic Design
I used AI (specifically **GitHub Copilot**) to help me brainstorm and refine the **Text Delta Measurement** strategy. I designed this logic to handle the non-deterministic nature of AI responses by:
* Calculating length differences between UI states to isolate the agent's net response.
* Identifying the need for a "Baseline" measurement to account for disappearing UI elements (like starters) that could otherwise skew my results.

### 2. Code Implementation & General Testing
* **URL Assertions:** I used GitHub Copilot to help me identify and implement the initial URL verification assertions (e.g., using `assertThat(page).hasURL()`) to ensure successful navigation and authentication.
* **Test Implementation:** I relied on Copilot's real-time suggestions for implementing various test methods, which helped me streamline the development of the main execution flow.
* **Boilerplate Generation:** I used AI to provide the initial structures for the Page Object Model (POM) using Playwright and Java.
* **Debugging:** I used AI to help me interpret Maven compilation errors and resolve Java scope issues during my refactoring process.

### 3. Synchronization & Async Handling
I focused a significant portion of my work on handling **Race Conditions**. I used AI to assist me in designing a synchronization flow to manage the transition from backend processing (Thinking state) to frontend rendering (Streaming text).

---

## Human Oversight & Validation
While I used AI for suggestions and code snippets, I maintained final responsibility for the project's integrity:
* **Requirement Engineering:** I defined the specific testing needs based on my analysis of the Foldspace platform's behavior.
* **Validation:** I manually reviewed every AI-generated suggestion, compiled it via Maven, and validated it through execution in both headed and headless browser modes.
* **Edge Case Analysis:** I identified critical edge cases, such as the disappearance of Starter Prompts, and manually adjusted the baseline measurement logic to handle them correctly.

## Tools Used
* **AI Models:** Google Gemini (Architectural advice and documentation), GitHub Copilot (Logic implementation, URL assertions, and code completion).
* **Purpose:** Architectural advice, logic development, code generation, and technical troubleshooting.
