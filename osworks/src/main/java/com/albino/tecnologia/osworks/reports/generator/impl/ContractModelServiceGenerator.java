package com.albino.tecnologia.osworks.reports.generator.impl;

import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.reports.generator.PDFGenerator;
import com.albino.tecnologia.osworks.reports.generator.Footer;
import com.albino.tecnologia.osworks.repositories.ContractRepository;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class ContractModelServiceGenerator implements PDFGenerator {

    private final ContractRepository repository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void generateReport(Long id, HttpServletResponse response) {

        Document document = new Document(PageSize.A4);
        Contract contract = repository.findById(id).get();

        log.info("Criando um Novo Modelo do Contract: '{}' ", contract.getContractCode());

        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, response.getOutputStream());
        } catch (IOException e) {
            log.error("Error Generating a Contract Template");
            log.error(e.getLocalizedMessage());
        }

        Objects.requireNonNull(writer).setPageEvent(new Footer());
        document.open();

        generateHeader(document, contract);
        generateBody(document, contract);

        document.close();
    }

    @Override
    public void export(HttpServletResponse response, Long id) {
        generateReport(id,response);
    }

    private void generateHeader(Document document, Contract contract){

        Font boldFont = new Font(Font.HELVETICA, 14, Font.BOLD);

        Paragraph title = new Paragraph("CONTRATO DE PRESTAÇÃO DE SERVIÇOS", boldFont);
        title.setAlignment(Element.ALIGN_LEFT);
        document.add(title);


        try {
            Image logo = Image.getInstance("icon/logo.png");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_RIGHT);
            document.add(logo);
        } catch (IOException e) {
            log.error("Error in Contract Logo Processing");
            log.error(e.getLocalizedMessage());
        }


        var separator = new LineSeparator();
        separator.setLineColor(Color.BLACK);
        document.add(separator);

    }

    private void generateBody(Document document, Contract contract){

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        Font titleFont = new Font(Font.TIMES_ROMAN,16);
        Font subTitleFont = new Font(Font.HELVETICA,14);
        Font normalFont = new Font(Font.HELVETICA, 12);

        Paragraph title = new Paragraph("CLÁUSULAS DO CONTRATO", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        document.add(new Paragraph(" "));

        // Cláusula 1: Contratante
        String infoContrante = String.format("O contratante, %s, inscrito no CNPJ " +
                "%s, com sede na cidade de %s, no endereço %s,%s - %s, %s-%s,%s" +
                ", Tendo o Responsável pelo pagamento dos valores estipulados neste contract, %s, com CPF: %s e RG: %s," +
                        "domiciliado na %s,%s - %s, %s-%s,%s"
        , contract.getCompany().getCompanyName(),
                contract.getCompany().getCnpj(),
                contract.getCompany().getAddress().getCity(),
                contract.getCompany().getAddress().getBackyard(),
                contract.getCompany().getAddress().getNumber(),
                contract.getCompany().getAddress().getNeighborhood(),
                contract.getCompany().getAddress().getCity(),
                contract.getCompany().getAddress().getUf(),
                contract.getCompany().getAddress().getPostalCode(),
                contract.getCompany().getResponsible().getName(),
                contract.getCompany().getResponsible().getCpf(),
                contract.getCompany().getResponsible().getRg(),
                contract.getCompany().getResponsible().getAddress().getBackyard(),
                contract.getCompany().getResponsible().getAddress().getNumber(),
                contract.getCompany().getResponsible().getAddress().getNeighborhood(),
                contract.getCompany().getResponsible().getAddress().getCity(),
                contract.getCompany().getResponsible().getAddress().getUf(),
                contract.getCompany().getResponsible().getAddress().getPostalCode()
        );

        Paragraph clasula1titulo = new Paragraph("CLÁUSULA 1 - CONTRATANTE",subTitleFont);
        clasula1titulo.setSpacingAfter(5);
        document.add(clasula1titulo);

        Paragraph clasula1Texto = new Paragraph(infoContrante, normalFont);
        clasula1Texto.setIndentationLeft(30);
        clasula1Texto.setSpacingAfter(15);
        document.add(clasula1Texto);



        // Cláusula 2: Contratado
        Paragraph clasula2titulo = new Paragraph("CLÁUSULA 2 - CONTRATADO", subTitleFont);
        clasula2titulo.setSpacingAfter(5);
        document.add(clasula2titulo);

        Paragraph clasula2texto = new Paragraph("O contratado, Albino Tecnologia S.A, " +
                "inscrito no CNPJ 11.621.967/0001-07, com sede na cidade de Belém," +
                " no endereço R. Manoel Barata, 900 - Cruzeiro (Icoraci), Belém - PA, 66810100, " +
                "compromete-se a executar serviceOrder serviços descritos neste contract " +
                "com eficiência e qualidade, dentro do prazo estipulado. ", normalFont);
        clasula2texto.setIndentationLeft(30);
        clasula2texto.setSpacingAfter(15);
        document.add(clasula2texto);


        // Cláusula 3: Objeto
        Paragraph clausula1Titulo = new Paragraph("CLÁUSULA 3 - OBJETO", subTitleFont);
        clausula1Titulo.setSpacingAfter(5);
        document.add(clausula1Titulo);

        Paragraph clausula1Texto = new Paragraph("O presente contract tem como objeto a prestação de " +
                "serviços descritos abaixo:", normalFont);
        clausula1Texto.setIndentationLeft(30);
        clausula1Texto.setSpacingAfter(15);
        document.add(clausula1Texto);

        List services = new List(List.UNORDERED);
        services.add(new ListItem(contract.getDescriptions().get(0), normalFont));
        services.setIndentationLeft(60);
        document.add(services);

        document.add(new Paragraph(" "));

        // Cláusula 4: Prazo
        Paragraph clausula2Titulo = new Paragraph("CLÁUSULA 4 - PRAZO", subTitleFont);
        clausula2Titulo.setSpacingAfter(5);
        document.add(clausula2Titulo);

        long difDeDias = ChronoUnit.DAYS.between(contract.getBeginDate(), contract.getEndDate());

        String clasula2 = String.format("O prazo para a realização dos serviços será de %d dias corridos, " +
                "contados a partir da data de %s",difDeDias, contract.getBeginDate().format(formatter));

        Paragraph clausula2Texto = new Paragraph(clasula2, normalFont);
        clausula2Texto.setIndentationLeft(30);
        clausula2Texto.setSpacingAfter(15);
        document.add(clausula2Texto);

        document.add(new Paragraph(" "));

        // Cláusula 5: Preço

        Paragraph clausula3Titulo = new Paragraph("CLÁUSULA 5 - PREÇO", subTitleFont);
        clausula3Titulo.setSpacingAfter(5);
        document.add(clausula3Titulo);

        String clausula3 = String.format("O valor total dos serviços descritos na cláusula 3 " +
                "será de %s",
                currencyInstance.format(contract.getNumberFunctionPointsTotal()));

        Paragraph clausula3Texto = new Paragraph(clausula3, normalFont);
        clausula3Texto.setIndentationLeft(30);
        clausula3Texto.setSpacingAfter(15);
        document.add(clausula3Texto);

        document.add(new Paragraph(" "));

        // Cláusula 6: Foro
        Paragraph clausula5Titulo = new Paragraph("CLÁUSULA 6 - FORO", subTitleFont);
        clausula5Titulo.setSpacingAfter(5);
        document.add(clausula5Titulo);

        Paragraph clausula5Texto = new Paragraph("Fica eleito o foro da comarca de Belém/PA, para dirimir " +
                "quaisquer controvérsias oriundas do presente contract, com renúncia expressa a qualquer outro, por mais " +
                "privilegiado que seja.");

        clausula5Texto.setIndentationLeft(30);
        clausula5Texto.setSpacingAfter(15);
        document.add(clausula5Texto);

    }

}
