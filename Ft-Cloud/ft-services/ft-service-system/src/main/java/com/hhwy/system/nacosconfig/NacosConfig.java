package com.hhwy.system.nacosconfig;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.extensions.MergedYamlMapping;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.N;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class NacosConfig {
//    @Value("${spring.profiles.active}")
//    private String profile;
    private static final String group = "DEFAULT_GROUP";
//    private static final String namespace = "hwj-dgl";
    private static final String addr = "10.11.239.194:8848";
//    private static final String addr = "127.0.0.1:8848";
    private static final String username = "nacos";
    private static final String password = "hhwy@dgl";

    /**
     * 更新Nacos配置 坏了
     *
     * @param
     * @return
     */
    private static Boolean updateConfigBack(String dataId,String namespace) {
        try {
//            String dataId = String.format("ft-gateway-%s.yml", "dev1");

            //1、配置server-addr
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, addr);
            properties.put(PropertyKeyConst.NAMESPACE, namespace);
            //2、创建ConfigService对象
            ConfigService configService = NacosFactory.createConfigService(properties);

            //读取nacos配置
            String content = configService.getConfig(dataId, group, 5000);

            //修改对应的配置
//            YamlMapping mapping = Yaml.createYamlInput(content).readYamlMapping();
            // log.info("{}", mapping.toString());
//            YamlMapping express = mapping.value("express").asMapping();
//            String accessToken = express.string("accessToken");
            // 如果两个token一样则忽略修改
//            if(newAccessToken.equals(accessToken)){
//                log.warn("token未变化，忽略");
//                return true;
//            }
//            YamlMapping edited = new MergedYamlMapping(
//                    // 原有配置
//                    mapping,
//                    // 需要更新的字段
//                    () -> Yaml.createYamlMappingBuilder()
//                            .add("idworker11", Yaml.createYamlMappingBuilder()
//                                    .add("workerId", "3")
//                                    .add("datacenterId", "3")
////                                    .add("datacenterId1", "2")
//                                    .build()
//                            ).build(),
//                    // true-标识覆盖已有值，false则为追加新的字段
//                    false
//            );
            //更新nacos配置
            boolean b=configService.publishConfig(dataId, group, "age: 30", ConfigType.YAML.getType());
            log.info("执行结果："+b);
            return b;
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    public static void main(String[] args) {
//        updateConfig("ft-service-flowable-dev.yml","hwj-dgl");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapSon = new HashMap<>();
        mapSon.put("workerId", 2);
        mapSon.put("datacenterId", 2);
        map.put("idworker", mapSon);
        updateConfig("ft-service-flowable-dev.yml","hwj-dgl",map);
    }

    /**
     * 更新nacos配置
     * @param dataId 配置文件名称
     * @param namespace 空间
     * @param mapSon 内容（map形式）
     * @return
     */
    static Boolean updateConfig(String dataId, String namespace, Map<String, Object> mapSon) {
        try {
//            String dataId = String.format("ft-gateway-%s.yml", "dev1");

            //1、配置server-addr
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, addr);
            properties.put(PropertyKeyConst.NAMESPACE, namespace);
            properties.put(PropertyKeyConst.USERNAME, username);
            properties.put(PropertyKeyConst.PASSWORD, password);
            //2、创建ConfigService对象
            ConfigService configService = NacosFactory.createConfigService(properties);

            //读取nacos配置
            String content = configService.getConfig(dataId, group, 5000);
            Yaml yaml = new Yaml();
            Map<String,Object> map = yaml.load(content);
            // 字段覆盖
            map.putAll(mapSon);
            String yamlStr = yaml.dumpAsMap(map);
            //更新nacos配置
            boolean b=configService.publishConfig(dataId, group, yamlStr, ConfigType.YAML.getType());
            if (b) {
                log.info("nacos配置文件修改成功");
            } else {
                log.info("nacos配置文件修改失败");
            }
            return b;
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

}
