package Homework2_A;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import java.io.*;
import java.util.*;

public class Apriori_Second_Pass
{
    static Double minSupport = 0.005;
    static Double total_row = 0.0;
    static ArrayList<ArrayList<String>> baskets = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> temp_baskets = new ArrayList<ArrayList<String>>();
    static ArrayList<String> all_items = new ArrayList<String>();
    public static void main(String args[]) throws IOException {
        // 获取一级频繁集
        long startTime=System.currentTimeMillis();
        System.out.println("Pass2 startTime: "+startTime);
        Map<String, Double> frequent_one = new HashMap<String, Double>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/output/pass1_result.txt"));
            String str;
            String[] arrs = null;
            while ((str = in.readLine()) != null) {
                arrs = str.split(" ");
                if (arrs[0] != null) {
//                    System.out.println("arrs[0]: "+arrs[0]);
                    frequent_one.put(arrs[0], Double.valueOf(arrs[1]));
                }
                arrs = null;
            }
        } catch (IOException e) {
        }
        // 生成候选对集
        Map<ArrayList<String>, Double> candidate_two = new HashMap<ArrayList<String>, Double>();
        for (Map.Entry<String, Double> entry : frequent_one.entrySet()) {
            for (Map.Entry<String, Double> entry2 : frequent_one.entrySet()) {
                ArrayList<String> temp_can = new ArrayList<String>();
                if (entry.getKey().equals(entry2.getKey())) {

                } else {
                    String s1 = new String(entry.getKey().toString().getBytes("GB2312"), "ISO-8859-1");
                    String s2 = new String(entry2.getKey().toString().getBytes("GB2312"), "ISO-8859-1");
//                    System.out.println(s1.compareTo(s2));
                    if (s1.compareTo(s2) > 0) {
                        temp_can.add(entry2.getKey());
                        temp_can.add(entry.getKey());
                    }
                    if (s1.compareTo(s2) < 0) {
                        temp_can.add(entry.getKey());
                        temp_can.add(entry2.getKey());
                    }
                    candidate_two.put(temp_can, 1.0);
                }
            }
        }

        System.out.println("候选集生成完成: " + candidate_two.size());
        // 计算支持度
        File file = new File("src/dataset");
        File[] tempList = file.listFiles();
        ArrayList<String> temp_compare1 = new ArrayList<String>();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("文     件：" + tempList[i]);
                FileReader fr = new FileReader(tempList[i].toString());
                BufferedReader br = new BufferedReader(fr);
                String line1 = "";
                String[] arrs1 = null;
                while ((line1 = br.readLine()) != null) {
                    total_row = total_row + 1.0;
                    arrs1 = line1.split(" ");
                    for (int e = 0; e < arrs1.length; e++) {
                        for (int a = e + 1; a < arrs1.length; a++) {
                            temp_compare1.clear();
                            temp_compare1.add(arrs1[e]);
                            temp_compare1.add(arrs1[a]);

                            if (candidate_two.containsKey(temp_compare1) == true || candidate_two.containsKey(reverse(temp_compare1))) {
                                Double temp_count = candidate_two.get(temp_compare1) + 1.0;
                                candidate_two.replace(temp_compare1, temp_count);
                            }


//
                        }
                    }
                    arrs1 = null;
                }
                br.close();
                fr.close();
            }
        }

        System.out.println("支持度计算完成");
        System.out.println("total_row: " + total_row);
        // 筛选频繁集
        File writename = new File("src/output/pass2_result.txt");
        writename.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        Map<ArrayList<String>, Double> frequent = new HashMap<ArrayList<String>, Double>();
        for (Map.Entry<ArrayList<String>, Double> entry : candidate_two.entrySet()) {
            if (entry.getValue() / total_row >= minSupport) {
                frequent.put(entry.getKey(), entry.getValue());
//                out.write(entry.getKey()+ " "+entry.getValue()+"\r\n");
//                out.flush(); // 把缓存区内容压入文件
            }
        }
        List<Map.Entry<ArrayList<String>, Double>> list = new ArrayList<Map.Entry<ArrayList<String>, Double>>(frequent.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<ArrayList<String>, Double>>() {
            @Override
            public int compare(Map.Entry<ArrayList<String>, Double> o1, Map.Entry<ArrayList<String>, Double> o2) {
                int compare = o1.getValue().compareTo(o2.getValue());
                return -compare;
            }
        });
        for (Map.Entry<ArrayList<String>, Double> entry : list) {
        out.write(entry.getKey() + " " + entry.getValue() + "\r\n");
        out.flush(); // 把缓存区内容压入文件
    }
        out.close();
        System.out.println("频繁集完成，数量为: "+frequent.size());
        long endTime=System.currentTimeMillis();
        System.out.println("Pass2 endTime: "+endTime);
        System.out.println("Pass2 程序运行时间： "+(endTime-startTime)+"ms");
    }

    public static ArrayList<String> reverse(ArrayList<String> list) {
        Collections.reverse(list);
        return list;
    }
//
}
