package com.hhwy.pm.qqch.wzch.demand.enums;

/**
 * @author HCT
 */

public enum MonthQuarterEnum {

    JAN(1,"01","1月", "第一季度","1"),
    FEB(2, "02","2月","第一季度","1"),
    MAR(3, "03","3月","第一季度","1"),
    APR(4, "04","4月","第二季度","2"),
    MAY(5, "05","5月","第二季度","2"),
    JUN(6, "06","6月","第二季度","2"),
    JUL(7, "07","7月","第三季度","3"),
    AUG(8,"08","8月" ,"第三季度","3"),
    SEPT(9,"09","9月" ,"第三季度","3"),
    OCT(10, "10","10月","第四季度","4"),
    NOV(11, "11","11月","第四季度","4"),
    DEC(12,"12", "12月","第四季度","4")
    ;
    MonthQuarterEnum(Integer value,String mCode, String mDesc,String qDesc,String qCode) {
        this.value = value;
        this.mCode = mCode;
        this.mDesc = mDesc;
        this.qDesc = qDesc;
        this.qCode = qCode;
    }

    private Integer value;
    private String mDesc;
    private String qDesc;
    private String mCode;
    private String qCode;

    public Integer getValue() {
        return value;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getqDesc() {
        return qDesc;
    }

    public String getmCode() {
        return mCode;
    }

    public String getqCode() {
        return qCode;
    }

    public static String parseMdesc(Integer value){
        for (MonthQuarterEnum m : MonthQuarterEnum.values()) {
            if (m.getValue().equals(value)) {
                return m.getmDesc();
            }
        }
        return null;
    }
    public static String parseQdesc(Integer value){
        for (MonthQuarterEnum m : MonthQuarterEnum.values()) {
            if (m.getValue().equals(value)) {
                return m.getqDesc();
            }
        }
        return null;
    }
    public static Integer parseValueByMdesc(String mdesc){
        for (MonthQuarterEnum m : MonthQuarterEnum.values()) {
            if (m.getmDesc().equals(mdesc)) {
                return m.getValue();
            }
        }
        return null;
    }
    public static String parseQdesc(String mCode){
        for (MonthQuarterEnum m : MonthQuarterEnum.values()) {
            if (m.getmCode().equals(mCode)) {
                return m.getqCode();
            }
        }
        return null;
    }
}
