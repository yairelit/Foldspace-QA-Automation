package ai.foldspace.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate("https://app.foldspace.ai/login");
    }

    public void login(String email, String password) {
        page.getByPlaceholder("Email Address").fill(email);
        page.getByPlaceholder("Password").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
    }
}
