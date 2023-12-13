package com.hhwy.log.utils.idworker;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.utils.idworker.IdWorkerService;

/**
 * @ClassName : IdWorker
 * @Description : 分布式自增长ID
 * 0 --- 0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---00000 ---000000000000
 * 第一个符号位，默认0；
 * 中间41位标识时间偏移量，毫秒级时间；
 * 5位机房id，范围0-31；
 * 5位机房内服务器id,范围0-31；
 * 最后12位表示当前1毫秒内生成id的计数器；
 * @Author : zxb
 * @Date : 2021-01-09 10:59
 * @Version : V1.0
 **/
public class IdWorker {

    public static IdWorkerService idWorkerService = null;
    static {
        idWorkerService = SpringUtils.getBean(IdWorkerService.class);
    }

    public static Long createId(){
        return idWorkerService.nextId();
    }

    public static void main(String[] args) {
        System.out.println(com.hhwy.utils.idworker.IdWorker.createId());
    }
}