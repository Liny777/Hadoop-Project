package com.test.wordcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.*;

public class A1reduce extends Reducer<Text, Text,Text, Text> {
    Map<String, List<String>> map =new HashMap<String,List<String>>();
    public void reduce(Text key, Iterable<Text> values,Context context)
            throws IOException,InterruptedException {
        List<String> arr = new ArrayList<String>();
        for (Text val:values){
            arr.add(val.toString());
        }
        map.put(key.toString(),arr);
    }
    @Override
    public void cleanup(Context context)throws IOException,InterruptedException {
        for (Map.Entry<String, List<String>> entry1 : map.entrySet())
        {
            int sim = 0;
            String followee = "";
            List<String> ls2 = new ArrayList<>(); // 用来存储最多关注者的列表
            for (Map.Entry<String, List<String>> entry2 : map.entrySet())
            {
                if(entry2.getKey()!= entry1.getKey())
                    {
                        List<String> ls1 = new ArrayList<String>();
                        ls1.addAll(entry1.getValue());
                        ls1.retainAll(entry2.getValue());
                        if (ls1.size()>sim)
                        {
                            followee = entry2.getKey();
                            sim = ls1.size();
                        }
                    }
            }
            Text text = new Text();
            text.set(entry1.getKey());
            Text text1 = new Text();
            text1.set(followee);
            int length = entry1.getKey().length();
//            if(length>=4){
//                if(entry1.getKey().substring(length-4,length).equals("9171")){
                    context.write(text,text1);
//                }
//            }

        }
    }
}
