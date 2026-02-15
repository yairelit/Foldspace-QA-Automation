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
        
        // For accurate measurement 
        assertThat(page.getByText("Hello! Share with me an important insight")).isVisible();
        
        // Base Line 
        int lengthAfterStarter = agenticPage.getCurrentTextLength();

        // --- Step 3: Wait & Verify ---
        agenticPage.waitForThinkingIndicator();
        agenticPage.waitForTextToIncrease(lengthAfterStarter);

        // --- Step 4: Verification ---
        int newLength = agenticPage.getCurrentTextLength();
        int lengthDifference = newLength - lengthAfterStarter;
        
        System.out.println("Initial (with starter): " + lengthAfterStarter + ", New Length: " + newLength);
        
        // Case 1 - the agent didnt respond
        assertTrue(lengthDifference > 0, "Test Failed! The chatbot did not respond.");
        
        // Case 2 - The agent responded with a response that was too brief
        assertTrue(lengthDifference > 30, 
            "Warning: The chatbot responded, but the message is suspiciously short (" + lengthDifference + " chars).");

        // Case 3 - the test will only pass if the text is long enough
        System.out.println("Test Passed! Response length: " + lengthDifference);

        page.waitForTimeout(3000);
    }
}
