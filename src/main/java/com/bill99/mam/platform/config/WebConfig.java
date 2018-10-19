package com.bill99.mam.platform.config;

import com.bill99.mam.platform.interceptor.HttpInterceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * auhtor:jerry_xu@99bill.com
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter implements
		ApplicationContextAware {

	@SuppressWarnings("unused")
	private ApplicationContext applicationContext;

	public WebConfig() {
		super();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
		registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
		super.addResourceHandlers(registry);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截规则：除了以下Url，其他都拦截判断
		registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/login")
				.excludePathPatterns("/loginView")
				.excludePathPatterns("/personalRegisterView")
				.excludePathPatterns("/personalRegister")
				.excludePathPatterns("/personal/register")
				.excludePathPatterns("/enterprise/register")
				.excludePathPatterns("/notifyTradeResult");
		super.addInterceptors(registry);
	}
}