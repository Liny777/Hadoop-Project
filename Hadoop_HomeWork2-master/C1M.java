package HK2_C;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class C1M extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    private Double min_support = 0.0025;
    private Double total_rows = 0.0;
    private Set<String> item_map = new HashSet<>();
    private Map<String,Double> pairCount=new HashMap<>();
    private ArrayList<ArrayList<String>> baskets=new ArrayList<ArrayList<String>>();
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
        ArrayList<String> temp_dataset = new ArrayList<String>();
//        System.out.println("key: "+value.toString());
        String[] arrs = value.toString().split(" ");
        for (int i = 0; i < arrs.length; i++)
        {
            temp_dataset.add(arrs[i]);
            if(item_map.contains(arrs[i])){
                pairCount.put(arrs[i],pairCount.get(arrs[i])+1.0);
            }else{
                item_map.add(arrs[i]);
                pairCount.put(arrs[i],1.0);
            }
        }
        baskets.add(temp_dataset);
        total_rows ++;
    }

    @Override
    protected void cleanup(Mapper<LongWritable, Text, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
//        context.write(new Text("1"),new Text("1"));
        Double threshold = min_support*total_rows;
        Map<String,Double> frequent_item = new HashMap<String,Double>();
        for(Map.Entry<String,Double> entry:pairCount.entrySet())
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
        // 给reducer输出候选对
        for (Map.Entry<ArrayList<String>, Double> entry : candidate_two.entrySet())
        {
            String temp_key = entry.getKey().get(0)+" "+entry.getKey().get(1);
            context.write(new Text(temp_key),new DoubleWritable(entry.getValue()));
        }


    }

}
