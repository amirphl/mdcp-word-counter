package com.example;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.net.URL;
import java.lang.Integer;

public class Job {
    public static void main(String[] args) throws IOException {
        String inputFileUrl = args[0];
        String outputFilePath = args[1];
        int fraction = Integer.parseInt(args[2]);
        int totalFractions = Integer.parseInt(args[3]);
        Job j = new Job();
        j.start(inputFileUrl, outputFilePath, fraction, totalFractions);
    }

    public void start(String inputFileURL, String outputFilePath, int fration, int totalFractions) throws IOException {
        ArrayList<String[]> content = readTextFile(inputFileURL);
        HashMap<String, Integer> wordCounts = countWords(content);
        writeMapInCSV(wordCounts, outputFilePath);
    }

    private ArrayList<String[]> readTextFile(String inputFileURL) throws IOException {
        URL url = new URL(inputFileURL);
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String row;
        ArrayList<String[]> content = new ArrayList<String[]>();
        while ((row = csvReader.readLine()) != null)
            content.add(row.split(","));
        csvReader.close();
        return content;
    }

    private HashMap<String, Integer> countWords(ArrayList<String[]> content) {
        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
        for (String[] row : content)
            for (String word : row)
                if (wordCounts.containsKey(word))
                    wordCounts.put(word, wordCounts.get(word) + 1);
                else
                    wordCounts.put(word, 1);
        return wordCounts;
    }

    private void writeMapInCSV(HashMap<String, Integer> map, String outputFilePath) throws IOException {
        FileWriter csvWriter = new FileWriter(outputFilePath);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            csvWriter.append(key);
            csvWriter.append(",");
            csvWriter.append(String.valueOf(value));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
