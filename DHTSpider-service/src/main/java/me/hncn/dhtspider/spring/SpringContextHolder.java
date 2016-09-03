package me.hncn.dhtspider.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component(value="springContextHolderProxy")
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext arg)
			throws BeansException {
		applicationContext = arg;
	}

	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}
	public static <T> T  getBean(Class<T> var1) {
		return applicationContext.getBean(var1);
	}

}
