package com.test.wordcount;

import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class A3reduce extends Reducer<Text,Text,Text,Text> {

    Text t = new Text();
    Text t1 =new Text();
    Integer num = new Integer(0);
    @Override
    protected void reduce(Text key,Iterable<Text> values,Context context) throws IOException, InterruptedException {
        // 1:遍历集合 获取V3 (first + second )
        List<String> arr = new ArrayList<String>();
        String first = "";
        String second = "";
        Map<String, List<String>> map =new HashMap<String,List<String>>();
        for (Text value : values){

            if(value.toString().startsWith("L")){
                first = value.toString().substring(3);
//                System.out.println(first);
            }else{
                second = value.toString();
                arr.add(second);
            }
        }
//        map.put(first,arr);

        num = arr.size();
        t.set(String.valueOf(num));
        t1.set(first);
        context.write(t1,t);
        // 2：将K3和V3写入上下文中
    }

}
