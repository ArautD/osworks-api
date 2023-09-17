package com.albino.tecnologia.osworks.reports.generator.impl;

import com.albino.tecnologia.osworks.models.Project;
import com.albino.tecnologia.osworks.reports.generator.PDFGenerator;
import com.albino.tecnologia.osworks.reports.generator.Footer;
import com.albino.tecnologia.osworks.repositories.ProjectRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
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
public class ProjectReportServiceGenerator implements PDFGenerator {

    private final ProjectRepository repository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void generateReport(Long id, HttpServletResponse response) {

        try {

            Document document = new Document(PageSize.A4);
            Project project = repository.findById(id).get();

            log.info("Creating a New Contract Report with Id: '{}'", id);

            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            writer.setPageEvent(new Footer());
            document.open();

            generateHeader(document);
            generateBody(document, project);

            document.close();

        } catch (IOException e) {
            log.error("Error trying to Process Project report");
            log.error(e.getLocalizedMessage());
        }

    }

    @Override
    public void export(HttpServletResponse response, Long id) {
        generateReport(id, response);
    }

    private void generateHeader(Document document) {

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

        Paragraph header = new Paragraph("RELATÓRIO DE PROJETO ",
                new Font(Font.TIMES_ROMAN, 18, Font.BOLD));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        document.add(new Paragraph(" "));

        var separator = new LineSeparator();
        separator.setLineColor(Color.DARK_GRAY);
        document.add(separator);

        document.add(new Paragraph(" "));
    }

    private void generateBody(Document document, Project project) {

        Font boldFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 12);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Title of Table

        PdfPCell tituloTabela = new PdfPCell(new Phrase("Informações do Projeto",
                new Font(Font.HELVETICA, 16, Font.BOLD)));
        tituloTabela.setColspan(2);
        tituloTabela.setHorizontalAlignment(Element.ALIGN_CENTER);
        tituloTabela.setBackgroundColor(new Color(191, 191, 191));
        table.addCell(tituloTabela);

        // CCreates cells for each project attribute and adds to the table

        PdfPCell celula = new PdfPCell(new Phrase("Nome do Projeto:", boldFont));
        celula.setBackgroundColor(new Color(240, 240, 240));
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(project.getName(), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Descrição:", boldFont));
        celula.setBackgroundColor(new Color(240, 240, 240));
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(project.getDescription(), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Data de Início:", boldFont));
        celula.setBackgroundColor(new Color(240, 240, 240));
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(project.getBeginDate().format(formatter), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Data de Término:", boldFont));
        celula.setBackgroundColor(new Color(240, 240, 240));
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(project.getEndDate().format(formatter), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Status:", boldFont));
        celula.setBackgroundColor(new Color(240, 240, 240));
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(capitalizeStatus(project.getStatus()), normalFont));
        table.addCell(celula);

        celula = new PdfPCell(new Phrase("Gerente de Projeto:", boldFont));
        celula.setBackgroundColor(new Color(240, 240, 240));
        table.addCell(celula);
        celula = new PdfPCell(new Phrase(project.getProjectManager().getName(), normalFont));
        table.addCell(celula);

        document.add(table);

        document.add(new Paragraph(" "));

        PdfPTable tableOS = new PdfPTable(2);
        tableOS.setWidthPercentage(100);
        tableOS.setSpacingBefore(10);
        tableOS.setSpacingAfter(10);

        // Add table title

        PdfPCell cellTituloOS = new PdfPCell(new Phrase("OS Do Projeto", new Font
                (Font.HELVETICA, 16, Font.BOLD)));
        cellTituloOS.setColspan(2);
        cellTituloOS.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTituloOS.setBackgroundColor(new Color(191, 191, 191));
        tableOS.addCell(cellTituloOS);

        // Add Service Order Information to the Table

        PdfPCell cellCodigo = new PdfPCell(new Phrase("Código da OS:", boldFont));
        cellCodigo.setBackgroundColor(new Color(240, 240, 240));
        PdfPCell cellValorCodigo = new PdfPCell(new Phrase(
                project.getServiceOrder().getOsCode(), normalFont));


        PdfPCell cellDescricao = new PdfPCell(new Phrase("Descrição:", boldFont));
        cellDescricao.setBackgroundColor(new Color(240, 240, 240));
        PdfPCell cellValorDescricao = new PdfPCell(new Phrase(
                project.getServiceOrder().getDescription(), normalFont));


        PdfPCell cellQtdHoras = new PdfPCell(new Phrase("Quantidade de horas:", boldFont));
        cellQtdHoras.setBackgroundColor(new Color(240, 240, 240));
        PdfPCell cellValorQtdHoras = new PdfPCell(new Phrase(
                project.getServiceOrder().getHoursNumber(), normalFont));


        PdfPCell openDateCell = new PdfPCell(new Phrase("Data de abertura:", boldFont));
        openDateCell.setBackgroundColor(new Color(240, 240, 240));
        PdfPCell openDateValueCell = new PdfPCell(new Phrase(
                project.getServiceOrder().getOpenDate().format(formatter), normalFont));


        String status = project.getServiceOrder().getStatus();
        PdfPCell cellStatus = new PdfPCell(new Phrase("Status:", boldFont));
        cellStatus.setBackgroundColor(new Color(240, 240, 240));
        PdfPCell cellValorStatus = new PdfPCell(new Phrase(status.substring(0, 1).toUpperCase() +
                status.substring(1).toLowerCase(),
                normalFont));

        tableOS.addCell(cellCodigo);
        tableOS.addCell(cellValorCodigo);
        tableOS.addCell(cellDescricao);
        tableOS.addCell(cellValorDescricao);
        tableOS.addCell(cellQtdHoras);
        tableOS.addCell(cellValorQtdHoras);
        tableOS.addCell(openDateCell);
        tableOS.addCell(openDateValueCell);
        tableOS.addCell(cellStatus);
        tableOS.addCell(cellValorStatus);

        document.add(tableOS);
        document.add(new Paragraph(" "));

    }

    private String capitalizeStatus(String status) {

        String[] words = status.split(" ");
        words[0] = words[0].substring(0, 1).toUpperCase() + words[0].substring(1);
        words[1] = words[1].substring(0, 1).toUpperCase() + words[1].substring(1);

        return String.join(" ", words);
    }
}
