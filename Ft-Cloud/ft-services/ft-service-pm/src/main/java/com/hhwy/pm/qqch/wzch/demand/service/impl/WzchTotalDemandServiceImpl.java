package com.hhwy.pm.qqch.wzch.demand.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandTimeCount;
import com.hhwy.pm.qqch.wzch.demand.mapper.WzchTotalDemandMapper;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandService;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandTimeCountService;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandAddVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandDetailRequest;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandExportRequest;
import com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 物资总需Service业务层处理
 *
 * @author mls
 * @date 2022-11-15
 */
@Slf4j
@Service
public class WzchTotalDemandServiceImpl implements IWzchTotalDemandService
{
    @Resource
    private WzchTotalDemandMapper wzchTotalDemandMapper;
    @Autowired
    private IWzchTotalDemandDetailService wzchTotalDemandDetailService;
    @Autowired
    private IWzchTotalDemandTimeCountService wzchTotalDemandTimeCountService;
    @Resource
    private GenCodeService genCodeService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    SystemApiService systemApiService;
    @Resource
    IQqchReviewService qqchReviewService;
    @Resource
    IWzchSourceService wzchSourceService;
//    @Resource
//    private IWzchSourceService wzchSourceService;
//    private SysDictDataMappe sysDictDataMapper;

    // 字段信息 用完之后记得释放
    private Map<String, Field> fieldMap;
    // 存放字典信息 用完之后记得释放
    private Map<String, Map<String, String>> dictListMap;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询物资总需
     *
     * @param id 物资总需ID
     * @return 物资总需
     */
    @Override
    public WzchTotalDemand selectWzchTotalDemandById(Long id) {

        return wzchTotalDemandMapper.selectWzchTotalDemandById(id);
    }

    /**
     * 查询物资总需列表
     *
     * @param wzchTotalDemand 物资总需
     * @return 物资总需
     */
    @Override
    public List<WzchTotalDemand> selectWzchTotalDemandList(WzchTotalDemand wzchTotalDemand) {
        return wzchTotalDemandMapper.selectWzchTotalDemandList(wzchTotalDemand);
    }

    /**
     * 新增物资总需
     *
     * @param wzchTotalDemand 物资总需
     * @return 结果
     */
    @Override
    public int insertWzchTotalDemand(WzchTotalDemand wzchTotalDemand) {
        return wzchTotalDemandMapper.insertWzchTotalDemand(wzchTotalDemand);
    }

    /**
     * 修改物资总需
     *
     * @param wzchTotalDemand 物资总需
     * @return 结果
     */
    @Override
    public int updateWzchTotalDemand(WzchTotalDemand wzchTotalDemand) {
        wzchTotalDemand.setUpdateTime(DateUtils.getNowDate());
        return wzchTotalDemandMapper.updateWzchTotalDemand(wzchTotalDemand);
    }

    /**
     * 删除物资总需对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchTotalDemandByIds(String[] ids) {
        return wzchTotalDemandMapper.deleteWzchTotalDemandByIds(ids);
    }

    /**
     * 删除物资总需信息
     *
     * @param id 物资总需ID
     * @return 结果
     */
    @Override
    public int deleteWzchTotalDemandById(Long id) {
        return wzchTotalDemandMapper.deleteWzchTotalDemandById(id);
    }

