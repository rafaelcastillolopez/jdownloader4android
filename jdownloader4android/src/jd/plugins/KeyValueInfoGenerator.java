package jd.plugins;

import java.util.ArrayList;

public class KeyValueInfoGenerator {
    private ArrayList<String[]> pairs;
    private String              name;

    public KeyValueInfoGenerator(String title) {
        this.name = title;
        pairs = new ArrayList<String[]>();
    }

    public void addPair(String key, String value) {
        pairs.add(new String[] { key, value });
    }    

}
