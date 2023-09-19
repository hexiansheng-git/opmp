package com.hhwy.pm.qqch.wzch.scenemanage.dto;

import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManage;
import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManageDetail;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class WzchSceneManageDTO extends WzchSceneManage {
    private String versionCodeStr;
    private List<WzchSceneManageDetail> detailList;
}
