package HK2_C;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;

public class C3M extends Mapper<LongWritable, Text, Text,DoubleWritable> {
    private Double total_rows = 0.0;
    private Integer baskets = 0;
    private Set<String> map1 = new HashSet<>();
    Map<String,Double> candidate_two = new HashMap<>();
    Map<String,Double> candidate = new HashMap<>();
    @Override
    protected void setup(Mapper<LongWritable, Text, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
        URI[] cacheFiles = context.getCacheFiles();
        FileReader in = new FileReader("./test");
        BufferedReader bufferedReader = new BufferedReader(in);
//        URI[] cacheFiles = context.getCacheFiles();
//        FileSystem in = FileSystem.get(context.getConfiguration());
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in.open(new Path(cacheFiles[0].toString()))));
        String str;
        // 将上一步的输出的所有候选对存入一个map里。
        while((str = bufferedReader.readLine()) != null) {
            String[] split = str.split(" ");
            if(split.length==3){
                String[] item = {split[0],split[1],split[2]};
                Arrays.sort(item);
                String item_String = item[0] +" "+ item[1]+" " + item[2];
                map1.add(item_String);
            }
        }
        bufferedReader.close();
        in.close();
    }
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
        total_rows ++;
        System.out.println("value: "+value);
        String[] arrs = value.toString().split(" ");
        // 计算所有候选对在当前块下的支持度
        System.out.println("开始计算所有候选对在当前块下的支持度");
        for (int i = 0; i < arrs.length; i++)
        {
            for (int j = i + 1; j < arrs.length; j++)
            {
                for(int k = j + 1; k<arrs.length;k++){
                    String[] temp_s = {arrs[i],arrs[j],arrs[k]};
                    Arrays.sort(temp_s);
                    String ts = temp_s[0]+" "+temp_s[1]+" "+temp_s[2];
//                    String ts = Arrays.toString(temp_s);
                    if(map1.contains(ts)==true){
                        Double count = candidate.get(ts);
                        if (count==null){
                            candidate.put(ts,1.0);
                        }else{
                            candidate.put(ts,count+1.0);
                        }
                    }
                }
            }
        }
                System.out.println("完成计算所有候选对在当前块下的支持度");
    }

    @Override
    protected void cleanup(Mapper<LongWritable, Text, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
        for(Map.Entry<String,Double> entry:candidate.entrySet()){
            context.write(new Text(entry.getKey()),new DoubleWritable(entry.getValue()));
        }
        System.out.println("总行数量: "+total_rows);
        context.write(new Text("son_totalRows"), new DoubleWritable(total_rows));
    }

}