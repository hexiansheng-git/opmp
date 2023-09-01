package com.hhwy.utils;

import cn.hutool.core.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.hhwy.common.core.web.page.TableDataInfo;

import java.util.ArrayList;
import java.util.List;
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

    /*
     * 处理List集合数据进行分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页数据个数
     * @param list        进行分页的数据
     * @param <T>
     * @return
     * @author  ldd
     * @date 2023-04-11
     */
    public static <T> List<T> getPageInfo(int currentPage, int pageSize, List<T> list) {
        List<T> newList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            int currIdx = (currentPage > 1 ? (currentPage - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < list.size() - currIdx; i++) {
                newList.add(list.get(currIdx + i));
            }
        }
        return newList;
    }

    /*
     * 处理List集合数据进行分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页数据个数
     * @param list        进行分页的数据
     * @param <T>
     * @return
     * @author  ldd
     * @date 2023-04-11
     */
    public static TableDataInfo getTableDataInfo(int currentPage, int pageSize, List list) {
        List resuList = getPageInfo(currentPage,pageSize,list);
        return new TableDataInfo(resuList, list.size());
    }
}
