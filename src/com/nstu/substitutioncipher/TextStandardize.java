package com.nstu.substitutioncipher;

import java.io.*;

public class TextStandardize {

       public static String convertToStandardText(String text) {
        StringBuilder standardText = new StringBuilder();
        boolean hyphenFlag = false; //проверка на повторный дефис

        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c >= 'А' && c <= 'Я' || c >= 'а' && c <= 'я' || c == 'ё' || c == 'Ё') {
                if (c == 'ё' || c == 'Ё') {
                    standardText.append('Е');
                } else if (c == 'ъ' || c == 'Ъ') {
                    standardText.append('Ь');
                } else {
                    standardText.append(c);
                }
                hyphenFlag = false;
            } else {
                if (standardText.length() > 0) {
                    if (c != '-' && c != (char) 173) { //как выяснялось есть еще один символ '-' с кодом 173
                        if (standardText.charAt(standardText.length() - 1) >= 'А' && standardText.charAt(standardText.length() - 1) <= 'Я' || standardText.charAt(standardText.length() - 1) >= 'а' && standardText.charAt(standardText.length() - 1) <= 'я') {
                            standardText.append(' ');
                            hyphenFlag = false;
                        }
                    } else { //реакция на дефис, если до этого дефис встречался добавляем пробел
                        if (hyphenFlag && (standardText.charAt(standardText.length() - 1) >= 'А' && standardText.charAt(standardText.length() - 1) <= 'Я' || standardText.charAt(standardText.length() - 1) >= 'а' && standardText.charAt(standardText.length() - 1) <= 'я')) {
                            standardText.append(' ');
                            hyphenFlag = false;
                        } else {
                            hyphenFlag = true;
                        }
                    }
                }
            }
        }
        return standardText.toString().toUpperCase();
    }

    public static void convertTextFileToStandardTextFile(File inFile, File outFile, String encoding) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), encoding));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        String text;
        while((text = reader.readLine()) != null) {
            text = convertToStandardText(text);
            if(!text.equals("")) {
                if(text.length() != 0 && text.charAt(text.length() - 1) != ' ') {
                    writer.write(text + ' ');
                }
                else {
                    writer.write(text);
                }
            }
        }
        reader.close();
        writer.flush();
        writer.close();
    }
}