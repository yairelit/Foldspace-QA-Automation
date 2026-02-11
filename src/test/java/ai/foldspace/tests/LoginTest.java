package ai.foldspace.tests;

import com.microsoft.playwright.*;
import ai.foldspace.pages.LoginPage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    static Playwright playwright;
    static Browser browser;
    Page page;
    LoginPage loginPage;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setup() {
        page = browser.newPage();
        loginPage = new LoginPage(page);
    }

    @Test
    @DisplayName("Verify successful login and redirection")
    void testSuccessfulLogin() {
        loginPage.navigate(); 
        loginPage.login("yelitzur@g.jct.ac.il", "199Yse5!"); 

        try {
            page.waitForURL(url -> !url.contains("login"), new Page.WaitForURLOptions().setTimeout(10000));
        } catch (Exception e) {
            // If 10 seconds have passed and we are still logged in - it will fail here
        }
        
        // Assertions 
        // 1. Checking the URL change (makes sure we are no longer on the login page)
        assertTrue(page.url().contains("dashboard") || !page.url().contains("login"), 
                   "User was not redirected after login");

        // 2. Check that there is no visible error message 
        assertFalse(page.isVisible("text=Invalid credentials"), "Error message is displayed");
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
