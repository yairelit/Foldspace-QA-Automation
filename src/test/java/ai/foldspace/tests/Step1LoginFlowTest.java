package ai.foldspace.tests;

import com.microsoft.playwright.*;
import ai.foldspace.pages.LoginPage;
import org.junit.jupiter.api.*;
import java.util.regex.Pattern;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Step1LoginFlowTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    LoginPage loginPage;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        // Launch options exactly as in your original code
         browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setup() {
        context = browser.newContext();
        page = context.newPage();
        loginPage = new LoginPage(page);
    }

    @Test
    void testLoginFlow() {
        // --- Actions (Delegated to Page Object) ---
        loginPage.navigate();
        loginPage.login("yelitzur@g.jct.ac.il", "199Yse5!");

        // --- Assertions  ---
        
        // Verify that the user is redirected to the app dashboard by checking the URL
        assertThat(page).hasURL(Pattern.compile(".*foldspace.ai/agent.*"));
        
        // Validates user page entry by checking for the presence of a prominent UI element
        Locator agentStudioBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Agent Studio"));
        assertThat(agentStudioBtn).isVisible();
        
        System.out.println("Test Passed!");
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
