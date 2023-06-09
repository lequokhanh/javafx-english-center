package com.models;

import java.awt.Color;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;


public class Text {
    PDDocument document;
    PDPageContentStream contentStream;

    public Text(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    public void addSingleLineText(String text, int xPosition, int yPosition, PDFont font, float fontSize, Color color) {
        try {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(text);
            contentStream.endText();
            contentStream.moveTo(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addMultiLineText(String[] textArray, float leading, int xPosition, int yPosition, PDFont font, float fontSize, Color color) {
        try {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.setLeading(leading);
            contentStream.newLineAtOffset(xPosition, yPosition);
            for (String text : textArray) {
                contentStream.showText(text);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.moveTo(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get & set
    float getTextWidth(String text, PDFont font, float fontSize) {
        try {
            return font.getStringWidth(text) / 1000 * fontSize;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
