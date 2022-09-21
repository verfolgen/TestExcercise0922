package org.sbitnev.part1.onethree;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ConversionValue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DecimalFormat decimalFormat = new DecimalFormat("#.#####");

        HashMap<String, HashMap<String, Float>> tableTransfer = new HashMap<>();

        String[] inputData;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.contains("?")) {
                inputData = line.replace("= ? ", "").split(" ");
                String answer = findAnswer(Float.parseFloat(inputData[0]), inputData[1], inputData[2], tableTransfer);
                if (answer.equals("Conversation not possible")) {
                    System.out.println(answer);
                } else {
                    System.out.println(line.replace("?", decimalFormat.format(Float.parseFloat(answer))));
                }
            } else if (line.contains("=")) {
                inputData = line.replace("= ", "").split(" ");
                Float a = Float.parseFloat(inputData[0]);
                Float b = Float.parseFloat(inputData[2]);
                if (a <= b) {
                    WriteToHashMap(b / a, inputData[1], inputData[3], tableTransfer);
                } else {
                    WriteToHashMap(a / b, inputData[3], inputData[1], tableTransfer);
                }
            }
        }
        sc.close();
    }

    public static String findAnswer(float number, String V, String W, HashMap<String, HashMap<String, Float>> tableTransfer) {
        if (tableTransfer.containsKey(V)) {
            HashMap<String, Float> tempMap = tableTransfer.get(V);
            if (tempMap.containsKey(W)) {
                return Float.toString(number * tempMap.get(W));
            }
        }
        if (tableTransfer.containsKey(W)) {
            HashMap<String, Float> tempMap = tableTransfer.get(W);
            if (tempMap.containsKey(V)) {
                return Float.toString(number / tempMap.get(V));
            }
        }

        if (tableTransfer.containsKey(V)) {
            HashMap<String, Float> tempMap = tableTransfer.get(V);
            Set<String> tempKeys = tempMap.keySet();
            for (String key: tempKeys) {
                Float k = tempMap.get(key);
                String answer = findAnswer(number * k, key, W, tableTransfer);
                if (!answer.equals("Conversation not possible")) {
                    return answer;
                }
            }
        }
        if (tableTransfer.containsKey(W)) {
            HashMap<String, Float> tempMap = tableTransfer.get(W);
            Set<String> tempKeys = tempMap.keySet();
            for (String key: tempKeys) {
                Float k = tempMap.get(key);
                String answer = findAnswer(number / k, V, key, tableTransfer);
                if (!answer.equals("Conversation not possible")) {
                    return answer;
                }
            }
        }
        return "Conversation not possible";
    }
    public static void WriteToHashMap(float p, String V, String W, HashMap<String, HashMap<String, Float>> tableTransfer) {
        if (tableTransfer.containsKey(V)) {
            if (!tableTransfer.get(V).containsKey(W)) {
                HashMap<String, Float> tempMap = tableTransfer.get(V);
                tempMap.put(W, p);
                tableTransfer.put(V, tempMap);
            }
        } else {
            HashMap<String, Float> innerMap = new HashMap<>();
            innerMap.put(W, p);
            tableTransfer.put(V, innerMap);
        }
    }
}
