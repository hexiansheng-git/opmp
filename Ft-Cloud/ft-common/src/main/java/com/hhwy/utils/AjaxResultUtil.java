package com.hhwy.utils;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @author m
 */
public class AjaxResultUtil extends JSONObject {

    /**
     * 获取集合
     *
     * @param ajaxResult
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getDataList(AjaxResult ajaxResult, Class<T> clazz) {
        return parseArray(getDataStr(ajaxResult), clazz);
    }

    /**
     * 获取对象
     *
     * @param ajaxResult
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getData(AjaxResult ajaxResult, Class<T> clazz) {
        return parseObject(getDataStr(ajaxResult), clazz);
    }

    /**
     * 获取List<Map>
     *
     * @param ajaxResult
     * @return
     */
    public static List<JSONObject> getDataList(AjaxResult ajaxResult) {
        return parseArray(getDataStr(ajaxResult), JSONObject.class);
    }

    /**
     * 获取Map
     *
     * @param ajaxResult
     * @return
     */
    public static JSONObject getData(AjaxResult ajaxResult) {
        return parseObject(getDataStr(ajaxResult));
    }

    private static String getDataStr(AjaxResult ajaxResult) {
        if (AjaxResult.isSuccess(ajaxResult)) {
            return JSONObject.toJSONString(ajaxResult.get(AjaxResult.DATA_TAG));
        } else {
            throw new CustomException("" + ajaxResult.get(AjaxResult.MSG_TAG));
        }
    }
}
