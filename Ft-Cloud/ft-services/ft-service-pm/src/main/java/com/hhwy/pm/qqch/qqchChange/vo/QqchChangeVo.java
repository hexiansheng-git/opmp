package com.hhwy.pm.qqch.qqchChange.vo;

import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import lombok.Data;

import java.util.List;

@Data
public class QqchChangeVo extends QqchChange {

    List<QqchChangeDetail> detailList;
}
