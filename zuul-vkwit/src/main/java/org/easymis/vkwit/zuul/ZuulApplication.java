package org.easymis.vkwit.zuul;

import org.easymis.vkwit.zuul.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
//允许zuul代理，配置session存储后立刻刷新设置刷新模式为立刻刷新，否则可能获取不到session
//@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@EnableResourceServer
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
	//将TokenFilter加入到请求拦截队列
	@Bean
	public TokenFilter tokenFilter() {
		return new TokenFilter();
	}

}
