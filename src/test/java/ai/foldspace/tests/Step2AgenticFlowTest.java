package ai.foldspace.tests;

import ai.foldspace.pages.AgenticPage;
import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Step2AgenticFlowTest extends BaseTest {

    @Test
    void testAgenticFlowExact() {
        AgenticPage agenticPage = new AgenticPage(page);

        // --- Step 1: Login ---
        
        agenticPage.navigateToLogin();
        agenticPage.login("yelitzur@g.jct.ac.il", "199Yse5!");
        assertThat(page).hasURL(Pattern.compile(".*foldspace.ai/agent.*"));

        // --- Step 2: Interact ---
        
        agenticPage.clickNewAgent();
        agenticPage.clickStarterPrompt(); 

        // --- Step 3: Wait & Verify ---

        // Synchronize with the UI to ensure the starter prompt is rendered before capturing the baseline length.
        assertThat(page.getByText("Hello! Share with me an important insight")).isVisible();
        
        // Capture the baseline length (UI + Starter Prompt). 
        // This serves as a reference point to isolate and measure the agent's net response later.
        int lengthAfterStarter = agenticPage.getCurrentTextLength();
        
        // Synchronize with the backend processing state. 
        // We wait for the 'thinking' phase to conclude to ensure the agent has transitioned to generating content.
        agenticPage.waitForThinkingIndicator();
        
        // Final synchronization for DOM rendering. 
        // Since AI responses are often streamed, this ensures the browser has physically rendered the new characters 
        // into the DOM before we perform the final measurement, preventing a "race condition" failure.
        agenticPage.waitForTextToIncrease(lengthAfterStarter);
        
        // --- Step 4: Verification & Response Analysis ---
        
        // Capture the final text length of the entire page after the streaming process is complete.
        int newLength = agenticPage.getCurrentTextLength();
        
        // Calculate the 'Delta': This isolates the agent's net contribution by subtracting 
        // the static UI and the user's prompt (baseline) from the final state.
        int lengthDifference = newLength - lengthAfterStarter;
        
        System.out.println("Initial (with starter): " + lengthAfterStarter + ", New Length: " + newLength);
        
        // Case 1: Functional Validation.
        // Ensures that the interaction actually triggered a response and added content to the DOM.
        assertTrue(lengthDifference > 0, "Test Failed! The chatbot did not respond (No text was added).");
        
        // Case 2: Content Quality Heuristic.
        // We verify that the response is substantial (e.g., > 30 characters).
        // This filters out generic error messages, empty bubbles, or failed API calls that might still return a 'success' state.
        assertTrue(lengthDifference > 30, 
            "Warning: The chatbot responded, but the message is suspiciously short (" + lengthDifference + " chars).");
        
        // Case 3: Success Confirmation.
        // If both assertions pass, we conclude that the agentic flow was executed successfully with meaningful output.
        System.out.println("Test Passed! Response length: " + lengthDifference);
        
        // Visualization Pause: 
        // Allows the developer to manually inspect the final UI state before the session is terminated by the BaseTest.
        page.waitForTimeout(3000);
    }
}
