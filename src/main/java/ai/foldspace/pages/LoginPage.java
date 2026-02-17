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

    public void login() {
        page.getByPlaceholder("Email Address").fill("input[name='email']", Config.EMAIL);
        page.getByPlaceholder("Password").fill("input[name='password']", Config.PASSWORD);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
    }
}
