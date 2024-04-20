package com.salt.cypher;

import com.salt.cypher.util.Math;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Decypher {
    private static Data<Character, Character> compareTable = new Data<>();

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

        compareTable.add(th[0], 't');
        compareTable.add(th[1], 'h');
        compareTable.add(th[2], 'e');

        ArrayList<Character> std = new ArrayList<>(getDist().keys);
        ArrayList<Character> mstd = new ArrayList<>(Math.generateDistribution(msg).keys);
    }

    public static String decryptedMessage() {
        String msg = getMessage();
        String decrypt = "";

        System.out.println(Math.generateDistribution(msg));

        char[] chars = msg.toCharArray();

        for (char c : chars) {
            if (compareTable.keys.contains(c)) {
                decrypt += "" + compareTable.get(c);
            } else {
                decrypt += "" + c;
            }
        }

        System.out.println(compareTable + "\n");

        System.out.println(msg);
        System.out.println(decrypt);

        System.out.println("\ninput change in \"a=b\" format (end to quit)");

        Scanner input = new Scanner(System.in);

        String in = input.nextLine();

        while (!in.equals("end")) {
            compareTable.add(in.charAt(0), in.charAt(2));
            decrypt = "";

            for (char c : chars) {
                if (compareTable.keys.contains(c)) {
                    decrypt += "" + compareTable.get(c);
                } else {
                    decrypt += "" + c;
                }
            }

            System.out.println("\n\n\n\n\n\n\n\n\n\n" + compareTable + "\n");

            System.out.println(msg);
            System.out.println(decrypt);

            System.out.println("\ninput change in \"a=b\" format (end to quit)");

            input = new Scanner(System.in);

            in = input.nextLine();
        }

        return decrypt;
    }
}
