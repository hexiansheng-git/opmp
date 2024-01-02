package com.hhwy.utils.validation;

import com.hhwy.constant.CommonYesNo;
import com.hhwy.utils.exception.CustomBusinessException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

/**
 * 子表校验
 *
 * @author hwj
 */
@Slf4j
public class JyDetailsUtil {

    /**
     * 单层子表校验
     *
     * @param detailList 子表对象list
     * @param groups     class对象
     */
    public static <T> Boolean jyDetails(List<T> detailList, Class<?>... groups) {
        StringBuilder str = new StringBuilder();
        for (T t : detailList) {
            jy(str, t, groups);
        }
        if (!"".contentEquals(str)) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, str.toString());
        }
        return true;
    }

    /**
     * 校验数据
     * @param str
     * @param t
     * @param groups
     * @param <T>
     */
    private static <T> void jy(StringBuilder str, T t, Class<?>[] groups) {
        BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(t, groups);
        if (!beanValidationResult.isSuccess()) {
            List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
            for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                String message = errorMessage.getMessage();
                if(str.indexOf(message) == -1){
                    if(str.length() > 0){
                        str.append(",");
                    }
                    str.append(message);
                }
            }
        }
    }

    /**
     * 双层子表校验
     *
     * @param detailList 子表对象list
     * @param key        第二层子表集合get方法
     * @param groups     class对象
     */
    public static <T, E> Boolean jyDetailsDetails(List<T> detailList, String key, Class<?>... groups) {
        StringBuilder str = new StringBuilder();
        StringBuffer strDetails = new StringBuffer();
        for (T t : detailList) {
            BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(t, groups);
            if (!beanValidationResult.isSuccess()) {
                List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
                for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                    if(str.indexOf(errorMessage.getMessage()) == -1){
                        str.append(errorMessage.getMessage()).append(",");
                    }
                }
            }

            // 一般而言每个属性都有其get和set方法
            // 通过方法名获取get方法
            Method getMethod = null;
            try {
                getMethod = t.getClass().getMethod(key);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            // 调用get方法
            List<E> detailsDetailsList = null;
            try {
                detailsDetailsList = (List<E>) getMethod.invoke(t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            if (detailsDetailsList != null) {
                for (E e : detailsDetailsList) {
                    BeanValidationResult beanValidationResultDetails = ValidationUtil.warpValidate(e, groups);
                    if (!beanValidationResultDetails.isSuccess()) {
                        List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResultDetails
                            .getErrorMessages();
                        for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                            strDetails = strDetails.append(errorMessage.getMessage() + ",");
                        }
                    }
                }
            }
        }

        if (!"".equals(str.toString())) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, str.toString());
        }
        if (!"".equals(strDetails.toString())) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, strDetails.toString());
        }
        return true;
    }

    /**
     * 树列表平铺后只校验叶子节点数据
     *
     * @param detailList
     * @param getLeaf    如何获取叶子节点
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> Boolean jyDetails(List<T> detailList, Function<T, String> getLeaf, Class<?>... groups) {
        StringBuilder str = new StringBuilder();
        for (T t : detailList) {
            String leaf = getLeaf.apply(t);
            if (CommonYesNo.YES.equals(leaf)) {
                jy(str, t, groups);
            }
        }
        if (!"".contentEquals(str)) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, str.toString());
        }
        return true;
    }

    /**
     * 树列表平铺后不校验根节点
     *
     * @param detailList
     * @param getPid     如何获取根节点
     * @param groups
     * @param <T>
     */
    public static <T> void jyExceptRootDetails(List<T> detailList, Function<T, Long> getPid, Class<?>... groups) {
        StringBuilder str = new StringBuilder();
        for (T t : detailList) {
            Long pid = getPid.apply(t);
            if (pid != null) {
                jy(str, t, groups);
            }
        }
        if (!"".contentEquals(str)) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, str.toString());
        }
    }

    /**
     * 树列表平铺后只校验根节点数据
     *
     * @param detailList
     * @param getPid     如何获取根节点
     * @param groups
     * @param <T>
     */
    public static <T> void jyRoot(List<T> detailList, Function<T, Long> getPid, Class<?>... groups) {
        StringBuilder str = new StringBuilder();
        for (T t : detailList) {
            Long pid = getPid.apply(t);
            if (pid == null) {
                jy(str, t, groups);
            }
        }
        if (!"".contentEquals(str)) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, str.toString());
        }
    }
}
