package com.nstu.substitutioncipher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R_A_D on 25.03.2016.
 */

public class TextStandardize {

   /* public static List<Character> editTextWithSpace(File file) {
        List<Character> arrayChars = new ArrayList<>();
        //если на вход подан txt файл
        if (getFileExtension(file.getName()).equals("txt")) {
            BufferedReader reader = null;
            try {
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                int c;
                int i = 0;
                boolean hyphenFlag = false; //проверка на повторный дефис
                while ((c = reader.read()) != -1) {
                    if (c >= 'А' && c <= 'Я' || c >= 'а' && c <= 'я' || c == 'ё' || c == 'Ё') {
                        if (c == 'ё' || c == 'Ё') {
                            arrayChars.add('Е');
                            i++;
                        } else if (c == 'ъ' || c == 'Ъ') {
                            arrayChars.add('Ь');
                            i++;
                        } else {
                            arrayChars.add((char) c);
                            i++;
                        }
                        hyphenFlag = false;
                    } else {
                        if (i > 0) {
                            if (c != '-' && c != (char) 173) { //как выяснялось есть еще один символ '-' с кодом 173
                                if (arrayChars.get(i - 1) >= 'А' && arrayChars.get(i - 1) <= 'Я' || arrayChars.get(i - 1) >= 'а' && arrayChars.get(i - 1) <= 'я') {
                                    arrayChars.add(' ');
                                    i++;
                                    hyphenFlag = false;
                                }
                            } else { //реакция на дефис, если до этого дефис встречался добавляем пробел
                                if (hyphenFlag && (arrayChars.get(i - 1) >= 'А' && arrayChars.get(i - 1) <= 'Я' || arrayChars.get(i - 1) >= 'а' && arrayChars.get(i - 1) <= 'я')) {
                                    arrayChars.add(' ');
                                    i++;
                                    hyphenFlag = false;
                                } else {
                                    hyphenFlag = true;
                                }
                            }
                        }
                    }
                }
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return arrayChars;
    }

    /**
     * Узнать расширение файла
     *
     * @param filename - полное имя файла
     * @return - расширение файла
     */
   /*
    private static String getFileExtension(String filename) {
        int dotPos = filename.lastIndexOf(".") + 1;
        return filename.substring(dotPos);
    }

    public static void saveStandardText(String inFile, String outDir) {
        //записываем в каталог
        File file = new File(inFile);
        List<Character> chars = editTextWithSpace(file);
        File folder = new File(outDir);
        if (!(folder.exists())) {
            folder.mkdirs();
        }
        RandomAccessFile out;
        File outFile = new File(outDir + "\\STD_" + file.getName());
        try {
            if (outFile.exists()) {
                //удаляем файл если уже существует
                outFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            out = new RandomAccessFile(outDir + "\\STD_" + file.getName(), "rw");
            //записывам полученный текст
            StringBuilder builder = new StringBuilder(chars.size());
            chars.forEach(builder::append);
            out.write(builder.toString().toUpperCase().getBytes("windows-1251"));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertAllTextsInFolderToStandardTextsInFolder(String inDir, String outDir) {
        File[] filesLen = new File(inDir).listFiles();
        for (int i = 0; i < filesLen.length; i++) {
            saveStandardText(filesLen[i].getPath(), outDir);
        }
    }
    */

    public static String convertToStandardText(String text) {
        String standardText = "";
        boolean hyphenFlag = false; //проверка на повторный дефис

        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c >= 'А' && c <= 'Я' || c >= 'а' && c <= 'я' || c == 'ё' || c == 'Ё') {
                if (c == 'ё' || c == 'Ё') {
                    standardText += 'Е';
                } else if (c == 'ъ' || c == 'Ъ') {
                    standardText += 'Ь';
                } else {
                    standardText += c;
                }
                hyphenFlag = false;
            } else {
                if (standardText.length() > 0) {
                    if (c != '-' && c != (char) 173) { //как выяснялось есть еще один символ '-' с кодом 173
                        if (standardText.charAt(standardText.length() - 1) >= 'А' && standardText.charAt(standardText.length() - 1) <= 'Я' || standardText.charAt(standardText.length() - 1) >= 'а' && standardText.charAt(standardText.length() - 1) <= 'я') {
                            standardText += ' ';
                            hyphenFlag = false;
                        }
                    } else { //реакция на дефис, если до этого дефис встречался добавляем пробел
                        if (hyphenFlag && (standardText.charAt(standardText.length() - 1) >= 'А' && standardText.charAt(standardText.length() - 1) <= 'Я' || standardText.charAt(standardText.length() - 1) >= 'а' && standardText.charAt(standardText.length() - 1) <= 'я')) {
                            standardText += ' ';
                            hyphenFlag = false;
                        } else {
                            hyphenFlag = true;
                        }
                    }
                }
            }
        }
        return standardText.toUpperCase();
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