package ai.foldspace.tests;

import ai.foldspace.pages.LoginPage;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Page;

import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Step1LoginFlowTest extends BaseTest {

    @Test
    void testLoginFlow() {
        LoginPage loginPage = new LoginPage(page);

        loginPage.navigate();
        loginPage.login("yelitzur@g.jct.ac.il", "199Yse5!");

        // --- Assertions  ---
        // Verify that the user is redirected to the app dashboard by checking the URL
        assertThat(page).hasURL(Pattern.compile(".*foldspace.ai/agent.*"));
        
        // Validates user page entry by checking for the presence of a prominent UI element
        Locator agentStudioBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Agent Studio"));
        assertThat(agentStudioBtn).isVisible();

        System.out.println("Test pass!");
    }
}
