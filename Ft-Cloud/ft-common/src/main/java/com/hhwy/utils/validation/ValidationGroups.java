package com.hhwy.utils.validation;

public class ValidationGroups {

    //根据id获取一条数据，使用的校验组
    public interface Get {};
    //新增保存时，使用的校验组
    public interface Save {};
    //更新数据时，使用的校验组
    public interface Update {};
    //查询时，使用的校验组
    public interface Select {};
    //删除时，使用的校验组
    public interface Delete {};

    public interface Other{};

    public interface Type1{};
    public interface Type2{};
    public interface Type3{};
    public interface Type4{};
    
}
