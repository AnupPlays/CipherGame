package com.salt.cypher.util;

import com.salt.cypher.Data;

import java.util.ArrayList;

public class Math {
    public static Data<Character, Double> generateDistribution(String msg) {
        Data<Character, Double> genMap = new Data<>();
        char cur = 'a';
        char[] split = msg.toCharArray();
        double len = msg.length();

        int c = 0;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < split.length; j++) {
                if (split[j] == cur) {
                    c++;
                }
            }
            double p = (c/len)*100;
            genMap.add(cur, p);
            c=0;cur++;
        }

        return rankDistribution(genMap);
    }

    private static Data<Character, Double> rankDistribution(Data<Character, Double> map) {
        ArrayList<Double> tempP = new ArrayList<>(map.values);
        ArrayList<Double> sortP = new ArrayList<>(map.values);
        ArrayList<Character> tempC = new ArrayList<>(map.keys);
        ArrayList<Character> sortC = new ArrayList<>();

        sortP.sort((o1, o2) -> (int) ((o2 - o1) * 10));

        for (Double d : sortP) {
            for (int j = 0; j < tempP.size(); j++) {
                if ((double) d == (double) tempP.get(j)) {
                    sortC.add(tempC.get(j));
                    tempC.set(j, ' ');
                    tempP.set(j, -1.0);
                    break;
                }
            }
        }

        Data<Character, Double> ret = new Data<>();

        for (int i = 0; i < tempC.size(); i++) {
            ret.add(sortC.get(i), sortP.get(i));
        }

        return ret;
    }
}
