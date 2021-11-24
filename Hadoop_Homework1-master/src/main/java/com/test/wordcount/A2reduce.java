package com.test.wordcount;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

    public class A2reduce extends Reducer<Text, Text,Text, Text> {
    Map<String, List<String>> map =new HashMap<String,List<String>>();
    Map<String, Integer> map1 = new HashMap<>();
    public void reduce(Text key, Iterable<Text> values,Context context)
            throws IOException,InterruptedException {
        List<String> arr = new ArrayList<String>();
        for (Text val:values){
            arr.add(val.toString());
        }
        map.put(key.toString(),arr);
    }
    @Override
    public void cleanup(Context context)throws IOException,InterruptedException
    {
        float A_sim = 0;
        String temp = "";
        float A_intersection_B_sim = 0;
        float B_sim = 0;
        Text text = new Text();
        Text text1 = new Text();
        FloatWritable similar = new FloatWritable();
        for (Map.Entry<String, List<String>> entry1 : map.entrySet())
        {

            A_sim = entry1.getValue().size();
            for (Map.Entry<String, List<String>> entry2 : map.entrySet())
            {
                if(entry2.getKey().equals(entry1.getKey())) {

                }else{
                    temp = "";
                    List<String> ls1 = new ArrayList<String>();
                    ls1.addAll(entry1.getValue());
                    ls1.retainAll(entry2.getValue());
                    if(ls1.size()!=0){
                        A_intersection_B_sim = ls1.size();
                        String common_followee = "";
                        common_followee = ls1.toString().replace("[","{").replace("]","}");
                        B_sim = entry2.getValue().size();
                        float A_and_B_sim = 0;
                        A_and_B_sim = A_intersection_B_sim / ( A_sim + B_sim - A_intersection_B_sim );
                        temp = entry1.getKey() + ":" + entry2.getKey()+","+ common_followee;
                        text.set(temp);
                        text1.set("sim:"+similar);
                        similar.set(A_and_B_sim);
                        context.write(text,text1);
                    }

                }

            }

        }
    }
}
