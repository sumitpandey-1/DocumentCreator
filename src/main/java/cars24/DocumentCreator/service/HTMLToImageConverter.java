package cars24.DocumentCreator.service;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.ScreenshotScale;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

@Service
public class HTMLToImageConverter implements HTMLConverter{

    @Override
    public byte[] process(String htmlContent, String format) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(true)
                    .setArgs(List.of(
                            "--no-sandbox",
                            "--disable-setuid-sandbox",
                            "--disable-gpu",
                            "--disable-extensions",
                            "--disable-dev-shm-usage"
                    )));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setDeviceScaleFactor(2));
            Page page = context.newPage();
            page.setViewportSize(1920, 1080);
            page.setContent(htmlContent);
            page.waitForLoadState(LoadState.NETWORKIDLE);
            String defaultImagePath = "/Users/a38648/Desktop/CARS24/DocumentCreator/src/main/resources/templates/output.png";
            byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions()
                    .setFullPage(true)
                    .setScale(ScreenshotScale.CSS));


            browser.close();
            return screenshotBytes;
        }
    }
}
