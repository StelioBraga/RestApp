package com.example.rest.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.rest.models.Pais;
import com.opencsv.CSVWriter;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportUtils {

    public static void exportToXLS(Context ctx, List<Pais> data) {
        String filename = "data.xls";
        File file = new File(ctx.getExternalFilesDir(null), filename);

        try {
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet1 = workbook.createSheet("Dados", 0);

            String[] headers = Pais.getAllKeysInPT();
            for (int i = 0; i < headers.length; i++) {
                String headerName = headers[i];
                Label headerLabel = new Label(i, 0, headerName);
                sheet1.addCell(headerLabel);
            }

            for (int i = 0; i < data.size(); i++) {
                int row = i+1;
                Pais pais = data.get(i);

                Label nome = new Label(0, row, pais.getName());
                Label capital = new Label(1, row, pais.getCapital());
                Label regiao = new Label(2, row, pais.getRegion());
                Number populacao = new Number(3, row, pais.getPopulation());
                Number area = new Number(4, row, pais.getArea());
                Label fusoshorario = new Label(5, row, pais.getTimezones().toString());
                Label nomenativo = new Label(6, row, pais.getNativeName());
                Label flag_url = new Label(7, row, pais.getFlag());
                Label subregiao = new Label(8, row, pais.getSub_region());
                // add to sheet
                ExportUtils.addMultipleCellsToSheet(sheet1, nome, capital, regiao, populacao, area, fusoshorario, nomenativo, flag_url, subregiao);
            }

            workbook.write();
            workbook.close();
            Toast.makeText(ctx, "Paìs exportado com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(ctx, "Ocorreu um erro.\nNenhum ficheiro foi exportado.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void exportToXML(Context ctx, List<Pais> data) {
        String filename = "data.xml";
        File file = new File(ctx.getExternalFilesDir(null), filename);


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        try {
            documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element paisesElement = document.createElement("paises");
            document.appendChild(paisesElement);

            for (int i = 0; i < data.size(); i++) {
                Pais pais = data.get(i);

                Element paisElement = document.createElement("pais");

                String[] attributeNames = Pais.getAllKeysInPT();
                paisElement.setAttribute(removeInvalidCharacters(attributeNames[0]), pais.getName());
                paisElement.setAttribute(removeInvalidCharacters(attributeNames[1]), pais.getCapital());
                paisElement.setAttribute(removeInvalidCharacters(attributeNames[2]), pais.getRegion());
                paisElement.setAttribute(removeInvalidCharacters(attributeNames[3]), String.valueOf(pais.getPopulation()));
                paisElement.setAttribute(removeInvalidCharacters(attributeNames[4]), String.valueOf(pais.getArea()));

                JSONArray timezonesRaw = pais.getTimezones();
                Element timezonesElement = document.createElement("fusoshorarios");

                for (int j = 0; j < timezonesRaw.length(); j++) {
                    String timezoneAtIndex = timezonesRaw.getString(j);
                    Element timezoneAtIndexElement = document.createElement("fusohorario");
                    timezoneAtIndexElement.setNodeValue(timezoneAtIndex);
                    timezonesElement.appendChild(timezoneAtIndexElement);
                }
                paisElement.appendChild(timezonesElement);

                paisElement.setAttribute(removeInvalidCharacters(attributeNames[6]), pais.getNativeName());
                paisElement.setAttribute(removeInvalidCharacters(attributeNames[7]), pais.getFlag());
                paisElement.setAttribute(removeInvalidCharacters(attributeNames[8]), pais.getSub_region());
                paisesElement.appendChild(paisElement);

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource,streamResult);

            Toast.makeText(ctx, "Ficheiro .xml criado com sucesso.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            // error happened.
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }
    public static String removeInvalidCharacters(String value){
        String transformador1 = value.replace(" ", "");
        return transformador1.replace("-", "");
    }

    private static void addMultipleCellsToSheet(WritableSheet sheet, WritableCell... cells) throws WriteException {
        int i = 0;
        for (WritableCell cell: cells) {
            sheet.setColumnView(i, 12); // column width.
            sheet.addCell(cell);
            i++;
        }
    }

    public static  void exportToCSV(Context ctx, List<Pais> data)  {
        String namefile ="data.csv";

        try {
            File file = new File(ctx.getExternalFilesDir(null),namefile);
            FileWriter output = new FileWriter(file);
            CSVWriter write = new CSVWriter(output);

            String[] attributeNames = Pais.getAllKeysInPT();
            write.writeNext(attributeNames);

            for (int i = 0; i <data.size() ; i++) {
              Pais pais = data.get(i);
                String[] array = new String[9];
                array[0] = pais.getName();
                array[1] = pais.getCapital();
                array[2] = pais.getRegion();
                array[3] = String.valueOf(pais.getPopulation());
                array[4] = String.valueOf(pais.getArea());
                array[5] = String.valueOf(pais.getTimezones());
                array[6] = pais.getNativeName();
                array[7] = pais.getFlag();
                array[8] = pais.getSub_region();


                write.writeNext(array);
            }
            write.close();
            Toast.makeText(ctx, "exportou com sucesso CSV.", Toast.LENGTH_SHORT).show();
            }catch (IOException e){
            Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
