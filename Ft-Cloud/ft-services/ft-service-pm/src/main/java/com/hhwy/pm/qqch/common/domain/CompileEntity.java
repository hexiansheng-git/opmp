package com.hhwy.pm.qqch.common.domain;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 编制通用实体类
 *
 * @author m
 */
@Data
@ToString
public class CompileEntity<T> extends TreeNode<T> {

    /**
     * 版本号
     */
    @NotNull(message = "版本不能为空！", groups = ValidationGroups.Save.class)
    private BigDecimal version;
    /**
     * 有效状态
     */
    private String valid;

    /**
     * 字段描述：按钮标识（0：保存，1：确认，2：提交）
     */
    @NotBlank(message = "按钮标识不能为空！", groups = ValidationGroups.Save.class)
    private String submitFlag;

    /**
     * 阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    @NotBlank(message = "阶段标识不能为空！", groups = ValidationGroups.Save.class)
    private String stageIdentity;

    @NotBlank(message = "模块唯一标识不能为空！", groups = ValidationGroups.Save.class)
    private String moduleIdentity;

    private T dto;

    
    
    
    public static <T> T dealListDto(BigDecimal version, T dto) {
        CompileEntity<T> tCompileDTO = new CompileEntity<>();
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


    public static <T> T dealSaveDto(BigDecimal version, String submitFlag, String menuId, T dto) {
        CompileEntity<T> tCompileDTO = new CompileEntity<>();
        tCompileDTO.setVersion(version);
        tCompileDTO.setSubmitFlag(submitFlag);
        tCompileDTO.setDto(dto);
        tCompileDTO.setModuleIdentity(menuId);
        return tCompileDTO.dealSaveDto();
    }

    public T dealSaveDto() {
        if (dto instanceof CompileEntity) {
            CompileEntity compileEntity = (CompileEntity) dto;
            EntityUtils.setCreateUpdateInfo(compileEntity);
            compileEntity.setSubmitFlag(submitFlag);
            compileEntity.setVersion(version == null ? new BigDecimal(InitVersionConstant.INIT_VERSION) : version);
            compileEntity.setModuleIdentity(this.moduleIdentity);
            if (StringUtils.isNotEmpty(submitFlag)) setValidStatus(compileEntity);
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
                o.setModuleIdentity(moduleIdentity);
                o.setVersion(version == null ? new BigDecimal(InitVersionConstant.INIT_VERSION) : version);
                if (StringUtils.isNotEmpty(submitFlag)) setValidStatus(o);
            }
            return (T) compileEntities;
        }


        // 如果当前保存的集合是空集合 就返回一个带有Version的数据 方便删除数据
        if ((dto != null && dto instanceof List && (list = (List) dto).size() == 0)) {
            CompileEntity entity = new CompileEntity<>();
            entity.setVersion(this.version);
            entity.setSubmitFlag(PmConstant.MINUS_ONE);
            list.add(entity);
            return (T) list;
        }
        return dto;
    }


    public static void setValidStatus(CompileEntity entity) {
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
