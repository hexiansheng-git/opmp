//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hhwy.utils.tree;

import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode<T> extends CommonBaseEntity {
    private Long id;
    private Long pid;
    private Integer sort;
    private List<T> children =new ArrayList<>();
}
