package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.mapper;

import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyDesignTeams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-25 11:08:53
 * @remark  2.1.3 勘察设计队伍配置
 */
public interface QqchSurveyDesignTeamsMapper {

    QqchSurveyDesignTeams getQqchSurveyDesignTeams(QqchSurveyDesignTeams qqchSurveyDesignTeams);

    List<QqchSurveyDesignTeams> getQqchSurveyDesignTeamsList(QqchSurveyDesignTeams qqchSurveyDesignTeams);

    int insertQqchSurveyDesignTeams(QqchSurveyDesignTeams qqchSurveyDesignTeams);

    int insertQqchSurveyDesignTeamsList(@Param("qqchSurveyDesignTeamsList") List<QqchSurveyDesignTeams> qqchSurveyDesignTeamsList);

    int updateQqchSurveyDesignTeams(QqchSurveyDesignTeams qqchSurveyDesignTeams);

    int updateQqchSurveyDesignTeamsList(@Param("qqchSurveyDesignTeamsList") List<QqchSurveyDesignTeams> qqchSurveyDesignTeamsList);

    int deleteQqchSurveyDesignTeams(QqchSurveyDesignTeams qqchSurveyDesignTeams);

    int deleteQqchSurveyDesignTeamsByPks(@Param("qqchSurveyDesignTeamsPkList") List<Long> qqchSurveyDesignTeamsPkList);
}
