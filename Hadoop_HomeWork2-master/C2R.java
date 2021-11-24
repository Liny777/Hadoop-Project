package HK2_C;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.*;

public class C2R extends Reducer<Text, DoubleWritable, Text, NullWritable> {
    Map<String, Double> map2 = new HashMap<>();
    Double totalRow = 0.0;
    Double mini_support = 0.0025;
    Map<String,Double> frequent = new HashMap<>();
    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Reducer<Text, DoubleWritable, Text, NullWritable>.Context context) throws IOException, InterruptedException {
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
    public void cleanup(Reducer<Text, DoubleWritable, Text, NullWritable>.Context context)throws IOException,InterruptedException {
        for(Map.Entry<String,Double> entry:map2.entrySet()){
            if(entry.getValue()>=mini_support*totalRow){
//               context.write(new Text(entry.getKey()),new DoubleWritable(entry.getValue()));
                frequent.put(entry.getKey(), entry.getValue());
            }
        }
        // 产生三元候选集 String[] arr = str.split(",")
        Set<String> triple_item = new HashSet<>();
        for(Map.Entry<String,Double> entry1:frequent.entrySet())
        {
            for(Map.Entry<String,Double> entry2:frequent.entrySet())
            {
                if(entry1.getKey().equals(entry2.getKey()))
                {
                    // 排除自己
                }else{
                    String[] arr1 = entry1.getKey().split(" ");
                    String[] arr2 = entry2.getKey().split(" ");
                   for(int k=0;k<arr2.length;k++){
                       if(arr2[k].equals(arr1[0]) || arr2.equals(arr1[1])){

                       }else{
                           String[] pair={arr1[0],arr1[1],arr2[k]};
                           Arrays.sort(pair);
//                           String s = Arrays.toString(pair);
                           String s = pair[0] + " "+pair[1]+" "+pair[2];
                           triple_item.add(s);
                       }
                   }
                }
            }
        }

        for(String value: triple_item){
            context.write(new Text(value), NullWritable.get());
        }

    }

}

