package com.hhwy.pm.qqch.common.domain;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.tree.TreeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 编制通用实体类
 *
 * @author m
 */
@Data
@ToString
@NoArgsConstructor
public class CompileDTO<T> {
    /**
     * 版本号
     */
    private BigDecimal version;
    private String submitFlag;
    private T dto;


    public static <T> T dealListDto(BigDecimal version, String submitFlag, T dto) {
        CompileDTO<T> tCompileDTO = new CompileDTO<>();
        tCompileDTO.setVersion(version);
        tCompileDTO.setDto(dto);
        return tCompileDTO.dealListDto();
    }


    public T dealListDto() {
        if (dto instanceof CompileEntity) {
            CompileEntity compileEntity = (CompileEntity) dto;
            compileEntity.setVersion(version == null ? new BigDecimal(InitVersionConstant.INIT_VERSION) : version);
            return (T) compileEntity;
        }
        return dto;
    }


    public static <T> T dealSaveDto(BigDecimal version, String submitFlag, T dto) {
        CompileDTO<T> tCompileDTO = new CompileDTO<>();
        tCompileDTO.setVersion(version);
        tCompileDTO.setSubmitFlag(submitFlag);
        tCompileDTO.setDto(dto);
        return tCompileDTO.dealSaveDto();
    }

    public T dealSaveDto() {
        if (dto instanceof CompileEntity) {
            CompileEntity compileEntity = (CompileEntity) dto;
            EntityUtils.setCreateUpdateInfo(compileEntity);
            compileEntity.setSubmitFlag(submitFlag);
            compileEntity.setVersion(version == null ? new BigDecimal(InitVersionConstant.INIT_VERSION) : version);
            if (StringUtils.isNotEmpty(submitFlag)) setValid(compileEntity);
            return (T) compileEntity;
        }


        List list;
        if (dto != null && dto instanceof List && (list = (List) dto).size() > 0 && list.get(0) instanceof CompileEntity) {
            List<CompileEntity> compileEntities = (List<CompileEntity>) dto;
            // 不用管是不是树形结构  就先转一下
            compileEntities = TreeUtil.treeToList(compileEntities);
            EntityUtils.setCreateUpdateInfo(compileEntities);
            for (CompileEntity o : compileEntities) {
                o.setSubmitFlag(submitFlag);
                o.setVersion(version);
                o.setVersion(version == null ? new BigDecimal(InitVersionConstant.INIT_VERSION) : version);
                if (StringUtils.isNotEmpty(submitFlag)) setValid(o);
            }
            return (T) compileEntities;
        }
        return dto;
    }


    public static void setValid(CompileEntity entity) {
        String submitFlag = entity.getSubmitFlag();
        if (StringUtils.isEmpty(submitFlag)) throw new RuntimeException("提交状态不能为空");

        switch (submitFlag) {
            case "0":
                //业务保存
                entity.setValid(entity.getVersion().compareTo(BigDecimal.ONE) == 0 ? "1" : "0");
                break;
            case "1":
                // 业务确认
                entity.setValid("1");
                break;
            case "2":
                // 提交
                entity.setValid("0");
                break;
            default:
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "标识不符合规范");
        }
    }

}
