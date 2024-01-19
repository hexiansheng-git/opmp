package com.hhwy.pm.qqch.common.domain;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.UUIDUtils;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
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

    private static RedisUtils redisUtils;

    static {
        try {
            redisUtils = SpringUtils.getBean(RedisUtils.class);
        } catch (Exception e) {
        }
    }

    /**
     * 版本号
     */
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

    //真模块唯一标识（前端路由地址） 因为前端都是将路由地址传到menuId
    private String menuId;


    private String dataType;

    /**
     * 请求id
     */
    private String reqId;
    /**
     * 年份
     */
    private String yearStr;

    private T dto;


    // 每次实例化的时候  就进行初始化请求id
    {
        reqId = reqId == null ? UUIDUtils.getUuid() : reqId;
    }


    public static <T> T dealListDto(BigDecimal version, T dto) {
        CompileEntity<T> tCompileDTO = new CompileEntity<>();
        tCompileDTO.setVersion(version);
        tCompileDTO.setDto(dto);
        return tCompileDTO.dealListDto();
    }


    public static <T> T dealSaveDto(CompileEntity param, T dto) {
        CompileEntity<T> tCompileDTO = new CompileEntity<>();
        tCompileDTO.setVersion(param.getVersion());
        tCompileDTO.setSubmitFlag(param.getSubmitFlag());
        tCompileDTO.setMenuId(param.getMenuId());
        tCompileDTO.setModuleIdentity(param.getModuleIdentity());
        tCompileDTO.setReqId(param.getReqId());
        tCompileDTO.setStageIdentity(param.getStageIdentity());
        tCompileDTO.setDataType(param.getDataType());
        tCompileDTO.setDto(dto);
        return tCompileDTO.dealSaveDto();
    }

    public static <T> T dealSaveDto(CompileEntity param, T dto,boolean treeFlag) {
        CompileEntity<T> tCompileDTO = new CompileEntity<>();
        tCompileDTO.setVersion(param.getVersion());
        tCompileDTO.setSubmitFlag(param.getSubmitFlag());
        tCompileDTO.setMenuId(param.getMenuId());
        tCompileDTO.setModuleIdentity(param.getModuleIdentity());
        tCompileDTO.setReqId(param.getReqId());
        tCompileDTO.setStageIdentity(param.getStageIdentity());
        tCompileDTO.setDataType(param.getDataType());
        tCompileDTO.setDto(dto);
        return tCompileDTO.dealSaveDto(treeFlag);
    }

    public static <T> T dealSaveDtoWithoutTree(CompileEntity param, T dto) {
        CompileEntity<T> tCompileDTO = new CompileEntity<>();
        tCompileDTO.setVersion(param.getVersion());
        tCompileDTO.setSubmitFlag(param.getSubmitFlag());
        tCompileDTO.setMenuId(param.getMenuId());
        tCompileDTO.setModuleIdentity(param.getModuleIdentity());
        tCompileDTO.setReqId(param.getReqId());
        tCompileDTO.setStageIdentity(param.getStageIdentity());
        tCompileDTO.setDataType(param.getDataType());
        tCompileDTO.setDto(dto);
        return tCompileDTO.dealSaveDto(false);
    }


    public T dealListDto() {
        if (dto instanceof CompileEntity) {
            CompileEntity compileEntity = (CompileEntity) dto;
            compileEntity.setVersion(version);
            return (T) compileEntity;
        }
        return dto;
    }

    public T dealSaveDto() {
        return dealSaveDto(true);
    }

    public T dealSaveDto(boolean tree) {
        if (StringUtils.isEmpty(reqId)) {
            reqId = UUIDUtils.getUuid();
        }
        if (dto instanceof CompileEntity) {
            CompileEntity compileEntity = (CompileEntity) dto;
            EntityUtils.setCreateUpdateInfo(compileEntity);
            setBaseInfo(compileEntity);
            return (T) compileEntity;
        }


        List list;
        if (dto != null && dto instanceof List && (list = (List) dto).size() > 0 && list.get(0) instanceof CompileEntity) {
            List<CompileEntity> compileEntities = (List<CompileEntity>) dto;
            // 不用管是不是树形结构  就先转一下
            if (tree) compileEntities = TreeUtil.treeToListWithLevel(compileEntities);
            EntityUtils.setCreateUpdateInfo(compileEntities);
            for (CompileEntity o : compileEntities) {
                setBaseInfo(o);
            }
            return (T) compileEntities;
        }


        // 如果当前保存的集合是空集合 就返回一个带有Version的数据 方便删除数据
        if ((dto != null && dto instanceof List && (list = (List) dto).size() == 0)) {
            CompileEntity entity = new CompileEntity<>();
            entity.setVersion(this.version);
            entity.setSubmitFlag(ObjectUtils.nvlString(this.submitFlag,PmConstant.MINUS_ONE));
            entity.setMenuId(this.menuId);
            entity.setStageIdentity(this.stageIdentity);
            list.add(entity);
            return (T) list;
        }
        return dto;
    }


    private void setBaseInfo(CompileEntity compileEntity) {

        compileEntity.setSubmitFlag(submitFlag);
        compileEntity.setMenuId(menuId);
        compileEntity.setModuleIdentity(moduleIdentity);
        compileEntity.setVersion(version == null ? new BigDecimal(InitVersionConstant.INIT_VERSION) : version);
        String dataType1 = compileEntity.getDataType();
        compileEntity.setDataType(StringUtils.isEmpty(dataType1) ? dataType : dataType1);
        compileEntity.setReqId(reqId);
        compileEntity.setStageIdentity(stageIdentity);
        if (StringUtils.isNotEmpty(submitFlag)) setValidStatus(compileEntity);
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
