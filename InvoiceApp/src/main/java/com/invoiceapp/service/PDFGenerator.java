package com.invoiceapp.service;

import com.invoiceapp.model.Customer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.time.LocalDate;

public class PDFGenerator {
    
    public void generateInvoice(Customer customer, String outputPath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Header
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("INVOICE");
                contentStream.endText();

                // Company Info
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Your Company Name");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("123 Business Street");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Business City, State 12345");
                contentStream.endText();

                // Customer Info
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, 620);
                contentStream.showText("Bill To:");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(customer.getName());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText(customer.getAddress());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText(customer.getCity() + ", " + customer.getState() + " " + customer.getZipCode());
                contentStream.endText();

                // Date
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(400, 700);
                contentStream.showText("Date: " + LocalDate.now().toString());
                contentStream.endText();

                // Invoice Details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, 500);
                contentStream.showText("Invoice Details");
                contentStream.endText();

                // Save the document
                document.save(outputPath);
            }
        }
    }
}

