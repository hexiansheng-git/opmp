package com.hhwy.utils.excel;


import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mls
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FtExcel {
    String name() default "";

    /**
     * 小数位数
     *
     * @return
     */
    int decimalScale() default 2;

    /**
     * 是否是序号列(导入树形结构数据时使用)
     *
     * @return
     */
    boolean serialNumFlag() default false;

    /**
     * 序号根据什么分割 如果是1.2.1,此值传 '.'; 如果是1-2-1,此值传 '-'
     *
     * @return
     */
    String serialStr() default ".";

    /**
     * 子节点字段名称
     *
     * @return
     */
    String childrenFieldName() default "children";

    String dateFormat() default "";

    String readConverterExp() default "";

    String dictType() default "";

    String resolveMethod() default "";

    ColumnType cellType() default ColumnType.STRING;

    double height() default 14.0D;

    double width() default 16.0D;

    HorizontalAlignment alignment() default HorizontalAlignment.CENTER;

    String suffix() default "";

    String defaultValue() default "";

    String prompt() default "";

    String[] combo() default {};

    boolean isExport() default true;

    String targetAttr() default "";

    Type type() default Type.ALL;

    String processKey() default "";

    public static enum ColumnType {
        NUMERIC(0),
        STRING(1);

        private final int value;

        private ColumnType(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public static enum Type {
        ALL(0),
        EXPORT(1),
        IMPORT(2);

        private final int value;

        private Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
