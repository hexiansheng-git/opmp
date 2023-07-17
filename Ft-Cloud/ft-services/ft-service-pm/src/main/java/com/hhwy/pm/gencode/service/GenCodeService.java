package com.hhwy.pm.gencode.service;

import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.gencode.domain.GenCode;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.mapper.GenCodeMapper;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 生成代码实体类
 *
 * @author Administrator
 */
@Service
public class GenCodeService {

    /**
     * 锁
     */
    private final static Object lock = new Object();

    @Resource
    private GenCodeMapper codeMapper;


    /**
     * 批量获取单据编码 (要入库时使用)
     *
     * @param num 生成多少个编码
     * @return
     */
    public List<String> getSetCodeList(CodeEnum codeEnum, int num) {
        return genCode(codeEnum.prefix(), codeEnum.timeFormat(), codeEnum.digit(), num, true);
    }


    /**
     * 获取一个单据编码 (要入库时使用)
     *
     * @return
     */
    public String getSetCode(CodeEnum codeEnum) {
        return genCode(codeEnum.prefix(), codeEnum.timeFormat(), codeEnum.digit(), 1, true).get(0);
    }

    /**
     * 发运退税台账模块规则 001-2022
     * 获取一个单据编码 (要入库时使用)
     *
     * @return
     */
    public String getSetCodeDispatch(CodeEnum codeEnum) {
        return genCodeDispatch(codeEnum.prefix(), codeEnum.timeFormat(), codeEnum.digit(), 1, true).get(0);
    }

    /**
     * 获取一个单据编码
     * (新增回显时使用, 数据并不会入库, 这里并没有考虑多个用户同时进入新增页面的回显问题,
     * 也就是说, 两个用户在同一时间段进入同一个新增页面, 显示的编码会相同, 但是保存的时候晚保存的用户的编码的流水号会+1)
     *
     * @return
     */
    public String getCode(CodeEnum codeEnum) {
        return genCode(codeEnum.prefix(), codeEnum.timeFormat(), codeEnum.digit(), 1, false).get(0);
    }


    /**
     * 生成code
     *
     * @param prefix
     * @param format
     * @param digit
     * @param num
     * @return
     */
    public List<String> genCode(String prefix, String format, int digit, int num, boolean submitFlag) {
        List<String> resList = new ArrayList<>(num);
        String userId = String.valueOf(SecurityUtils.getUserId());
        Date date = new Date();
        GenCode genCode = new GenCode();
        String middle = "";
        if (StringUtils.isNotBlank(format)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            middle = simpleDateFormat.format(date);
        }
        genCode.setMiddle(middle);
        genCode.setPrefix(prefix);
        // genCode.setCreateDate(date);
        int count = 0;

        try {
            //加锁 防止多个用户同时提交
            if (RedissonLockUtil.lock(prefix)) {
                //        synchronized (lock) {
                // 根据前缀获取当天共生成了几次编码
                List<GenCode> genCodes = this.codeMapper.selectGenCodeList(genCode);

                // 判断之前是否有生成code
                if (CollectionUtils.isEmpty(genCodes)) {
                    // 如果数据为空 新增一条数据 为0
                    GenCode iData = new GenCode();
                    iData.setId(IdWorker.createId());
                    iData.setPrefix(prefix);
                    iData.setMiddle(middle);
                    // 如果是需要提交 计数更改
                    iData.setCount(submitFlag ? count + num : count);
                    iData.setCreateTime(date);
                    iData.setCreateBy(userId);
                    iData.setUpdateTime(date);
                    iData.setUpdateBy(userId);
                    this.codeMapper.insertGenCode(iData);
                } else {
                    // 如果数据不为空 更新
                    GenCode code = genCodes.get(0);
                    count = code.getCount();
                    // 如果是要提交
                    if (submitFlag) {
                        code.setCount(count + num);
                        code.setUpdateTime(date);
                        this.codeMapper.updateGenCode(code);
                    }
                }
            }
        } finally {
            RedissonLockUtil.unlock(prefix);
        }

        // 拿到count开始生成数据
        for (int i = 1; i <= num; i++) {
            resList.add(prefix + middle + fillString(count + i, digit));
        }


        return resList;
    }

    /**
     * 发运台账生成规则 002-2022
     * 生成code
     *
     * @param prefix
     * @param format
     * @param digit
     * @param num
     * @return
     */
    public List<String> genCodeDispatch(String prefix, String format, int digit, int num, boolean submitFlag) {
        List<String> resList = new ArrayList<>(num);
        String userId = String.valueOf(SecurityUtils.getUserId());

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String middle = simpleDateFormat.format(date);
        GenCode genCode = new GenCode();
        genCode.setPrefix(prefix);
        genCode.setMiddle(middle);
        // genCode.setCreateDate(date);
        int count = 0;

        try {
            //加锁 防止多个用户同时提交
            if (RedissonLockUtil.lock(prefix)) {
                //        synchronized (lock) {
                // 根据前缀获取当天共生成了几次编码
                List<GenCode> genCodes = this.codeMapper.selectGenCodeList(genCode);

                // 判断之前是否有生成code
                if (CollectionUtils.isEmpty(genCodes)) {
                    // 如果数据为空 新增一条数据 为0
                    GenCode iData = new GenCode();
                    iData.setId(IdWorker.createId());
                    iData.setPrefix(prefix);
                    iData.setMiddle(middle);
                    // 如果是需要提交 计数更改
                    iData.setCount(submitFlag ? count + num : count);
                    iData.setCreateTime(date);
                    iData.setCreateBy(userId);
                    iData.setUpdateTime(date);
                    iData.setUpdateBy(userId);
                    this.codeMapper.insertGenCode(iData);

                } else {
                    // 如果数据不为空 更新
                    GenCode code = genCodes.get(0);
                    count = code.getCount();
                    // 如果是要提交
                    if (submitFlag) {
                        code.setCount(count + num);
                        code.setUpdateTime(date);
                        this.codeMapper.updateGenCode(code);
                    }
                }
            }
        } finally {
            RedissonLockUtil.unlock(prefix);
        }

        // 拿到count开始生成数据
        for (int i = 1; i <= num; i++) {
            resList.add(fillString(count + i, digit) + "-" + middle);
        }


        return resList;
    }


    /**
     * 左补齐0 例如 0006
     *
     * @param num   数字
     * @param digit 位数
     * @return
     */
    public String fillString(int num, int digit) {
        /**
         * 0：表示前面补0
         * digit：表示保留数字位数
         * d：表示参数为正数类型
         */
        return String.format("%0" + digit + "d", num);
    }


    /**
     * 替换后几位的字符串
     *
     * @param oldCode
     * @param nVersionCode
     * @param digit
     * @return
     */
    public String getNewCode(String oldCode, int nVersionCode, int digit) {
        String nCode= fillString(nVersionCode, 2);
        StringBuilder stringBuffer = new StringBuilder(oldCode);
        return stringBuffer.replace(stringBuffer.length() - digit, stringBuffer.length(), nCode).toString();
    }


    /**
     * 替换后两位的字符串
     *
     * @param oldCode
     * @param nVersionCode
     * @return
     */
    public String getNewCode(String oldCode, int nVersionCode) {
        return getNewCode(oldCode, nVersionCode, 2);
    }

}
