package Homework2_A;

import java.io.*;
import java.util.*;

public class Apriori_First_Pass {
    static ArrayList<ArrayList<String>> baskets = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> temp_baskets = new ArrayList<ArrayList<String>>();
    static ArrayList<String> all_items = new ArrayList<String>();
    // 需要满足的最小值尺度
    static Double minSupport = 0.005;
    static Double total_row = 0.0;
    public static void main(String args[]) throws IOException
    {
        long startTime=System.currentTimeMillis();
        System.out.println("Pass 1 startTime: "+startTime);
        File file = new File("src/dataset");
        File[] tempList = file.listFiles();
        //
        for (int i = 0; i < tempList.length; i++)
        {
            if (tempList[i].isFile())
            {
                System.out.println("文     件：" + tempList[i]);
                FileReader fr = new FileReader(tempList[i].toString());
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                String[] arrs = null;
                while ((line = br.readLine()) != null) {
                    total_row = total_row + 1;
                    arrs = line.split(" ");
                    ArrayList<String> basket = new ArrayList<String>();
                    for (int j = 0; j < arrs.length; j++) {
                        // 将每一个word放到arr里
                        basket.add(arrs[j]);
                        all_items.add(arrs[j]);
                    }
                    // 输入文件的每一行都是一个篮子，即一个交易，每一行出现的单词
                    baskets.add(basket);
                    // 交易的副本
                    temp_baskets.add(basket);
                    arrs = null;
                }
                br.close();
                fr.close();
            }
        }
        System.out.println("total rows: "+total_row);
        Map<String,Double> candidate = new HashMap<String,Double>();
        int count = 0;
        for(ArrayList<String> transaction : baskets){
            for(String item : transaction){
              if(candidate.containsKey(item)==false){
                  candidate.put(item,1.0);
              }else{
                  Double temp_value = candidate.get(item);
                  temp_value = temp_value + 1.0;
                  candidate.replace(item,temp_value);
              }
            }
        }
        System.out.println("支持度计算完成,候选集数量: "+candidate.size());
        // 开始筛选频繁集
        File writename  =new File("src/output/pass1_result.txt");
        writename .createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        Map<String,Double> frequent = new HashMap<String,Double>();
        for(Map.Entry<String,Double> entry:candidate.entrySet()){
            if(entry.getValue()/total_row >= minSupport ){
//                frequent.put(entry.getKey(),entry.getValue());
                out.write(entry.getKey()+ " "+entry.getValue()+"\r\n");
                out.flush(); // 把缓存区内容压入文件
            }
        }
        out.close();
        System.out.println("频繁集完成，数量为: "+frequent.size());
        long endTime=System.currentTimeMillis();
        System.out.println("Pass1 endTime: "+endTime);
        System.out.println("Pass1 程序运行时间： "+(endTime-startTime)+"ms");

    }
}
