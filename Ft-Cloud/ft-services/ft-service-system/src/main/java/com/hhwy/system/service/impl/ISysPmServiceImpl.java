package com.hhwy.system.service.impl;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.system.api.domain.*;
import com.hhwy.system.core.domain.SysDictType;
import com.hhwy.system.core.service.ISysDictDataService;
import com.hhwy.system.core.service.ISysDictTypeService;
import com.hhwy.system.core.service.ISysTenantService;
import com.hhwy.system.mapper.SysPmMapper;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.service.ISysPmService;
import com.hhwy.system.service.IUserService;
import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;

@Service
public class ISysPmServiceImpl implements ISysPmService {

    @Autowired
    SysPmMapper sysPmMapper;
    @Resource
    private ISysDictTypeService sysDictTypeService;
    @Resource
    private ISysDictDataService sysDictDataService;


    @Autowired
    private ISysTenantService tenantService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Override
    public List<SysDictData> selectDictValueByTypeAndLabel(String dictType, String dictLabel) {
        return sysPmMapper.selectDictValueByTypeAndLabel(dictType,dictLabel);
    }

    /**
     * 导入字典
     * 模板:一级代码	一级名称 	二级代码 	二级名称 	三级编码	三级名称	四级编码	四级名称  (备注1-4)
     * @param file
     * @return
     */
    public String importDict(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String dictType = fileName.split("-")[0];
        String dictName = StringUtils.substringBefore(fileName.split("-")[1],".");
        int beginRowIndex = 1;  //开始检索的行索引
        int nowSheetNum = 0;    //sheet索引
        int nowRowNum = beginRowIndex;      //行索引
        Long beginMills = System.currentTimeMillis();
        Date now = new Date();
        Function<Cell,String> getForceStringValueFilterZero = cell->{
            if(cell == null)
                return null;
            cell.setCellType(CellType.STRING);
            String val = cell.getStringCellValue();
            return val==null?null:val.toString();
        };
        Function<Object,String> toString = obj->obj==null?"":obj.toString();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Map<String, SysDictData> dictDataMap = new HashMap<>();
            Map<String,Integer> dictSortMap = new HashMap<>();
            //遍历sheet页
            for(;nowSheetNum<workbook.getNumberOfSheets();nowSheetNum++){
                Sheet sheet = workbook.getSheetAt(nowSheetNum);
                //遍历sheet的行
                for(;nowRowNum<sheet.getPhysicalNumberOfRows();nowRowNum++){
                    Row row = sheet.getRow(nowRowNum);
                    if(row == null){   //停止寻找当前sheet
                        nowRowNum = beginRowIndex;
                        break;
                    }
                    String code = getForceStringValueFilterZero.apply(row.getCell(0));
                    String name = toString.apply(row.getCell(1));
                    String scode = getForceStringValueFilterZero.apply(row.getCell(2));
                    String sname = toString.apply(row.getCell(3));
                    String tcode = getForceStringValueFilterZero.apply(row.getCell(4));
                    String tname = toString.apply(row.getCell(5));
                    String fcode = getForceStringValueFilterZero.apply(row.getCell(6));
                    String fname = toString.apply(row.getCell(7));
                    //备注开始 4个备注字段
                    String remark1 = toString.apply(row.getCell(8));
                    String remark2 = toString.apply(row.getCell(9));
                    String remark3 = toString.apply(row.getCell(10));
                    String remark4 = toString.apply(row.getCell(11));
                    String[] codes = new String[]{code,scode,tcode,fcode};
                    String[] names = new String[]{name,sname,tname,fname};
                    String[] remarks = new String[]{remark1,remark2,remark3,remark4};
                    //若每个编码都为空，停止解析
                    boolean hasValue = false;
                    for (int i = 0; i < codes.length; i++) {
                        if(StringUtils.isNotBlank(codes[i])){
                            hasValue = true;
                            break;
                        }
                    }
                    if(!hasValue)
                        break;
                    for (int i = codes.length-1; i >= 0; i--) {
                        if(StringUtils.isBlank(codes[i]) || "—".equals("codes[i]"))
                            continue;
                        parse(codes,names,remarks,i,dictDataMap,dictSortMap,dictType,dictName);
                    }
                }
            }
            SysDictType dictTypeObj = new SysDictType();
            dictTypeObj.setDictType(dictType);
            dictTypeObj.setDictName(dictName);
            dictTypeObj.setStatus("0");
            sysDictTypeService.insertDictType(dictTypeObj);
            nowSheetNum = -1;
        }catch (RuntimeException e){
            e.printStackTrace();
            if(nowSheetNum != -1)
                throw new RuntimeException( StringFormatter.format("第%sSheet,第%s行,%s",nowSheetNum+1,nowRowNum+1,e.getMessage()).getValue());
            throw new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();
            if(nowSheetNum != -1){
                throw new RuntimeException(StringFormatter.format("第%sSheet,第%s行,%s",nowSheetNum+1,nowRowNum+1,e.getMessage()).getValue());
            }
            throw new RuntimeException(e);
        }finally {
            String msg = "字典导入完成，耗时"+(System.currentTimeMillis()-beginMills);
            System.out.println(msg);
            System.gc();
        }
        return null;
    }

    @Override
    public List<SysMenu> testTenant(String s) {
        Map projectBasicInfo = JSON.parseObject(s, Map.class);
        String projectName = (String) projectBasicInfo.get("projectName");
        String projectCode = (String) projectBasicInfo.get("projectCode");
        String projectId = (String) projectBasicInfo.get("projectId");
        Map params = (Map) projectBasicInfo.get("params");

        SysTenant sysTenant = new SysTenant();

        sysTenant.setParams(projectBasicInfo);
        sysTenant.setTenantName(projectName);
        sysTenant.setTenantKey(projectCode);
        sysTenant.setTenantStatus("0");
        sysTenant.setValidityStatus("1");
        sysTenant.setAdministratorNickName("系统管理员");
        sysTenant.setAdministratorUserName("admin");
        sysTenant.setAdministratorPassword(SecurityUtils.encryptRSAPassword("admin123"));
        sysTenant.setMenuStatus("1");
        sysTenant.setRoleStatus("1");
        sysTenant.setPostStatus("1");
        sysTenant.setDeptStatus("0");
        sysTenant.setUserStatus("0");

        SysDept dept=deptService.selectDeptIdByprojectId(projectId);
        Long deptId = dept.getDeptId();
        String ancestors = dept.getAncestors();
        List<SysDept> deptList = deptService.selectAllDept(deptId,ancestors);
        sysTenant.setDeptList(deptList);

        List<SysUser> userList=userService.selectAllUser(deptList);

        String roleUserStr=JSON.toJSONString(params.get("roleUserList"));
        List<SysUser>  roleUserList = JSON.parseArray(roleUserStr,SysUser.class);

        String partUserStr=JSON.toJSONString(params.get("partUserList"));
        List<SysUser>  partUserList = JSON.parseArray(partUserStr,SysUser.class);


        List<SysUser> idList = this.handUserInfo(userList, roleUserList, partUserList);

        sysTenant.setUserList(idList);

        this.tenantService.insertSysTenant(sysTenant);
        return  null;
    }

    public SysDictData parse(String[] codes, String[] names, String[] remarks, int i, Map<String,SysDictData> dictDataMap, Map<String,Integer> dictSortMap
            , String dictType, String dictName){
        if(StringUtils.isBlank(codes[i]) || "—".equals("codes[i]"))
            return null;
        String nowName = getParentName(names, i);
        if(dictDataMap.get(nowName) != null){
            return null;
        }
        boolean isMaster = i==0;
        SysDictData dictData = new SysDictData();
        dictData.setDictValue(codes[i]);
        dictData.setDictLabel(names[i]);
        dictData.setRemark(remarks[i]);
        dictData.setDictType(dictType);
        String parentName = isMaster?"M":getParentName(names,i-1);
        Integer sort = dictSortMap.get(parentName);
        if(sort == null){
            sort=0;
        }
        dictSortMap.put(parentName,++sort);
        dictData.setDictSort(Long.valueOf(sort+"")); //排序
        dictData.setDictType(dictType);
        //父级
        if(dictDataMap.get(parentName) == null && i > 0){
            SysDictData parentDict = parse(codes,names,remarks,i-1,dictDataMap,dictSortMap,dictType,dictName);
            dictData.setParentId(parentDict.getDictDataId());
        }else if(dictDataMap.get(parentName) != null){
            dictData.setParentId(dictDataMap.get(parentName).getDictDataId());
        }
        dictData.setCreateTime(DateUtils.addSeconds(new Date(), dictDataMap.size()*2));
        sysDictDataService.insertDictData(dictData);
        dictDataMap.put(nowName, dictData);
        return dictData;
    }
    public String getParentName(String[] names,int j){
        String name="";
        for (int i=0;  i<=j; i++) {
            name+=names[i]+"-";
        }
        return name;
    }


    public List<SysUser> handUserInfo(List<SysUser> userList,List<SysUser> roleUserList,List<SysUser> partUserList) {
        List<SysUser> list=new ArrayList<>();
        Map<Long, Object> idmap = new HashMap<>();
        for(SysUser item:userList){
            if(!idmap.containsKey(item.getUserId()) &&  item.getUserId()!=1){
                list.add(item);
            }
        }
        for(SysUser item:roleUserList){
            if(!idmap.containsKey(item.getUserId()) &&  item.getUserId()!=1){
                list.add(item);
            }
        }
        for(SysUser item:partUserList){
            if(!idmap.containsKey(item.getUserId()) &&  item.getUserId()!=1){
                list.add(item);
            }
        }
        return  list;
    }

}
