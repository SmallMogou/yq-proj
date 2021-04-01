package lm.api;

import lm.utils.YqUtils;
import java.util.ArrayList;

/**
    一个省的信息
    @author lm
    since 3.0
 */
public class Province{
    private String province;
    private int caseSum;
    private ArrayList<Area> areas;

    public Province(String province, int caseSum, ArrayList<Area> areas){
        this.province = province;
        this.caseSum = caseSum;
        this.areas = areas;
    }

    public String getProvince(){
        return this.province;
    }

    public int getCaseSum(){
        return this.caseSum;
    }

    public ArrayList<Area>  getAreas(){
        return this.areas;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public void setCaseSum(int caseSum){
        this.caseSum = caseSum;
    }

    public void setAreas(ArrayList<Area> areas){
        this.areas = areas;
    }
    
    /**
        按照病例总数从大到小排序省
        @author lm
        since 3.0
     */

    public static void sort(Province[] provinces){
        String[] provinceArr2 = new String[2];
        Province temp = null;
        ArrayList<Area> tempAreas = null;
        for(int i = 0; i < provinces.length; i++){
            for(int j = i + 1; j < provinces.length; j++){
                if(provinces[i].getCaseSum() < provinces[j].getCaseSum()){
                    temp = provinces[i];
                    provinces[i] = provinces[j];
                    provinces[j] = temp;
                } else if(provinces[i].getCaseSum() == provinces[j].getCaseSum()){
                    // 根据拼音排序时，交换两个省的地区
                    tempAreas = provinces[i].getAreas();
                    provinces[i].setAreas(provinces[j].getAreas());
                    provinces[j].setAreas(tempAreas);
                    provinceArr2[0] = provinces[i].getProvince();
                    provinceArr2[1] = provinces[j].getProvince(); 
                    YqUtils.sortByPinyinCode(provinceArr2);
                    provinces[i].setProvince(provinceArr2[0]);
                    provinces[j].setProvince(provinceArr2[1]);
                }
            }
        }
    }
}