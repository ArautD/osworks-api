package com.albino.tecnologia.osworks.reports.generator;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class Footer extends PdfPageEventHelper {

    private final Font footerFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {

        PdfContentByte cb = writer.getDirectContent();


        int pageNumbers = writer.getPageNumber();

        Phrase footer = new Phrase(String.format("Página %d até %d", 1, pageNumbers), footerFont);

        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer,
                document.right(), document.bottomMargin() - 10, 0);

    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        PdfContentByte canvas = writer.getDirectContent();

        canvas.moveTo(document.left(), document.bottomMargin());
        canvas.lineTo(document.right(), document.bottomMargin());
        canvas.stroke();

        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(" " , footerFont),
                document.right(), document.bottomMargin() - 10, 0);


        String copyright = "Albino Tecnologia © 2023, Todos serviceOrder Direitos Reservados";

        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(copyright, footerFont),
                (document.left() + document.right()) / 2, document.bottomMargin() - 10, 0);

    }
}
