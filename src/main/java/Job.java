package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.lang.Integer;

public class Job {
    public static void main(String[] args) throws IOException {
        Job j = new Job();
        String outputFilePath = j.start(args[0]);
        System.out.println(outputFilePath);
    }

    public String start(String inputFilePath) throws IOException {
        ArrayList<String[]> content = readTextFile(inputFilePath);
        HashMap<String, Integer> wordCounts = countWords(content);
        String outputFilePath = generateOutputFilePathFromInputFilePath(inputFilePath);
        writeMapInCSV(wordCounts, outputFilePath);
        return outputFilePath;
    }

    private ArrayList<String[]> readTextFile(String inputFilePath) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(inputFilePath));
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

    private String generateOutputFilePathFromInputFilePath(String inputFilePath) {
        String[] arr = inputFilePath.split("/");
        arr[arr.length - 1] += "_output";
        StringBuilder builder = new StringBuilder();
        for (String s : arr) {
            builder.append(s);
            builder.append("/");
        }
        return builder.toString();
    }
}
