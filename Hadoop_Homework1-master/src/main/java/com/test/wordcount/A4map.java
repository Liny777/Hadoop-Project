package com.test.wordcount;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class A4map extends MapReduceBase implements Mapper<Text, Text, Text, Text> {
    Text t = new Text();
    Text t1 = new Text();
    public void map(Text attacker, Text victim, OutputCollector<Text, Text> output,
                    Reporter reporter) throws IOException {
        String[] temp = attacker.toString().split(" ");
        t.set(temp[0]);
        t1.set(temp[1]);
        output.collect(t1, t);
    }
}
