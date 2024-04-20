package com.salt.cypher;

import com.salt.cypher.util.Data;
import com.salt.cypher.util.Math;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Decypher {
    public static Data<Character, Character> compareTable = new Data<>();

    private static String getMessage() {
        File file = new File("/Users/saltdev/Documents/All Programming/IntelliJ/Reverse-Cypher/res/com/salt/cypher/message.txt");

        Scanner in;

        String message = "";

        try {
            in = new Scanner(file);
            while (in.hasNextLine()) {
                message += in.nextLine() + " ";
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        message = message.toLowerCase();



        return message;
    }

    private static Data<Character, Double> getDist() {
        File file = new File("/Users/saltdev/Documents/All Programming/IntelliJ/Reverse-Cypher/res/com/salt/cypher/letterDist.txt");

        Scanner in;

        char[] letters;
        double[] doubles = new double[26];
        String build = "";

        try {
            in = new Scanner(file);
            int i = 0;
            while (in.hasNextLine()) {
                build += in.nextLine();
                doubles[i++] = Double.parseDouble(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        build = build.toLowerCase();

        letters = build.toCharArray();

        Data<Character, Double> map = new Data<>();

        for (int j = 0; j < 26; j++) {
            map.add(letters[j], doubles[j]);
        }

        return map;
    }

    private static void buildCTable() {
        String msg = getMessage();
        ArrayList<String> words = new ArrayList<>();

        Data<Character, Character> ret = new Data<>();

        msg = msg.replaceAll("\\p{Punct}", "");
        String temp = msg.replaceAll("\\p{Punct}", "");

        while (temp.contains(" ")) {
            words.add(temp.substring(0, temp.indexOf(" ")));
            temp = temp.substring(temp.indexOf(" ")+1);
        }

        words.add(temp);

        String the = "";
        int cHigh = 0;
        int cCur = 0;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).length() == 3) {
                temp = words.get(i);
                for (int j = i+1; j < words.size(); j++) {
                    if (temp.equals(words.get(j))) {
                        cCur++;
                    }
                }

                if (cCur > cHigh) {
                    cHigh = cCur;
                    the = "";
                    the += temp;
                    cCur = 0;
                }
            }
        }

        char[] th = the.toCharArray();

        compareTable.set(th[0], 't');
        compareTable.set(th[1], 'h');
        compareTable.set(th[2], 'e');

        ArrayList<Character> std = new ArrayList<>(getDist().keys);
        ArrayList<Character> mstd = new ArrayList<>(Math.generateDistribution(msg).keys);
    }

    public static String decryptedMessage() {
        String msg = getMessage();
        String decrypt = "";

        Data<Character, Double> dist = Math.generateDistribution(msg);

        char[] chars = msg.toCharArray();

        Scanner input = new Scanner(System.in);

        String in = "";

        while (!in.equals("end")) {
            decrypt = "";

            for (char c : chars) {
                if (compareTable.keys.contains(c)) {
                    decrypt += "\u001B[32m" + compareTable.get(c);
                } else {
                    decrypt += "\u001B[37m" + c;
                }
            }
            decrypt += "\u001B[0m";

            System.out.println(dist + "\n" + compareTable + "\n");

            System.out.println(msg);
            System.out.println(decrypt);

            System.out.println("\ninput change in \"a=b\" format (end to quit)");

            in = input.nextLine();

            compareTable.set(in.charAt(0), in.charAt(2));

            System.out.println("\n\n\n\n\n\n\n\n\n");
        }

        return decrypt;
    }
}
