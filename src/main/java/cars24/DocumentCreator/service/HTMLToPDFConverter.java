package cars24.DocumentCreator.service;

import com.microsoft.playwright.*;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HTMLToPDFConverter {

    public void process(String htmlContent , String format ){
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
            String defaultFormat = "A4";
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.setContent(htmlContent);
            page.pdf(new Page.PdfOptions()
                    .setPath(Paths.get("/Users/a38648/Desktop/CARS24/DocumentCreator/src/main/resources/templates/output.pdf"))
                    .setFormat((Objects.isNull(format) || format.isEmpty()) ? defaultFormat : format)
                    .setPrintBackground(true));
            browser.close();
        }
    }
}
