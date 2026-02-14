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
        int initialLength = agenticPage.getCurrentTextLength();
        agenticPage.sendPrompt("Hello! I have some problem for you to solve");

        // --- Step 3: Wait & Verify ---
        agenticPage.waitForThinkingIndicator();
        agenticPage.waitForTextToIncrease(initialLength);

        // --- Step 4: Verification ---
        int newLength = agenticPage.getCurrentTextLength();
        int lengthDifference = newLength - initialLength;
        
        System.out.println("Initial Length: " + initialLength + ", New Length: " + newLength);
        
        // Case 1 - the agent didnt respond
        assertTrue(lengthDifference > 0, "Test Failed! The chatbot did not respond.");
        
        // Case 2 - The agent responded with a response that was too brief, 
        //  which raises suspicion that the response was not carried out properly.
        assertTrue(lengthDifference > 30, 
            "Warning: The chatbot responded, but the message is suspiciously short (" + lengthDifference + " chars).");

        // Case 3 - the test will only pass if the text is long enough to verify that it is a corrective message.
        System.out.println("Test Passed! Response length: " + lengthDifference);

        // I've added this 'wait' in order to be able to see the results
        page.waitForTimeout(3000);
    }
}
