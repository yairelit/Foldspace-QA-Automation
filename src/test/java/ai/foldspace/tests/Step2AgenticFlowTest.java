package ai.foldspace.tests;

import ai.foldspace.pages.AgenticPage;
import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
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

        int newLength = agenticPage.getCurrentTextLength();
        int lengthDifference = newLength - initialLength;

        System.out.println("Initial Length: " + initialLength + ", New Length: " + newLength);

        if (lengthDifference > 30) {
            System.out.println("Test Passed! Substantial message received.");
        } else if (lengthDifference > 0) {
            System.out.println("Warning: Short response.");
        } else {
            System.err.println("Test Failed: No response.");
        }
        
        page.waitForTimeout(3000);
    }
}
