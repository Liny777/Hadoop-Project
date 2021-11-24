package com.test.wordcount;

//import org.openjsse.legacy8ujsse.sun.security.ssl.AppOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;

import java.util.*;

public class Hellostring {
    public static void main(String[] args){
//        String uuu = "0:1166,{2582}";
        String[] aa ="0:1166,{2582}".split(":");
        String[] bb ="0:1701,{1862, 633}\tsim:0.2".split(":");
        for (int i = 0 ; i <bb.length ; i++ ) {
            System.out.println("--"+bb[i]);
        }
        //String[] aa = "aaa|bbb|ccc".split("\\|"); 这样才能得到正确的结果
        for (int i = 0 ; i <aa.length ; i++ ) {
            System.out.println("--"+aa[i]);
        }
//        int a = 5;
//        int b =4;
//        int k = 1;
//        float c =5;
//        float d = 4;
//        float e =1 ;
//        float h = k/(a+b-k);
//        System.out.println(h);
//        float g = e/(c+d-e);
//        System.out.println(g);
//        String temp = "123";
//        temp = "[asdsad]";
//        int a = 123;
//        System.out.println(temp.replace("[","{").replace("]","}"));
//        Text v;
//        v.set("123");
//        String a = "123";
//        String b = new String("asbd");
//        b = "1232";
//        System.out.println("B: "+b);
//        a= "124122132132";
//        System.out.println(a);
//        Map<String, List<String>> map =new HashMap<String,List<String>>();
//        List<String> arr = new ArrayList<>();
//        arr.add("E");
//        arr.add("B");
//        arr.add("C");
//        arr.add("D");
//        map.put("A",arr);
////////        System.out.println((arr));
//        List<String> arr1 = new ArrayList<>();
//        arr1.add("E");
//        arr1.add("G");
//        arr1.add("C");
//        arr1.add("D");
//        map.put("B",arr1);
//        Map<String,Integer> map1 = new HashMap<>();
////        map1.put("A",3);
//        map1.put("B",3);
//        map1.put("C",3);
//        map1.put("D",3);
//        map1.put("E",3);
//        map1.put("F",3);
//        map1.put("G",3);
//        for (Map.Entry<String, List<String>> entry1 : map.entrySet()) {
//            for (Map.Entry<String, List<String>> entry2 : map.entrySet()) {
//                List<String> ls1 = new ArrayList<String>();
//                ls1.addAll(entry1.getValue());
//                ls1.retainAll(entry2.getValue());
//                System.out.println(ls1.toString());
////                int sim = map1.get(entry1.getKey());
////                System.out.println("key: "+entry1.getKey());
////                System.out.println("Sim: "+sim);
//            }
//        }
//        System.out.println(arr);
//        list1.retainAll(list2);
//        String value = "1 11";
//        String blog = value.toString();
//        String[] words = StringUtils.split(value," ");

//        for(String word : words)
//        {
//            System.out.println(words[0]); System.out.println(words[1]);
                         //输出的key是Text类型的，value是LongWritable类型的

//        }


    }
}
