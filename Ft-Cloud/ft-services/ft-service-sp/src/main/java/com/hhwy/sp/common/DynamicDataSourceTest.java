package com.hhwy.sp.common;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsBuildScheme;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.ISgjsBuildSchemeService;
import com.hhwy.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sgjsBuildScheme1")
public class DynamicDataSourceTest {


    @Autowired
    private ISgjsBuildSchemeService sgjsBuildSchemeService;

    @GetMapping("/testDynamicDataSource")
    public void test() {
        List<SgjsBuildScheme> sgjsBuildScheme11 = sgjsBuildSchemeService.getSgjsBuildSchemeList1(new SgjsBuildScheme());
        log.info("第一次：{}--数据源头：{}", sgjsBuildScheme11.size(), DynamicDataSourceContextHolder.peek());
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        String master = DynamicDataSourceContextHolder.push("master");
        List<SgjsBuildScheme> sgjsBuildScheme = sgjsBuildSchemeService.getSgjsBuildSchemeList1(new SgjsBuildScheme());
//        log.info("第一次：{}", JSON.toJSONString(sgjsBuildScheme));
        log.info("切换master后：{}--数据源头：{}", sgjsBuildScheme.size(),  DynamicDataSourceContextHolder.peek());
        ThreadPoolUtil.execute(() -> {
            String oldDataSource44 = DynamicDataSourceContextHolder.peek();
            List<SgjsBuildScheme> sgjsBuildScheme1111 = sgjsBuildSchemeService.getSgjsBuildSchemeList1(new SgjsBuildScheme());
            log.info("子线程第一次：{}--数据源头：{}", sgjsBuildScheme1111.size(), oldDataSource44);
            String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey("PJ2023015174");
            DynamicDataSourceContextHolder.push(dataSource);
            List<SgjsBuildScheme> sgjsBuildScheme1 = sgjsBuildSchemeService.getSgjsBuildSchemeList1(new SgjsBuildScheme());
//            log.info("子线程：{}", JSON.toJSONString(sgjsBuildScheme1));
            log.info("子线程内切换大圆厅：{}--数据源头：{}", sgjsBuildScheme1.size(), DynamicDataSourceContextHolder.peek());
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource44);
            List<SgjsBuildScheme> sgjsBuildScheme111 = sgjsBuildSchemeService.getSgjsBuildSchemeList1(new SgjsBuildScheme());
            log.info("子线程恢复后：{}--数据源头：{}", sgjsBuildScheme111.size(), DynamicDataSourceContextHolder.peek());
            DynamicDataSourceContextHolder.peek();
        });
        List<SgjsBuildScheme> sgjsBuildScheme2 = sgjsBuildSchemeService.getSgjsBuildSchemeList1(new SgjsBuildScheme());
//        log.info("第二次：{}", JSON.toJSONString(sgjsBuildScheme2));
        log.info("主线程恢复之前：{}--数据源头：{}", sgjsBuildScheme2.size(), DynamicDataSourceContextHolder.peek());
        DynamicDataSourceContextHolder.poll();
        DynamicDataSourceContextHolder.push(oldDataSource);
        List<SgjsBuildScheme> sgjsBuildScheme3 = sgjsBuildSchemeService.getSgjsBuildSchemeList1(new SgjsBuildScheme());
        log.info("主线程恢复之后：{}--数据源头：{}", sgjsBuildScheme3.size(), DynamicDataSourceContextHolder.peek());
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        List<SgjsBuildScheme> sgjsBuildScheme32 = sgjsBuildSchemeService.getSgjsBuildSchemeList1(new SgjsBuildScheme());
//        log.info("主线程恢复之后,睡眠3s：{}--数据源头：{}", sgjsBuildScheme32.size(), DynamicDataSourceContextHolder.peek());
    }
}
