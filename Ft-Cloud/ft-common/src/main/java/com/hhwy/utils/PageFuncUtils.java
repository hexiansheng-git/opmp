package com.hhwy.utils;

import cn.hutool.core.util.PageUtil;
import com.github.pagehelper.PageHelper;

import java.util.function.BiFunction;

/**
 *  分页操作工具类 
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2023/8/14 17:15   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2023/8/14 17:15    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
public class PageFuncUtils {

    /**
     * 执行
     * @param count     总数
     * @param limit     每页条目数
     * @param function  执行方法
     */
    public static void exec(int count, int limit, BiFunction<Integer,Integer,Boolean> function){
        int page = PageUtil.totalPage(count, limit);
        for (int i = 1; i <= page; i++) {
            int[] t =PageUtil.transToStartEnd(i-1,limit);
            if(t[1] >count)
                t[1] = count;
            Boolean isContinue =function.apply(t[0],t[1]);
            if(!isContinue)
                break;
        }
    }
}
