//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hhwy.utils.tree;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TreeNode<T> extends CommonBaseEntity {
    private Long id;
    private Long pid;
    private List<T> children =new ArrayList<>();
}
