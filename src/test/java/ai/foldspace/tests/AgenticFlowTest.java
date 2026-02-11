package ai.foldspace.tests;

import com.microsoft.playwright.*;
import ai.foldspace.pages.AgenticPage;
import org.junit.jupiter.api.*;
import java.util.regex.Pattern;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AgenticFlowTest {

    static Playwright playwright;
    static Browser browser;
    Page page;
    AgenticPage agenticPage;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(1000));
    }

    @BeforeEach
    void setup() {
        // Initialize Context and Page for each test
        BrowserContext context = browser.newContext();
        page = context.newPage();
        agenticPage = new AgenticPage(page);
    }

    @Test
    void testAgenticFlowExact() {
        // --- Step 1: Login Flow ---
        agenticPage.navigateToLogin();
        agenticPage.login("yelitzur@g.jct.ac.il", "199Yse5!");

        // Validate successful login by checking the URL pattern
        assertThat(page).hasURL(Pattern.compile(".*foldspace.ai/agent.*"));
        System.out.println("Login Successful. Starting Agentic Flow...");

        // --- Step 2: Interact with the Agent ---
        agenticPage.clickNewAgent();

        int initialLength = agenticPage.getCurrentTextLength();

        agenticPage.sendPrompt("Hello! I have some problem for you to solve");

        // --- Step 3: Wait for Response ---
        agenticPage.waitForThinkingIndicator();

        // --- Step 4: Verification ---
        agenticPage.waitForTextToIncrease(initialLength);

        // Final validation logic (Calculations kept in Test as in your original main method)
        int newLength = agenticPage.getCurrentTextLength();
        int lengthDifference = newLength - initialLength;

        System.out.println("Initial Length: " + initialLength + ", New Length: " + newLength);

        if (lengthDifference > 0) {
            if (lengthDifference > 30) {
                System.out.println("Test Passed! The chatbot responded with a substantial message.");
            } else {
                System.out.println("Warning: The chatbot responded, but the message is very short.");
            }
        } else {
            System.err.println("Test Failed! The chatbot did not respond (No text added).");
        }
        
        // Small pause to visualize result
        page.waitForTimeout(3000);
    }

    @AfterEach
    void tearDown() {
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
