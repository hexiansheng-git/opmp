
package com.hhwy.utils.tree;

import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNodeBase<T,R> extends BaseEntity {
    private R id;
    private R pid;
    private Integer sort;
    private List<T> children =new ArrayList<>();
}
