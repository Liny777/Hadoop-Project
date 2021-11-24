package Homework2_B;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.*;

public class SonReducer2 extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    Map<String, Double> map2 = new HashMap<>();
    Double totalRow = 0.0;
    Double mini_support = 0.005;
    Map<String,Double> frequent = new HashMap<>();
    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Reducer<Text, DoubleWritable, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
        if(key.toString().equals("son_totalRows")){
            for (DoubleWritable val:values){
                totalRow = totalRow + val.get();
            }
        }else{
            Double support = 0.0;
            for (DoubleWritable val:values){
                support = support + val.get();
            }
            if(map2.containsKey(key.toString())==true){
                // 已存在
                Double count_temp = support + map2.get(key.toString());
                map2.put(key.toString(),count_temp);
            }else{
                map2.put(key.toString(),support);
            }
        }
    }
    @Override
    public void cleanup(Reducer<Text, DoubleWritable, Text, DoubleWritable>.Context context)throws IOException,InterruptedException {
        for(Map.Entry<String,Double> entry:map2.entrySet()){
           if(entry.getValue()>=mini_support*totalRow){
//               context.write(new Text(entry.getKey()),new DoubleWritable(entry.getValue()));
               frequent.put(entry.getKey(), entry.getValue());
           }
        }
//        List<Map<String,Double>> mapList = new ArrayList<>();
        List<Map.Entry<String,Double>> mapList = new ArrayList<Map.Entry<String,Double>>(frequent.entrySet());
        Collections.sort(mapList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return -(o1.getValue().compareTo(o2.getValue()));
            }
        });
        for(Map.Entry<String,Double> mapping:mapList){
            context.write(new Text(mapping.getKey()),new DoubleWritable(mapping.getValue()));
        }
        System.out.println("totalRow: "+totalRow);
        System.out.println("map2: "+frequent.size());
    }

}
