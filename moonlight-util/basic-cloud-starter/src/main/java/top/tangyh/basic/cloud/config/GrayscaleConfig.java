package top.tangyh.basic.cloud.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

/**
 * 灰度配置
 * 默认开启，视情况关闭。
 *
 * @author zuihou
 * @date 2021年07月13日11:44:09
 */
@ConditionalOnProperty(value = "lamp.grayscale.enabled", havingValue = "true", matchIfMissing = true)
@LoadBalancerClients(defaultConfiguration = GrayscaleLbConfig.class)
public class GrayscaleConfig {

}
