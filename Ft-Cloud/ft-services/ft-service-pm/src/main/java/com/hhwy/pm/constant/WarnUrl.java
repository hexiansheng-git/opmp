package com.hhwy.pm.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WarnUrl {


    public static String WORK_GROUP;

    public static String WORK_PLAN;

    public static String REVIEW;

    public static String REVIEW_DETAIL;

    public static String CHANGE;

    public static String SUMMARY_EVALUATION;

    public static String PERSON_CONTROL_PLAN;

    @Value("${warn.url.workGroup}")
    private String workGroup;

    @Value("${warn.url.workPlan}")
    private String workPlan;

    @Value("${warn.url.review}")
    private String review;

    @Value("${warn.url.reviewDetail}")
    private String reviewDetail;

    @Value("${warn.url.change}")
    private String change;

    @Value("${warn.url.summaryEvaluation}")
    private String summaryEvaluation;

    @Value("${warn.url.personControlPlan}")
    private String personControlPlan;

    @PostConstruct
    public void init() {
        WORK_GROUP = workGroup;
        WORK_PLAN = workPlan;
        REVIEW = review;
        REVIEW_DETAIL = reviewDetail;
        CHANGE = change;
        SUMMARY_EVALUATION = summaryEvaluation;
        PERSON_CONTROL_PLAN = personControlPlan;
    }
}
