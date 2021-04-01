package lm.api;

import lm.utils.YqUtils;

/**
    一个地区的信息
    @author lm
    since 3.0
 */
public class Area{
    private String area;
    private int cases;

    public Area(String area, int cases){
        this.area = area;
        this.cases = cases;
    }

    public String getArea(){
        return this.area;
    }

    public int getCases(){
        return this.cases;
    }

    public void setArea(String area){
        this.area = area;
    }

    public void setCases(int cases){
        this.cases = cases;
    }

    /**
        按照病例数从大到小排序地区
        @author lm
        since 3.0
     */
    public static void sort(Area[] areas){
        String[] areaArr2 = new String[2];
        Area temp = null;
        for(int i = 0; i < areas.length; i++){
            for(int j = i + 1; j < areas.length; j++){
                if(areas[i].getCases() < areas[j].getCases()){
                    temp = areas[i];
                    areas[i] = areas[j];
                    areas[j] = temp;
                } else if (areas[i].getCases() == areas[j].getCases()){
                    areaArr2[0] = areas[i].getArea();
                    areaArr2[1] = areas[j].getArea(); 
                    YqUtils.sortByPinyinCode(areaArr2);
                    areas[i].setArea(areaArr2[0]);
                    areas[j].setArea(areaArr2[1]);
                }
            }
        }
    }
}