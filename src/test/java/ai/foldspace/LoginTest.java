import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.regex.Pattern;

public class LoginTest {
    public static void main(String[] args) {
        // Initialize Playwright with a Try-with-resources block for auto-closing
        try (Playwright playwright = Playwright.create()) {
            
            // Launch browser in headed mode to visually verify actions
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // --- Step 1: Navigation ---
            System.out.println("Navigating to login page...");
            page.navigate("https://app.foldspace.ai/login");

            // --- Step 2: Perform Login Actions ---
            // Fill in the user credentials
            page.getByPlaceholder("Email Address").fill("yelitzur@g.jct.ac.il");
            page.getByPlaceholder("Password").fill("199Yse5!");
            
            // Click the 'Sign in' button to submit the form
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();

            // --- Step 3: Verify Successful Login ---
            // Copilot prompt / Logic:
            // Verify that the user is redirected to the app dashboard by checking the URL pattern.
            // We use Regex to match "foldspace.ai/agent" regardless of the dynamic Agent ID.
            assertThat(page).hasURL(Pattern.compile(".*foldspace.ai/agent.*"));

            System.out.println("Test Passed! Login successful.");

            // Optional: Leave the browser open for a second for the result to show
            // page.waitForTimeout(2000);
            
            browser.close();
        }
    }
}
