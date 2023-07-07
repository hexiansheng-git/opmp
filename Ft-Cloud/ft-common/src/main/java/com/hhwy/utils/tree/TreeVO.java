package com.hhwy.utils.tree;

import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-07 16:20:00
 * @remark 树实体类
 */
@Data
public class TreeVO extends BaseEntity {

    private Long id;

    private Long pid;

    private List<TreeVO> children;

}
