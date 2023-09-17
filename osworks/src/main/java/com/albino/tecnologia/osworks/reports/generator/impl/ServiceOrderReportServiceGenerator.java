package com.albino.tecnologia.osworks.reports.generator.impl;

import com.albino.tecnologia.osworks.models.ServiceOrder;
import com.albino.tecnologia.osworks.reports.generator.PDFGenerator;
import com.albino.tecnologia.osworks.reports.generator.Footer;
import com.albino.tecnologia.osworks.repositories.ServiceOrderRepository;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
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
public class ServiceOrderReportServiceGenerator implements PDFGenerator {

    private final ServiceOrderRepository repository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void generateReport(Long id, HttpServletResponse response) {

        try {

            Document document = new Document(PageSize.A4);
            ServiceOrder serviceOrder = repository.findById(id).get();

            log.info("Creating a new Service Order report with Id: '{}'", id);

            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            writer.setPageEvent(new Footer());
            document.open();

            generateHeader(document, serviceOrder);
            generateBody(document, serviceOrder);

            document.close();

        } catch (IOException e) {
            log.error("Error trying to Process the ServiceOrder report");
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void export(HttpServletResponse response, Long id) {
        generateReport(id, response);
    }

    private void generateHeader(Document document, ServiceOrder serviceOrder) {

        Paragraph title = new Paragraph();
        title.setAlignment(Element.ALIGN_CENTER);

        try {
            Image logo = Image.getInstance("icon/logo.png");
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scaleToFit(100, 100);
            title.add(logo);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        Paragraph header = new Paragraph("RELATÓRIO DA ORDEM DE SERVIÇO " + serviceOrder.getOsCode(),
                new Font(Font.TIMES_ROMAN, 18, Font.BOLD));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        document.add(new Paragraph(" "));

        var separator = new LineSeparator();
        separator.setLineColor(Color.DARK_GRAY);
        document.add(separator);

        document.add(new Paragraph(" "));
    }

    private void generateBody(Document document, ServiceOrder serviceOrder) {

        Font boldFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 12);

        PdfPTable tableOS = new PdfPTable(2);
        tableOS.setWidthPercentage(100);
        tableOS.setSpacingBefore(10);
        tableOS.setSpacingAfter(10);

        // Add Title of Table

        PdfPCell titleOSCell = new PdfPCell(new Phrase("Informações Da OS:", new Font
                (Font.HELVETICA, 16, Font.BOLD)));
        titleOSCell.setColspan(2);
        titleOSCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleOSCell.setBackgroundColor(new Color(191, 191, 191));
        tableOS.addCell(titleOSCell);



        PdfPCell codeCell = new PdfPCell(new Phrase("Código da OS:", boldFont));
        PdfPCell codeValueCell = new PdfPCell(new Phrase(serviceOrder.getOsCode(), normalFont));


        PdfPCell descriptionCell = new PdfPCell(new Phrase("Descrição:", boldFont));
        PdfPCell descriptionValueCell = new PdfPCell(new Phrase(serviceOrder.getDescription(), normalFont));


        PdfPCell cellQtdHoras = new PdfPCell(new Phrase("Quantidade de horas:", boldFont));
        PdfPCell cellValorQtdHoras = new PdfPCell(new Phrase(serviceOrder.getHoursNumber(), normalFont));


        PdfPCell cellDataAbertura = new PdfPCell(new Phrase("Data de abertura:", boldFont));
        PdfPCell cellValorDataAbertura = new PdfPCell(new Phrase(serviceOrder.getOpenDate().format(formatter),
                normalFont));


        String status = serviceOrder.getStatus();
        PdfPCell cellStatus = new PdfPCell(new Phrase("Status:", boldFont));
        PdfPCell cellValorStatus = new PdfPCell(new Phrase(status.substring(0,1).toUpperCase() +
                status.substring(1).toLowerCase(),
                normalFont));

        tableOS.addCell(codeCell);
        tableOS.addCell(codeValueCell);
        tableOS.addCell(descriptionCell);
        tableOS.addCell(descriptionValueCell);
        tableOS.addCell(cellQtdHoras);
        tableOS.addCell(cellValorQtdHoras);
        tableOS.addCell(cellDataAbertura);
        tableOS.addCell(cellValorDataAbertura);
        tableOS.addCell(cellStatus);
        tableOS.addCell(cellValorStatus);

        document.add(tableOS);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Título da tabela
        table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        PdfPCell companyTitleCell = new PdfPCell(new Phrase("Informações Da Empresa ", new Font
                (Font.HELVETICA, 16, Font.BOLD)));
        companyTitleCell.setColspan(2);
        companyTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        companyTitleCell.setBackgroundColor(new Color(191, 191, 191));
        companyTitleCell.setBorderColor(new Color(0, 0, 0));
        companyTitleCell.setBorderWidth(1f);
        table.addCell(companyTitleCell);


        // Add spacing between cells
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);


        table.addCell(new PdfPCell(new Phrase("Razão Social Da Empresa", boldFont)));
        table.addCell(new PdfPCell(new Phrase(serviceOrder.getCompany().getCompanyName(), normalFont)));

        table.addCell(new PdfPCell(new Phrase("Endereço Da Empresa", boldFont)));
        PdfPCell addressCell = new PdfPCell();
        addressCell.addElement(new Phrase("CEP:", boldFont));
        addressCell.addElement(new Phrase(serviceOrder.getCompany().getAddress().getPostalCode(), normalFont));
        addressCell.addElement(new Phrase("Logradouro:", boldFont));
        addressCell.addElement(new Phrase(serviceOrder.getCompany().getAddress().getBackyard(), normalFont));
        addressCell.addElement(new Phrase("Número:", boldFont));
        addressCell.addElement(new Phrase(serviceOrder.getCompany().getAddress().getNumber(), normalFont));
        addressCell.addElement(new Phrase("Complemento:", boldFont));
        addressCell.addElement(new Phrase(serviceOrder.getCompany().getAddress().getComplement(), normalFont));
        addressCell.addElement(new Phrase("Bairro:", boldFont));
        addressCell.addElement(new Phrase(serviceOrder.getCompany().getAddress().getNeighborhood(), normalFont));
        addressCell.addElement(new Phrase("Cidade:", boldFont));
        addressCell.addElement(new Phrase(serviceOrder.getCompany().getAddress().getCity(), normalFont));
        addressCell.addElement(new Phrase("UF:", boldFont));
        addressCell.addElement(new Phrase(serviceOrder.getCompany().getAddress().getUf(), normalFont));
        table.addCell(addressCell);

        table.addCell(new PdfPCell(new Phrase("Responsável", boldFont)));

        PdfPCell responsibleCell = new PdfPCell();

        responsibleCell.addElement(new Phrase("Nome:", boldFont));
        responsibleCell.addElement(new Phrase(serviceOrder.getCompany().getResponsible().getName(), normalFont));
        responsibleCell.addElement(new Phrase("Cargo:", boldFont));
        responsibleCell.addElement(new Phrase(serviceOrder.getCompany().getResponsible().getPost(), normalFont));
        responsibleCell.addElement(new Phrase("Departamento:", boldFont));
        responsibleCell.addElement(new Phrase(serviceOrder.getCompany().getResponsible().getDepartment(), normalFont));
        table.addCell(responsibleCell);

        // adds the table to the document
        document.add(table);

        document.add(new Paragraph(" "));

        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(new Color(0, 0, 0));
        cell.setBorderWidth(1f);

        float[] columnWidths = {2, 3};
        table = new PdfPTable(columnWidths);

        table.setWidthPercentage(100);

        PdfPCell contractTitleCell = new PdfPCell(new Phrase("Informação Do Contrato:", new Font
                (Font.HELVETICA, 16, Font.BOLD)));
        contractTitleCell.setColspan(2);
        contractTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        contractTitleCell.setBackgroundColor(new Color(191, 191, 191));
        table.addCell(contractTitleCell);

        cell = new PdfPCell(new Paragraph("Código do Contrato:", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(serviceOrder.getContract().getContractCode(), normalFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Data de início do Contrato:", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(serviceOrder.getContract().getBeginDate().format(formatter), normalFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Data de término do Contrato:", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(serviceOrder.getContract().getEndDate().format(formatter), normalFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        document.add(table);
    }
}
