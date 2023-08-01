package com.hhwy.utils.excelUtil;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HeadVo implements Comparable<HeadVo>{
    /**
     * 列头名
     */
    private List<String> headTitle;
    /**
     * 字段名
     */
    private String key;
    /**
     * 主排序
     */
    private int index;


    @Override
    public int compareTo(HeadVo o) {
        return this.index - o.getIndex();
    }
}
