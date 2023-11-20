package com.hhwy.pm.xmsl.wbs;


import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.TreeUtils;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.utils.MaterialUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class test {

    public static void main(String[] args) throws Exception {
        BigDecimal num =  new BigDecimal("2.00");
        System.out.println(num);
        System.out.println(num.setScale(1));

//        QqchChangeDetail t = new QqchChangeDetail();
//        QqchChangeDetail t1 = new QqchChangeDetail();
//        QqchChangeDetail t2 = new QqchChangeDetail();
//        t.setChildren(Arrays.asList(t1,t2));
//        t.setId(1L);
//        t1.setId(11L);
//        t2.setId(12L);
//
//        QqchChangeDetail s = new QqchChangeDetail();
//        QqchChangeDetail s1 = new QqchChangeDetail();
//        QqchChangeDetail s11 = new QqchChangeDetail();
//        s.setChildren(Arrays.asList(s1));
//        s1.setChildren(Arrays.asList(s11));
//        s.setId(2L);
//        s1.setId(22L);
//        s11.setId(222L);
//
//        List<QqchChangeDetail> list = Arrays.asList(t,s);
//        List<QqchChangeDetail> list1 = new ArrayList<>();
//         menuTree2List(list,list1);
//        list1.stream().forEach(r-> System.out.println(r.getId()));


    }

    private static void menuTree2List(List<QqchChangeDetail> list, List<QqchChangeDetail> resuList){
        if(CollectionUtils.isEmpty(list))
            return;
        for (int i = 0; i < list.size(); i++) {
            QqchChangeDetail temp = list.get(i);
            resuList.add(temp);
            menuTree2List(temp.getChildren(),resuList);
        }
    }
}
