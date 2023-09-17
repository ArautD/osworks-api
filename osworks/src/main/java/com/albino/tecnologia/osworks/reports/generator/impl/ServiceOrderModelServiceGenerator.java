package com.albino.tecnologia.osworks.reports.generator.impl;

import com.albino.tecnologia.osworks.models.ServiceOrder;
import com.albino.tecnologia.osworks.reports.generator.PDFGenerator;
import com.albino.tecnologia.osworks.reports.generator.Footer;
import com.albino.tecnologia.osworks.repositories.ServiceOrderRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Log4j2
public class ServiceOrderModelServiceGenerator implements PDFGenerator {

    private final ServiceOrderRepository repository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void generateReport(Long id, HttpServletResponse response) {

        Document document = new Document(PageSize.A4);
        ServiceOrder serviceOrder = repository.findById(id).get();

        log.info("Generating the Service Order '{}'", serviceOrder.getOsCode());

        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, response.getOutputStream());
            writer.setPageEvent(new Footer());
            document.open();

            generateHeader(document, serviceOrder);
            generateBody(document, serviceOrder);

            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void export(HttpServletResponse response, Long id) {
        generateReport(id, response);
    }

    private void generateHeader(Document document, ServiceOrder serviceOrder) {


        try {
            Image logo = Image.getInstance("icon/logo.png");
            logo.scaleToFit(150, 150);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (IOException e) {
            log.error("Error in starting the logo");
            log.error(e.getLocalizedMessage());
        }


        Paragraph title = new Paragraph("Ordem de Serviço", new Font(Font.HELVETICA, 18, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph osInfo = new Paragraph();
        osInfo.add(Chunk.NEWLINE);
        osInfo.setAlignment(Element.ALIGN_CENTER);
        osInfo.setSpacingBefore(20);
        document.add(osInfo);


        LineSeparator separator = new LineSeparator();
        separator.setLineColor(Color.black);
        separator.setLineWidth(0.5f);
        document.add(separator);
    }

    private void generateBody(Document document, ServiceOrder serviceOrder) {

        Font boldFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 12);

        Color whiteColor = new Color(225, 225, 225);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(0);

        table.getDefaultCell().setBorder(Rectangle.BOTTOM | Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);

        table.getDefaultCell().setBackgroundColor(Color.darkGray);


        table.getDefaultCell().setPadding(10);

        PdfPCell celula = new PdfPCell(new Phrase("Número da ServiceOrder:", boldFont));
        celula.setBackgroundColor(whiteColor);
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(serviceOrder.getOsCode(), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Company:", boldFont));
        celula.setBackgroundColor(whiteColor);
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(serviceOrder.getCompany().getCompanyName(), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Descrição:", boldFont));
        celula.setBackgroundColor(whiteColor);
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(serviceOrder.getDescription(), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Data de Abertura:", boldFont));
        celula.setBackgroundColor(whiteColor);
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(serviceOrder.getOpenDate().format(formatter), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Qtd. De Horas:", boldFont));
        celula.setBackgroundColor(whiteColor);
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(serviceOrder.getHoursNumber(), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Qtd. De Pontos De Função (PF):", boldFont));
        celula.setBackgroundColor(whiteColor);
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(serviceOrder.getNumberFunctionPoints().toString(), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Status:", boldFont));
        celula.setBackgroundColor(whiteColor);
        table.addCell(celula);

        String status = serviceOrder.getStatus();
        celula = new PdfPCell(new Phrase(status.substring(0, 1).toUpperCase() +
                status.substring(1).toLowerCase(), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Responsável:", boldFont));
        celula.setBackgroundColor(whiteColor);
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(serviceOrder.getResponsible().getName(), normalFont));
        table.addCell(celula);

        document.add(table);

    }

}
