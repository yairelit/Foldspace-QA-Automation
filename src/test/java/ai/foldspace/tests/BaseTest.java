package ai.foldspace.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.nio.file.Paths;

public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(1000));
    }

    @BeforeEach
    void setup() {
        context = browser.newContext();
        // Trace viewer
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (context != null){
            // Stopping and saving the trace
            // Here we have defined that it will save a ZIP file with the test name in the traces folder.
            context.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get("traces/" + testInfo.getDisplayName() + ".zip")));
            context.close();
        }
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
