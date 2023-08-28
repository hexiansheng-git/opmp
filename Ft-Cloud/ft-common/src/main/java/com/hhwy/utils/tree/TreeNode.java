//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hhwy.utils.tree;

import com.hhwy.common.core.domain.R;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode<T> extends TreeNodeBase<T,Long> {
    private Long id;
    private Long pid;
    /**
     * 是否是叶子节点 1-是 0-否
     */
    private String leaf;
}
