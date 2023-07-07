package com.hhwy.utils.tree;

import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-07 16:20:00
 * @remark 组装树结构工具类
 */
public class TreeUtils extends BaseEntity {

    /**
     * @param dtoList
     * @param pid
     * @return 树形结构
     */
    public static List<? extends TreeVO> buildTree(List<? extends TreeVO> dtoList, Long pid) {

        if (CollectionUtils.isEmpty(dtoList)) {
            return null;
        }
        dtoList.stream().forEach(treeVO -> {
            treeVO.setChildren(
                dtoList.stream().filter((item) -> treeVO.getId().equals(item.getPid())).collect(Collectors.toList()));
        });

        List<? extends TreeVO> collect = dtoList.stream().filter((item) -> pid.equals(item.getPid()))
            .collect(Collectors.toList());

        return collect;

    }

}
