import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.regex.Pattern;

public class AgenticFlowTest {
    public static void main(String[] args) {
        // Initialize Playwright with a Try-with-resources block for auto-closing
        try (Playwright playwright = Playwright.create()) {

            // Launch browser in headed mode with slow-mo for visibility
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setSlowMo(1000));

            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // --- Step 1: Login Flow ---
            System.out.println("Navigating to login page...");
            page.navigate("https://app.foldspace.ai/login");

            page.getByPlaceholder("Email Address").fill("yelitzur@g.jct.ac.il");
            page.getByPlaceholder("Password").fill("199Yse5!");

            // Click 'Sign in' and wait for navigation to start
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();

            // Validate successful login by checking the URL pattern
            assertThat(page).hasURL(Pattern.compile(".*foldspace.ai/agent.*"));
            System.out.println("Login Successful. Starting Agentic Flow...");

            // --- Step 2: Interact with the Agent ---

            // Select the agent to start a conversation
            page.getByLabel("New Agent").click();

            // Capture the initial amount of text on the screen before sending the prompt.
            // We use 'evaluate' to execute JavaScript in the browser context.
            int initialLength = (int) page.evaluate("document.body.innerText.length");

            // Send the prompt to the chatbot
            System.out.println("Sending prompt to agent...");
            page.getByRole(AriaRole.TEXTBOX).fill("Hello! I have some problem for you to solve");
            page.keyboard().press("Enter");

            // --- Step 3: Wait for Response (The "Thinking" Phase) ---

            // Wait for the UI indicator "thinking..." to appear (confirming the request was sent)
            try {
                page.waitForSelector("text=thinking...", new Page.WaitForSelectorOptions().setTimeout(5000));
                // Wait for the "thinking..." indicator to disappear (confirming the response is ready)
                page.waitForSelector("text=thinking...", new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.DETACHED));
            } catch (PlaywrightException e) {
                // If "thinking" happens too fast or text is different, we log it but don't fail,
                // as we rely on text length check next.
                System.out.println("Note: 'Thinking' state was skipped or too fast to catch.");
            }

            // --- Step 4: Verification (Content Length Check) ---

            // Wait until the text length on the page is strictly greater than the initial length.
            // This confirms that *something* was added to the DOM.
            page.waitForFunction("initialLength => document.body.innerText.length > initialLength", initialLength);

            // Final validation logic
            int newLength = (int) page.evaluate("document.body.innerText.length");
            int lengthDifference = newLength - initialLength;

            System.out.println("Initial Length: " + initialLength + ", New Length: " + newLength);

            if (lengthDifference > 0) {
                // Heuristic check: A very short difference might be just a status update.
                // We expect a substantial response (> 30 characters).
                if (lengthDifference > 30) {
                    System.out.println("Test Passed! The chatbot responded with a substantial message.");
                } else {
                    System.out.println("Warning: The chatbot responded, but the message is very short.");
                }
            } else {
                // This block acts as a manual assertion failure
                System.err.println("Test Failed! The chatbot did not respond (No text added).");
                // Optional: throw new RuntimeException("Test Failed");
            }

            // Small pause to visualize the result before closing
            page.waitForTimeout(3000);
            browser.close();
        }
    }
}
