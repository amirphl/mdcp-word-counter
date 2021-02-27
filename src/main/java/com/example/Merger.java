package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Merger {

    public static String OUTPUT_PATH;
    public static final String SPLITTER = "#";

    public static void write(HashMap<String, Integer> data) throws IOException {
        FileWriter fw = new FileWriter(new File(OUTPUT_PATH));
        for (Map.Entry<String, Integer> e : data.entrySet()) {
            fw.write(e.getKey());
            fw.write(" : ");
            fw.write(String.valueOf(e.getValue()));
            fw.write("\n");
        }
    }

    public static HashMap<String, Integer> mergeTwoFreqDoc(HashMap<String, Integer> firstFreqDoc,
            HashMap<String, Integer> secondFreqDoc) {
        String word;
        Integer oldFreq, currFreq, newFreq;
        for (Map.Entry<String, Integer> wordFreq : secondFreqDoc.entrySet()) {
            word = wordFreq.getKey();
            currFreq = wordFreq.getValue();
            oldFreq = firstFreqDoc.get(word);
            newFreq = (oldFreq == null) ? currFreq : currFreq + oldFreq;
            firstFreqDoc.put(word, newFreq);
        }
        return firstFreqDoc;
    }

    public static HashMap<String, Integer> merge(ArrayList<HashMap<String, Integer>> freqDocs) {
        Optional<HashMap<String, Integer>> finalFreqDoc = freqDocs.stream()
                .reduce((firstFreqDoc, secondFreqDoc) -> mergeTwoFreqDoc(firstFreqDoc, secondFreqDoc));
        return finalFreqDoc.get();
    }

    public static ArrayList<HashMap<String, Integer>> readFreqDocs(File freqDocFiles[]) throws IOException {
        String line, word, arr[];
        Integer oldFreq, currFreq, newFreq;
        ArrayList<HashMap<String, Integer>> freqDocs = new ArrayList<>();

        for (File f : freqDocFiles) {
            HashMap<String, Integer> freqDoc = new HashMap<>();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                while ((line = br.readLine()) != null) {
                    arr = line.split(SPLITTER);
                    word = arr[0];
                    // freq = Integer.parseInt(arr[1]); // TOOD fix malformed words with # inside
                    // and duplicates inside a file
                    oldFreq = freqDoc.get(word);
                    currFreq = Integer.parseInt(arr[arr.length - 1]);
                    newFreq = (oldFreq == null) ? currFreq : currFreq + oldFreq;
                    freqDoc.put(word, newFreq);
                }
            }
            freqDocs.add(freqDoc);
        }

        return freqDocs;
    }

    public static void main(String args[]) throws IOException {
        long s = System.currentTimeMillis();
        if (args.length == 0) {
            System.out.println("no args found...");
            return;
        }

        OUTPUT_PATH = args[args.length - 1];
        File[] freqDocFiles = new File[args.length - 1];
        for (int i = 0; i < freqDocFiles.length; i++)
            freqDocFiles[i] = new File(args[i]);
        // File[] freqDocFiles = new File("xx").listFiles();

        ArrayList<HashMap<String, Integer>> freqDocs = readFreqDocs(freqDocFiles);
        HashMap<String, Integer> finalFreqDoc = merge(freqDocs);
        write(finalFreqDoc);
        long e = System.currentTimeMillis();
        System.out.println("took " + (e - s) + " milliseconds");
    }
}

