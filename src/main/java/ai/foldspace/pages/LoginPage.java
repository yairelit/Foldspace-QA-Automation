package ai.foldspace.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    // Locators
    private final String emailInput = "input[type='email']";
    private final String passwordInput = "input[type='password']";
    private final String loginButton = "button[type='submit']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate("https://app.foldspace.ai/login"); // [cite: 18, 23]
    }

    public void login(String email, String password) {
        page.fill(emailInput, email);
        page.fill(passwordInput, password);
        page.click(loginButton); // [cite: 25]
    }
}
