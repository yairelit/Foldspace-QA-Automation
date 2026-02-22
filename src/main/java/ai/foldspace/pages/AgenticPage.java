package ai.foldspace.pages;

import ai.foldspace.utils.Config;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class AgenticPage {
    private final Page page;

    public AgenticPage(Page page) {
        this.page = page;
    }

    // --- Step 1: Login Actions ---
    public void navigateToLogin() {
        System.out.println("Navigating to login page...");
        page.navigate("https://app.foldspace.ai/login");
    }

    public void login() {
        page.getByPlaceholder("Email Address").fill(Config.EMAIL);
        page.getByPlaceholder("Password").fill(Config.PASSWORD);
        
        // Click 'Sign in'
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
    }

    // --- Step 2: Agent Interaction Actions ---
    public void clickNewAgent() {
        page.getByLabel("Yair Agent").click();
    }

    public int getCurrentTextLength() {
        // Capture amount of text on screen using JS execution
        return (int) page.evaluate("document.body.innerText.length");
    }

    public void clickStarterPrompt() { 
        page.getByText("Hello! Share with me an important insight").click();
    }

    // --- Step 3: Wait Logic ---
    public void waitForThinkingIndicator() {
        try {
            // Wait for "thinking..." to appear
            page.waitForSelector("text=thinking...", new Page.WaitForSelectorOptions().setTimeout(5000));
            // Wait for "thinking..." to disappear
            page.waitForSelector("text=thinking...", new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.DETACHED));
        } catch (PlaywrightException e) {
            System.out.println("Note: 'Thinking' state was skipped or too fast to catch.");
        }
    }

    // --- Step 4: Verification Logic ---
    public void waitForTextToIncrease(int initialLength) {
        // Wait until text length is strictly greater than initial
        page.waitForFunction("initialLength => document.body.innerText.length > initialLength", initialLength);
    }
}
