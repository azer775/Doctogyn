package org.example.medicalreport.services;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    public byte[] generateMedicalRecordPdf(String htmlContent) throws Exception {
        // CSS from the previous artifact
        String css = """
            body {
                font-family: Arial, Helvetica, sans-serif;
                margin: 40px;
                font-size: 12pt;
                color: #333333;
                line-height: 1.4;
            }
            h2 {
                font-size: 18pt;
                color: #1a3c6d;
                text-align: center;
                margin-bottom: 20px;
                border-bottom: 2px solid #1a3c6d;
                padding-bottom: 5px;
            }
            h3 {
                font-size: 14pt;
                color: #2e5a8a;
                margin-top: 20px;
                margin-bottom: 10px;
            }
            h4 {
                font-size: 12pt;
                color: #4a4a4a;
                margin-top: 15px;
                margin-bottom: 8px;
                font-weight: bold;
            }
            p {
                margin: 5px 0;
                font-size: 11pt;
            }
            strong {
                font-weight: bold;
                color: #333333;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 10px 0;
                font-size: 11pt;
            }
            th, td {
                border: 1px solid #999999;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #e6f0fa;
                font-weight: bold;
                color: #1a3c6d;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            ul {
                list-style-type: disc;
                margin: 10px 0 10px 20px;
                font-size: 11pt;
            }
            .custom-table {
                width: auto;
                min-width: 50%;
                border-collapse: collapse;
                margin: 10px 0;
            }
            .custom-table th, .custom-table td {
                border: 1px solid #999999;
                padding: 8px;
            }
            .custom-table th {
                background-color: #e6f0fa;
            }
            div {
                margin-bottom: 15px;
            }
            p p {
                margin: 0;
                display: inline;
            }
            h3, h2 {
                page-break-after: avoid;
            }
            table {
                page-break-inside: auto;
            }
            tr {
                page-break-inside: avoid;
                page-break-after: auto;
            }
        """;

        // Wrap the provided HTML content in the specified structure
        String fullHtml = """
            <html>
                <head>
                    <title>Medical Record</title>
                    <style>%s</style>
                </head>
                <body>%s</body>
            </html>
            """.formatted(css, htmlContent);

        // Parse HTML with Jsoup and convert to XHTML
        Document doc = Jsoup.parse(fullHtml, "UTF-8");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        String xhtml = doc.html();

        // Render to PDF
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(xhtml, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        }
    }
}

