package com.albino.tecnologia.osworks.reports.generator.impl;

import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.models.ServiceOrder;
import com.albino.tecnologia.osworks.reports.generator.PDFGenerator;
import com.albino.tecnologia.osworks.reports.generator.Footer;
import com.albino.tecnologia.osworks.repositories.ContractRepository;
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
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Log4j2
public class ContractReportServiceGenerator implements PDFGenerator {

    private final ContractRepository contractRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public void generateReport(Long id, HttpServletResponse response) {

        try {
            Document document = new Document(PageSize.A4);
            Contract contract = contractRepository.findById(id).get();


            log.info("Creating a New Contract Report with Id: '{}' ",id);

            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            writer.setPageEvent(new Footer());
            document.open();

            generateHeader(document);
            generateBody(document, contract);

            document.close();

        } catch (Exception ex) {
            log.error("Error trying to Process the Contract report");
            log.error(ex.getLocalizedMessage());
        }

    }

    private void generateCooperationValue(Document document, Contract contract) throws DocumentException {

        Font normalBoldFont = new Font(Font.HELVETICA,10,Font.NORMAL);

        BigDecimal functionsPoints = BigDecimal.valueOf(contract.getNumberFunctionPoints());
        BigDecimal totalValue = contract.getUnitValueTotal();
        BigDecimal UnitValue = contract.getUnitValue();
        BigDecimal ConsumedValue = totalValue.subtract(functionsPoints.multiply(UnitValue));

        BigDecimal availableValue = totalValue.subtract(ConsumedValue);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);


        float[] columnWidths = {2f, 2f, 2f};
        table.setWidths(columnWidths);


        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPCell cell = new PdfPCell();


        cell.setBackgroundColor(new Color(191, 191, 191));


        cell.setPhrase(new Phrase("Valor Total do Contract", normalBoldFont));
        table.addCell(cell);


        cell.setPhrase(new Phrase("Valor Consumido", normalBoldFont));
        table.addCell(cell);


        cell.setPhrase(new Phrase("Valor Disponível", normalBoldFont));
        table.addCell(cell);


        cell = new PdfPCell(new Phrase(NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                .format(totalValue.subtract(ConsumedValue))));
        table.addCell(cell);


        cell = new PdfPCell(new Phrase(NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                .format(ConsumedValue)));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                .format(availableValue)));
        table.addCell(cell);

        document.add(table);

    }

    private Long countOSInStatus(Contract contract, String status) {

        List<ServiceOrder> serviceOrders = contract.getOs();

        return serviceOrders.stream()
                .filter(os -> os.getStatus().equals(status))
                .count();
    }


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

        document.add(new Paragraph(" "));
        title.add(new Chunk("RELATÓRIO DETALHADO DO CONTRATO", new Font(Font.HELVETICA, 24)));
        document.add(title);
        document.add(new Paragraph(" "));

        var separator = new LineSeparator();
        separator.setLineColor(Color.DARK_GRAY);
        document.add(separator);
    }


    private void generateBody(Document document, Contract contract) {

        var fontNormal = new Font(Font.HELVETICA,12,Font.BOLD);

        document.add(new Paragraph(" "));

        document.add(new Paragraph("Informações gerais do Contrato:",
                new Font(Font.HELVETICA, 14, Font.BOLD)));

        document.add(new Paragraph(" "));

        Paragraph infoContract = new Paragraph();

        infoContract.setAlignment(Element.ALIGN_JUSTIFIED);
        infoContract.add(new Chunk("Código do Contrato: ", fontNormal));
        infoContract.add(new Chunk(contract.getContractCode(),fontNormal));
        infoContract.add(new Chunk("\nValor total do Contrato: ", fontNormal));
        infoContract.add(new Chunk(NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                .format(contract.getUnitValueTotal()),fontNormal));
        infoContract.add(new Chunk("\nValor ainda disponível: ",fontNormal));
        infoContract.add(new Chunk(NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                .format(contract.getUnitValueTotal()),fontNormal));
        infoContract.add(new Chunk("\nData de início do Contrato: ", fontNormal));
        infoContract.add(new Chunk(contract.getBeginDate().format(formatter),fontNormal));
        infoContract.add(new Chunk("\nData de término do Contrato: ", fontNormal));
        infoContract.add(new Chunk(contract.getEndDate().format(formatter),fontNormal));
        infoContract.add(new Chunk("\nQuantidade de pontos de função já consumidos: ", fontNormal));
        infoContract.add(new Chunk(contract.getNumberFunctionPoints().toString(),fontNormal));
        infoContract.add(new Chunk("\nQuantidade de pontos de função ainda disponíveis: ", fontNormal));
        infoContract.add(new Chunk(contract.getNumberFunctionPointsTotal().toString(),fontNormal));
        infoContract.add(new Chunk("\nNúmero de Ordens de Serviço (ServiceOrder) já criadas: ", fontNormal));
        infoContract.add(new Chunk(String.valueOf(contract.getOs().size()),fontNormal));
        infoContract.add(new Chunk("\nNúmero de ServiceOrder em andamento: ", fontNormal));
        infoContract.add(new Chunk(String.valueOf(countOSInStatus(contract,"em andamento")),fontNormal));
        infoContract.add(new Chunk("\nNúmero de ServiceOrder já finalizadas: ", fontNormal));
        infoContract.add(new Chunk(String.valueOf(countOSInStatus(contract,"finalizada")),fontNormal));
        infoContract.add(new Chunk("\nResponsável pela gestão da company: ", fontNormal));
        infoContract.add(new Chunk(contract.getCompany().getResponsible().getName(),fontNormal));

        document.add(infoContract);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Tabela Do Valor Consumido:",
                new Font(Font.HELVETICA, 14, Font.BOLD)));

        document.add(new Paragraph(" "));

        generateCooperationValue(document, contract);
    }

}
