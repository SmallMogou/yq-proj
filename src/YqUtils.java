package lm.utils;

import java.text.Collator;
import java.util.Comparator;
import java.util.ArrayList;
import java.io.*;

import lm.api.*;

/**
    工具类，命令参数处理，文件io处理
    @author lm
    since 3.0
 */
public class YqUtils{

    private static String inputPath = "D:\\Learning\\java-lang\\basic\\yq\\yq_in.txt"; // 读取文件路径 默认yq_in.txt
    private static String outputPath = "yq_out.txt"; // 写入文件路径 默认yq_out.txt
    private static String provinceOfArgs = ""; // 指定输入省
    private static boolean argsIsOk = true; // 执行参数是否输入正确
    public static final String encoding="GBK"; //编码格式

    /**
        处理命令参数
        该方法表示，输入参数只能是0个，1个，2个或者3个四种情况：
        当0个时：默认
        当1个是：指明文件读取路径
        当2个时：第一个参数指明文件读取路径，第二个指明文件输出路径
        当3个时：前2个同上，第三个指定获取的省信息
        @author lm
        since 3.0
     */
    public static void procArgs(String args[]){
        if (args.length == 0)
            return;
        inputPath = args[0];
        switch(args.length){
            case 1:
                break;
            case 2:
                outputPath = args[1];
                break;
            case 3:
                outputPath = args[1];
                provinceOfArgs = args[2];
                break;
            default:
                argsIsOk = false;
        }
    }

    /**
        汉字拼音排序
        @author lm
        since 3.0
     */
    public static void sortByPinyinCode(String[] tempStr){
        Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
        java.util.Arrays.sort(tempStr, cmp);     
    }

    /**
        读取文件
        @author lm
        since 3.0
     */
    public static Province[] readFile(){
        File file = null;
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        ArrayList<Province> provinces = null;
        Province province = null;
        ArrayList<Area> areas = null;
        String lineTxt = null;
        String[] tempArr = null;
        String temp = "";

        try {
            file = new File(inputPath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                read = new InputStreamReader(new FileInputStream(file),encoding);
                bufferedReader = new BufferedReader(read);
                provinces = new ArrayList<Province>();
   
                while((lineTxt = bufferedReader.readLine()) != null){ //读取文件
                    tempArr = lineTxt.split("\\s+");
                    if(provinceOfArgs != "" && !provinceOfArgs.equals(tempArr[0]))
                        continue;
                    if(!temp.equals(tempArr[0])){
                        temp = tempArr[0];
                        areas = new ArrayList<Area>();
                        province = new Province(temp, 0, areas);
                        provinces.add(province); // 存入yqInfos
                    }
                    if(!(tempArr[1]).equals("待明确地区")){ // 存入一个地区
                        province.getAreas().add(new Area(tempArr[1], Integer.valueOf(tempArr[2])));
                        province.setCaseSum(province.getCaseSum() + Integer.valueOf(tempArr[2]));
                    }
                }
            } else {
                System.out.println("找不到指定的文件，请检查参数是否正确");
            }           
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        } finally {
            ioClose(read, null, bufferedReader, null);
        }
        if(provinces != null)
            return provinces.toArray(new Province[provinces.size()]);
        else
            return null;
    }

    /**
        写入文件
        @author lm
        since 3.0
     */
    public static void writeFile(Province[] provinces){
        if (provinces != null) {
            FileWriter fwriter = null;
            try {
                fwriter = new FileWriter(outputPath, false);
                Province.sort(provinces);
                Area[] areaArr = null;
                for(Province p : provinces){ //写入文件
                    fwriter.write(p.getProvince()+ " " + "总数：" + p.getCaseSum() + "\r\n");
                    areaArr = p.getAreas().toArray(new Area[p.getAreas().size()]);
                    Area.sort(areaArr);
                    for(Area a : areaArr)
                        fwriter.write(a.getArea() + " " + a.getCases() + "\r\n");
                }
            } catch (Exception e) {
                System.out.println("写入文件内容出错");
                e.printStackTrace();
            } finally {
                ioClose(null, fwriter, null, null);
            }                
        }
    }

    /**
        处理数据
        @author lm
        since 3.0
     */
    public static void procYqData(String[] args){
        procArgs(args);
        if(argsIsOk)
            writeFile(readFile());
        else
            System.out.println("参数输入错误，请检查参数是否输入正确");     
    }

    /**
        资源释放
        @author lm
        since 3.0
     */
    public static void ioClose(InputStreamReader in, OutputStreamWriter out, Reader r, Writer w) {
        try {
            if (in != null)
                 in.close();
            if (out != null)
                out.close();
            if (r != null)
                r.close();
            if (w != null)
                w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}