    /**
     * 导出物资总需信息
     *
     * @param wzchTotalDemandExportRequest
     * @param response
     * @return 结果
     */
    @Override
    public void export(WzchTotalDemandExportRequest wzchTotalDemandExportRequest, HttpServletResponse response) {


        WzchTotalDemand wzchTotalDemand = new WzchTotalDemand();
        BeanUtils.copyProperties(wzchTotalDemandExportRequest, wzchTotalDemand);
        List<WzchTotalDemand> wzchTotalDemands = wzchTotalDemandMapper.selectExportList(wzchTotalDemand);

        ZipOutputStream zipOutputStream = null;


        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String("总需求".getBytes(), StandardCharsets.ISO_8859_1) + DateUtils.dateTime() + ".zip");
            zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (int i = 0; i < wzchTotalDemands.size(); i++) {
                WzchTotalDemand totalDemand = wzchTotalDemands.get(i);
                ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                // 生成文件
                this.createWorkbook(totalDemand, byteOutputStream);
                zipOutputStream.putNextEntry(new ZipEntry(totalDemand.getProjectName() + "_" + totalDemand.getVersionCodeStr() + "_" + i + ".xlsx"));
                zipOutputStream.write(byteOutputStream.toByteArray());
                zipOutputStream.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipOutputStream != null) zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 用完释放掉
            fieldMap = null;
            dictListMap = null;
        }
    }


    private void createWorkbook(WzchTotalDemand totalDemand, OutputStream outputStream) {
        // 创建一个工作簿
        Workbook workbook = new SXSSFWorkbook();

        try {

            // 年度视角
            this.creatYearSheet(workbook, totalDemand);
            // 季度视角
            this.creatQuarterSheet(workbook, totalDemand);
            // 月度视角
            this.creatMouthSheet(workbook, totalDemand);
            
            // 写入流
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("导出异常", e);
        } finally {
            try {
                workbook.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    private void creatYearSheet(Workbook workbook, WzchTotalDemand totalDemand) {
        // 创建表
        Sheet yearSheet = workbook.createSheet();
        // 创建前两行
        this.creatTopTwoRows(workbook, yearSheet, totalDemand);

        // 拿到详情数据
        List<WzchTotalDemandDetail> wzchTotalDemandDetailList = totalDemand.getWzchTotalDemandDetailList();
        List<WzchTotalDemandTimeCount> objects = new ArrayList<>();
        for (WzchTotalDemandDetail demandDetail : wzchTotalDemandDetailList) {
            objects.addAll(demandDetail.getWzchTotalDemandTimeCountList());
        }

        // 取年份 去重 排序 转集合
        // 纯年份 不加汉字 ’年‘  用于筛选数据
        List<String> years = objects.stream().map(WzchTotalDemandTimeCount::getYear).distinct().sorted(Comparator.comparing(Long::parseLong)).collect(Collectors.toList());
        // 加上汉字 '年' 作为表头
        // List<String> yearsHeader = years.stream().map(item -> item + "年").distinct().sorted(Comparator.comparing(Long::parseLong)).collect(Collectors.toList());

        // 第三行为详情表头
        List<String> headWithYears = Arrays.stream(new String[]{"序号", "物资编码", "物资名称", "规格型号", "技术参数", "执行标准", "单位", "总需用量", "自采需用量", "非自采量", "类型", "是否优先进场"}).collect(Collectors.toList());
        headWithYears.addAll(years);
        headWithYears.addAll(Arrays.stream(new String[]{"业主合同相关技术标准要求", "资源调查", "附件"}).collect(Collectors.toList()));

        // 写入第三行
        Row row3 = yearSheet.createRow(2);
        for (int i = 0; i < headWithYears.size(); i++) {
            Cell cell = row3.createCell(i);
            cell.setCellValue(headWithYears.get(i));
        }
        wzchTotalDemandDetailList = wzchCommonService.setWzchtMaterialInfo(wzchTotalDemandDetailList);
        //写入数据
        for (int i = 0; i < wzchTotalDemandDetailList.size(); i++) {
            WzchTotalDemandDetail demandDetail = wzchTotalDemandDetailList.get(i);
            // 创建行
            Row row = yearSheet.createRow(i + 3);

            for (int j = 0; j < headWithYears.size(); j++) {
                if (j == 0) {
                    // 第一列为序号
                    Cell cell = row.createCell(0);
                    cell.setCellValue(i + 1);
                    continue;
                }
                String headName = headWithYears.get(j);
                Cell cell = row.createCell(j);
                cell.setCellValue(this.getFieldValueByHeadName(headName, demandDetail));
            }

        }

    }


    private String getFieldValueByHeadName(String headName, WzchTotalDemandDetail demandDetail) {
        AtomicReference<String> val = new AtomicReference<>("");

        if (fieldMap == null) {
            fieldMap = new HashMap<>();
        }
        if (dictListMap == null) {
            dictListMap = new HashMap<>();
        }

        AtomicReference<Field> atomicField = new AtomicReference<>(fieldMap.get(headName));
        if (fieldMap.get(headName) == null) {
            // 根据注释找到字段
            Arrays.stream(demandDetail.getClass().getDeclaredFields())
                    .filter(item -> item.getAnnotation(FtExcel.class) != null && headName.equals(item.getAnnotation(FtExcel.class).name()))
                    .findFirst()
                    .ifPresent(f -> {
                        atomicField.set(f);
                        fieldMap.put(headName, f);
                    });
        }

        try {
            // 根据注解和表头获取到的字段信息
            Field field = atomicField.get();
            field.setAccessible(true);

            // 设置字典项目
            val.set(this.getDictLabel(field, demandDetail));

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("没有根据表头'{}'获取到字段信息, 尝试获取年份列的数据,", headName);
            try {
                List<WzchTotalDemandTimeCount> timeCounts = demandDetail.getWzchTotalDemandTimeCountList();
                timeCounts.stream().filter(item -> headName.equals(item.getYear())).findFirst().ifPresent(countInfo -> {
                    val.set(countInfo.getYearNum() == null?"" : countInfo.getYearNum()+"");
                });
            } catch (Exception exception) {
                log.error(exception.getMessage());
            }
        }
        return val.get();
    }

    private <T> String getDictLabel(Field field, T t) throws Exception {
        String fVal = String.valueOf(field.get(t) == null ? "" : field.get(t));
        FtExcel FtExcel = field.getAnnotation(FtExcel.class);
        String dictType = FtExcel.dictType();
        // 如果有设置字典项
        if (StringUtils.isNotEmpty(dictType)) {
            // 从内存中获取对应字典类型
            Map<String, String> dicMap = dictListMap.get(dictType);
            // 如果内存中有当前字典类型 直接获取
            if (dicMap != null) {
                fVal = dicMap.get(fVal);
            } else {
                // 如果内存中没有值 就从数据库中查去
                List<SysDictData> sysDictData = systemApiService.selectDictDataByType(dictType);
                Map<String, String> dicMap1 = new HashMap<>(sysDictData.size());
                for (SysDictData sysDictDatum : sysDictData) {
                    dicMap1.put(sysDictDatum.getDictValue(), sysDictDatum.getDictLabel());
                }
                fVal = dicMap1.get(fVal) == null ? fVal : dicMap1.get(fVal);
                dictListMap.put(dictType, dicMap1);
            }
        }

        return fVal;
    }


    private void creatMouthSheet(Workbook workbook, WzchTotalDemand totalDemand) {

    }

    private void creatQuarterSheet(Workbook workbook, WzchTotalDemand totalDemand) {
    }


    private List<WzchTotalDemand> selectDemandList(WzchTotalDemand wzchTotalDemand) {
        return wzchTotalDemandMapper.selectWzchTotalDemandList(wzchTotalDemand);
    }


    private void creatTopTwoRows(Workbook workbook, Sheet sheet, WzchTotalDemand totalDemand) {
        // 样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//左右居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//上下居中

        // 第一行
        Row row1 = sheet.createRow(0);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue(totalDemand.getTitle());
        cell1.setCellStyle(cellStyle);
        // 合并单元格
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(region);

        // 第二行
        Row row2 = sheet.createRow(1);
        // 第二行 第一列
        Cell cell20 = row2.createCell(0);
        cell20.setCellValue("项目: ");
        // 第二行 第二列
        Cell cell21 = row2.createCell(1);
        cell21.setCellValue(totalDemand.getProjectName());
        // 第二行 第三列
        Cell cell22 = row2.createCell(2);
        cell22.setCellValue("版本号: ");
        // 第二行 第四列
        Cell cell23 = row2.createCell(3);
        cell23.setCellValue(totalDemand.getVersionCodeStr());
        // 第二行 第五列
        Cell cell24 = row2.createCell(4);
        cell24.setCellValue("计划起止时间: ");
        // 第二行 第六列
        Cell cell25 = row2.createCell(5);
        cell25.setCellValue(sdf.format(totalDemand.getPlanStartTime()) + "至" + sdf.format(totalDemand.getPlanEndTime()));

    }

    @Override
    public WzchTotalDemandAddVO add() {
        WzchTotalDemandAddVO wzchTotalDemandVO = new WzchTotalDemandAddVO();
        wzchTotalDemandVO.setId(IdWorker.createId());
        wzchTotalDemandVO.setVersionCode("1.0");
        wzchTotalDemandVO.setVersionCodeStr("V1.0");
        wzchTotalDemandVO.setCreateTime(DateUtils.getNowDate());
        wzchTotalDemandVO.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        return FlowInfoSearchUtil.getFlowInfo(wzchTotalDemandVO,  FlowEnum.WZZX);
    }

    @Override
    public AjaxResult modify(WzchTotalDemand wzchTotalDemand) {
        try {
            WzchTotalDemand demand = wzchTotalDemandMapper.selectWzchTotalDemandById(wzchTotalDemand.getId());
            if (demand == null) {
                throw new BaseException("调整失败");
            }
            if (YesOrNoEnum.NO.getValue().equals(demand.getValid()) || StringUtils.isBlank(demand.getVersionCode())) {
                throw new BaseException("调整失败");
            }
            checkVersionCode(demand);
            demand = queryWzchTotalDemand(wzchTotalDemand);
            fillNewWzchTotalDemand(demand);
            return new AjaxResult(200, "成功", demand);
        } catch (BaseException b) {
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("调整异常");
        }
    }

    private void checkVersionCode(WzchTotalDemand wzchTotalDemand) {
        String versionCode = new BigDecimal(wzchTotalDemand.getVersionCode()).add(new BigDecimal("1.0")).toString();
        List<WzchTotalDemand> wzchTotalDemands = wzchTotalDemandMapper.selectWzchTotalDemandList(new WzchTotalDemand(versionCode, wzchTotalDemand.getProjectId()));
        if (CollectionUtils.isNotEmpty(wzchTotalDemands)) {
            throw new BaseException("项目名称【" + wzchTotalDemand.getProjectName() + "】已存在【V" + versionCode + "】版本！");
        }
    }

    private void fillNewWzchTotalDemand(WzchTotalDemand wzchTotalDemand) {
        //版本号
        BigDecimal versionCode = new BigDecimal(wzchTotalDemand.getVersionCode()).add(new BigDecimal("1.0"));
        wzchTotalDemand.setVersionCode(String.valueOf(versionCode));
        //单据编号
        wzchTotalDemand.setDemandCode(genCodeService.getNewCode(wzchTotalDemand.getDemandCode(), versionCode.intValue()));
        Long demandId = IdWorker.createId();
        wzchTotalDemand.setId(demandId);
        wzchTotalDemand.setValid("0");
        wzchTotalDemand.setUpdateUser(null);
        wzchTotalDemand.setUpdateUserName(null);
        wzchTotalDemand.setUpdateTime(null);
        wzchTotalDemand.setCreateTime(null);
        wzchTotalDemand.setCreateUserName(null);
        wzchTotalDemand.setCreateUser(null);
        List<WzchTotalDemandDetail> wzchTotalDemandDetailList = wzchTotalDemand.getWzchTotalDemandDetailList();
        if (CollectionUtils.isEmpty(wzchTotalDemandDetailList)) {
            return;
        }
        for (WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemandDetailList) {
            Long detailId = IdWorker.createId();
            wzchTotalDemandDetail.setId(detailId);
            wzchTotalDemandDetail.setTotalDemandId(demandId);
            wzchTotalDemandDetail.setValid("0");
            wzchTotalDemandDetail.setCreateUser(null);
            wzchTotalDemandDetail.setCreateUserName(null);
            wzchTotalDemandDetail.setCreateTime(null);
            wzchTotalDemandDetail.setUpdateUser(null);
            wzchTotalDemandDetail.setUpdateUserName(null);
            wzchTotalDemandDetail.setUpdateTime(null);
            List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCountList = wzchTotalDemandDetail.getWzchTotalDemandTimeCountList();
            if (CollectionUtils.isEmpty(wzchTotalDemandTimeCountList)) {
                continue;
            }
            for (WzchTotalDemandTimeCount wzchTotalDemandTimeCount : wzchTotalDemandTimeCountList) {
                wzchTotalDemandTimeCount.setId(IdWorker.createId());
                wzchTotalDemandTimeCount.setTotalDemandDetailId(detailId);
                wzchTotalDemandTimeCount.setCreateUser(null);
                wzchTotalDemandTimeCount.setCreateTime(null);
                wzchTotalDemandTimeCount.setUpdateUser(null);
                wzchTotalDemandTimeCount.setUpdateTime(null);
            }
        }

    }


    @Override
    public WzchTotalDemandDetailVO detail(WzchTotalDemandDetailVO vo) {
        BigDecimal version = VersionUtil.getVersion("wzch_total_demand_detail", vo.getVersion());
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());

        WzchTotalDemandDetail query = new WzchTotalDemandDetail();
        query.setVersion(version);
        List<WzchTotalDemandDetail> wzchTotalDemandDetailList = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(query);
        if (CollectionUtils.isEmpty(wzchTotalDemandDetailList)){
            vo.setWzchTotalDemandDetailList(new ArrayList<>(2));
            return vo;
        } 
        vo.setWzchTotalDemandDetailList(wzchTotalDemandDetailList);
        //查询日期数据
        List<Long> totalDemandIds = wzchTotalDemandDetailList.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountService.selectByTotalDemandDetailIds(totalDemandIds);
        if (CollectionUtils.isEmpty(wzchTotalDemandTimeCounts)) {
            return vo;
        }
        WzchTotalDemandServiceImpl.wzchTotalDemandDetail(wzchTotalDemandDetailList, wzchTotalDemandTimeCounts);
        //缓存中获取物资信息
        wzchCommonService.setWzchtMaterialInfo(wzchTotalDemandDetailList);
        return vo;
    }

    @Override
//    @DataScope(userAlias = "a")
    public List<WzchTotalDemand> selectList(WzchTotalDemand wzchTotalDemand) {
        if (wzchTotalDemand == null) {
            return new ArrayList<>();
        }
        return wzchTotalDemandMapper.selectList(wzchTotalDemand);
    }

    @Override
    public List<WzchTotalDemand> selectWzchTotalDemandsByProjectIds(List<Long> projectIds) {
        return wzchTotalDemandMapper.selectWzchTotalDemandsByProjectIds(projectIds);
    }

    @Override
    public WzchTotalDemand selectByProjectIdAndVersionCode(Long projectId, String versionCode) {
        if (projectId == null || StringUtils.isBlank(versionCode)) {
            throw new CustomBusinessException("入参缺失");
        }
        return wzchTotalDemandMapper.selectByProjectIdAndVersionCode(projectId, versionCode);
    }

    @Override
    public List<WzchTotalDemand> selectByProjectId(Long projectId) {
        if (projectId == null) {
            throw new CustomBusinessException("入参缺失");
        }
        return wzchTotalDemandMapper.selectByProjectId(projectId);
    }

    @Override
    public int deleteById(Long id) {
        return wzchTotalDemandMapper.deleteById(id);
    }

    @Override
    public List<Long> selectIdsByProjectIdAndVersionCodes(Long projectId, List<String> versoinCodes) {
        return wzchTotalDemandMapper.selectIdsByProjectIdAndVersionCodes(projectId, versoinCodes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processStatus(WzchTotalDemand wzchTotalDemand) {
        if (wzchTotalDemand == null || wzchTotalDemand.getId() == null) {
            throw new BaseException("入参缺失");
        }
        WzchTotalDemand demand = wzchTotalDemandMapper.selectWzchTotalDemandById(wzchTotalDemand.getId());
        if (demand == null) {
            throw new BaseException("数据异常");
        }
        if (YesOrNoEnum.YES.getValue().equals(demand.getValid())) {
            return;
        }
        //先查询有效版本的数据
        List<WzchTotalDemand> wzchTotalDemands = wzchTotalDemandMapper.selectWzchTotalDemandList(new WzchTotalDemand(null, demand.getProjectId(), YesOrNoEnum.YES.getValue()));
        if (CollectionUtils.isNotEmpty(wzchTotalDemands)) {
            WzchTotalDemand demand1 = wzchTotalDemands.get(0);
            //修改为失效
            wzchTotalDemandMapper.updateWzchTotalDemand(new WzchTotalDemand(demand1.getId(), YesOrNoEnum.NO.getValue()));
            List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(new WzchTotalDemandDetail(demand1.getId()));
            if (CollectionUtils.isEmpty(wzchTotalDemandDetails)) {
                throw new BaseException("数据不存在");
            }
            List<Long> detailIds = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
            wzchTotalDemandDetailService.updateOfValid(YesOrNoEnum.NO.getValue(), detailIds);
        }
        wzchTotalDemandMapper.updateWzchTotalDemand(new WzchTotalDemand(demand.getId(), YesOrNoEnum.YES.getValue()));
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(new WzchTotalDemandDetail(demand.getId()));
        if (CollectionUtils.isNotEmpty(wzchTotalDemandDetails)) {
            List<Long> detailIds = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
            wzchTotalDemandDetailService.updateOfValid(YesOrNoEnum.YES.getValue(), detailIds);
        }
        //修改wzch_source
        //TODO
        List<WzchSource> wzchSources = wzchSourceService.selectWzchSourceList(new WzchSource(null, demand.getProjectId()));
        if (CollectionUtils.isEmpty(wzchSources)) {
            log.error("wzchSources为空");
            return;
        }
        WzchSource wzchSource = wzchSources.get(0);
        wzchSource.setDemandNewVersion(demand.getVersionCode());
        wzchSource.setDemandValidDate(new Date());
        wzchSourceService.updateWzchSource(wzchSource);

    }

    @Override
    @Transactional
    public boolean remove(String id) {
        WzchTotalDemand demand = wzchTotalDemandMapper.selectWzchTotalDemandById(Long.parseLong(id));
        if (demand == null) {
            throw new BaseException("未查询到数据");
        }
        wzchTotalDemandMapper.deleteWzchTotalDemandById(demand.getId());
        List<WzchTotalDemandDetail> demandDetails = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(new WzchTotalDemandDetail(demand.getId()));
        if (CollectionUtils.isEmpty(demandDetails)) {
            return true;
        }
        List<Long> detailIds = demandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        wzchTotalDemandDetailService.deleteWzchTotalDemandDetailByIds(detailIds);
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountService.selectByTotalDemandDetailIds(detailIds);
        if (CollectionUtils.isEmpty(wzchTotalDemandTimeCounts)) {
            return true;
        }
        List<Long> timeCountIds = wzchTotalDemandTimeCounts.stream().map(WzchTotalDemandTimeCount::getId).collect(Collectors.toList());
        wzchTotalDemandTimeCountService.deleteWzchTotalDemandTimeCountByIds(timeCountIds);
        return true;
    }

    @Override
    public WzchTotalDemand selectMaxValidVersionCodeWzchTotalDemandByProjectId(Long projectId) {
        return wzchTotalDemandMapper.selectMaxValidVersionCodeWzchTotalDemandByProjectId(projectId);
    }

    private WzchTotalDemand queryWzchTotalDemand(WzchTotalDemand wzchTotalDemand) {
        WzchTotalDemand demand = wzchTotalDemandMapper.selectWzchTotalDemandById(wzchTotalDemand.getId());
        if (demand == null) {
            throw new BaseException("查询详情异常");
        }
        List<WzchTotalDemandDetail> wzchTotalDemandDetailList = wzchTotalDemandDetailService.selectWzchTotalDemandDetails(new WzchTotalDemandDetailRequest(wzchTotalDemand.getId()));
        if (CollectionUtils.isEmpty(wzchTotalDemandDetailList)) {
            return null;
        }
        List<Long> totalDemandIds = wzchTotalDemandDetailList.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(totalDemandIds)) {
            throw new BaseException("查询详情异常");
        }
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountService.selectByTotalDemandDetailIds(totalDemandIds);
        if (CollectionUtils.isEmpty(wzchTotalDemandTimeCounts)) {
            return null;
        }
        WzchTotalDemandServiceImpl.wzchTotalDemandDetail(wzchTotalDemandDetailList, wzchTotalDemandTimeCounts);
        //缓存中获取物资信息
        wzchCommonService.setWzchtMaterialInfo(wzchTotalDemandDetailList);
        wzchTotalDemandDetailList = wzchTotalDemandDetailList.stream().sorted(Comparator.comparing(WzchTotalDemandDetail::getMaterialCode)).collect(Collectors.toList());
        demand.setWzchTotalDemandDetailList(wzchTotalDemandDetailList);
        return demand;
    }


    static void wzchTotalDemandDetail(List<WzchTotalDemandDetail> wzchTotalDemandDetailList, List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts) {
        List<String> viewYearList = wzchTotalDemandTimeCounts.stream().map(WzchTotalDemandTimeCount::getYear).distinct().sorted().collect(Collectors.toList());
        Map<Long, List<WzchTotalDemandTimeCount>> map = wzchTotalDemandTimeCounts.parallelStream().collect(Collectors.groupingBy(WzchTotalDemandTimeCount::getTotalDemandDetailId));
        for (WzchTotalDemandDetail demandDetail : wzchTotalDemandDetailList) {
            for (Map.Entry<Long, List<WzchTotalDemandTimeCount>> entry : map.entrySet()) {
                if (demandDetail.getId().equals(entry.getKey())) {
                    demandDetail.setViewYearList(viewYearList);
                    //按照年份排序
//                    entry.getValue().sort((r,r1)->{
//                        Integer y1 = Integer.parseInt(r.getYear());
//                        Integer y2 = Integer.parseInt(r1.getYear());
//                        return y1==y2?0:(y1>y2?-1:1);
//                    });
                    demandDetail.setWzchTotalDemandTimeCountList(entry.getValue());
                }
            }
        }
    }

}
