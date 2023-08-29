项管平台数据库初始化SQL语句

-- 初始化结构

-------------------------

-- 初始化数据

-- 2.1.2 总体组、勘测组、验收组默认存在，可删除
INSERT INTO `qqch_survey_organization` VALUES (1696325630121086976, 0, '总体组', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', 'admin', '2023-08-29 08:55:21', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, 1.0, '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `qqch_survey_organization` VALUES (1696325630121086978, 0, '验收组', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', 'admin', '2023-08-29 08:55:21', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, 1.0, '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `qqch_survey_organization` VALUES (1696325630121086979, 0, '勘测组', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', 'admin', '2023-08-29 08:55:21', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, 1.0, '1', NULL, NULL, NULL, NULL, NULL);

-- 2.3.2 形式审查内容 默认展示数据，可删除
INSERT INTO `qqch_survey_result_ask` VALUES (1694285806111428608, '勘察方法、手段及勘探点的布设原则是否合理', NULL, 1.0, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', 'admin', '2023-08-23 17:49:49', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `qqch_survey_result_ask` VALUES (1694285806111428609, '勘察内容、深度及工作量是否满足规范和项目勘察大纲的要求', NULL, 1.0, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', 'admin', '2023-08-23 17:49:49', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `qqch_survey_result_ask` VALUES (1694285806111428610, '勘察成果是否真实、可靠，重要工点是否进行了综合验证', NULL, 1.0, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', 'admin', '2023-08-23 17:49:49', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `qqch_survey_result_ask` VALUES (1694285806111428611, '勘察报告结论是否准确、合理，依据是否充分；对基本地质条件和重要地质问题的揭示和论证能否满足工程设计和方案比选的需要', NULL, 1.0, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', 'admin', '2023-08-23 17:49:49', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL);

