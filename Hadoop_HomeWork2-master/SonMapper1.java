package Homework2_B;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SonMapper1 extends Mapper<LongWritable, Text, Text,Text> {
    private Map<String, Double> map = new HashMap<>();
    private Double total_rows = 0.0;
    Double mini_support = 0.005;
//    Set<String> candidatePairs=new HashSet<>();
    @Override
    protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {

    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        String[] arrs = null;
        total_rows ++;
        arrs = value.toString().split(" ");
        for (int j = 0; j < arrs.length; j++)
        {
            if(map.containsKey(arrs[j])==false)
            {
                map.put(arrs[j],1.0);
            }
            else
            {
                map.put(arrs[j],map.get(arrs[j])+1);
            }
        }
        System.out.println("Map: total_rows: "+total_rows);

    }

    @Override
    protected void cleanup(Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        Double threshold = mini_support*total_rows;
        Map<String,Double> frequent_item = new HashMap<String,Double>();
        for(Map.Entry<String,Double> entry:map.entrySet())
        {
            if(entry.getValue()>=threshold)
            {
                frequent_item.put(entry.getKey(), entry.getValue());
            }
        }
        System.out.println("频繁项完成，数量为: "+frequent_item.size()+" 总行数为： "+ total_rows);
        System.out.println("开始处理频繁对");
        // 生成候选对集
        Map<ArrayList<String>,Double> candidate_two = new HashMap<ArrayList<String>,Double>();
        for(Map.Entry<String,Double> entry:frequent_item.entrySet())
        {
            for(Map.Entry<String,Double> entry2:frequent_item.entrySet()){
                ArrayList<String> temp_can = new ArrayList<String>();
                if(entry.getKey().equals(entry2.getKey())){

                }else{
                    String s1 = new String(entry.getKey().toString().getBytes("GB2312"), "ISO-8859-1");
                    String s2 = new String(entry2.getKey().toString().getBytes("GB2312"), "ISO-8859-1");
                    if(s1.compareTo(s2)>0){
                        temp_can.add(entry2.getKey());
                        temp_can.add(entry.getKey());
                    }
                    if(s1.compareTo(s2)<0){
                        temp_can.add(entry.getKey());
                        temp_can.add(entry2.getKey());
                    }
                    candidate_two.put(temp_can,1.0);
                }
            }
        }
        for(Map.Entry<ArrayList<String>,Double> entry:candidate_two.entrySet()){
            String s = entry.getKey().get(0)+" "+entry.getKey().get(1);
            context.write(new Text(s),new Text(entry.getValue().toString()));
        }
    }
}