package cars24.papermill.service;

import cars24.papermill.enums.DocFormat;
import cars24.papermill.utility.Constants;
import com.microsoft.playwright.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HTMLToPDFConverter implements HTMLConverter {

    @Override
    public byte[] process(String htmlContent , DocFormat format ){
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
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.setContent(htmlContent);
            byte[] pdfBytes = page.pdf(new Page.PdfOptions()
                    .setFormat(format.getValue())
                    .setPrintBackground(true));
            browser.close();

            return pdfBytes;
        }
    }

    @Override
    public boolean canConvert(String requestType) {
        return Constants.DOCUMENT_TYPE.PDF.contains(requestType.toLowerCase());
    }
}
