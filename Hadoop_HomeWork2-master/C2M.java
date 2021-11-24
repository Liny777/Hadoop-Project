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

public class C2M extends Mapper<LongWritable, Text, Text,DoubleWritable> {
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
            String[] item = {split[0], split[1]};
            Arrays.sort(item);
//            String item_String = Arrays.toString(item);
            String item_String = item[0]+" "+item[1];
            map1.add(item_String);
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
        for (int e = 0; e < arrs.length; e++)
        {
            for (int a = e + 1; a < arrs.length; a++)
            {
                String[] s1 = {arrs[e],arrs[a]};
                Arrays.sort(s1);
//                String s = Arrays.toString(s1);
                String s = s1[0]+" "+s1[1];
                if(map1.contains(s)==true){
                    Double count = candidate.get(s);
                    if (count==null){
                        candidate.put(s,1.0);
                    }else {
                        candidate.put(s,count+1.0);
                    }
                    //                   candidate.put(s,1.0);
                }
            }
        }
        //        System.out.println("完成计算所有候选对在当前块下的支持度");
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