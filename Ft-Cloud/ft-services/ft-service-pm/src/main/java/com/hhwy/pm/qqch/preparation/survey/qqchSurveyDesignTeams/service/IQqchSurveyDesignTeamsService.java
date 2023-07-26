package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service;

import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyDesignTeams;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.vo.QqchSurveyDesignTeamsVo;

/**
 * @author ldd
 * @date 2023-07-25 11:08:53
 * @remark 2.1.3 勘察设计队伍配置
 */
public interface IQqchSurveyDesignTeamsService {


    QqchSurveyDesignTeamsVo getQqchSurveyDesignTeamsList(QqchSurveyDesignTeams qqchSurveyDesignTeams);


    void save(QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo);

    void confirm(QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo);
}
