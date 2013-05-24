package com.insanedev.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.insanedev.web.util.StringToAddressConverter;
import com.insanedev.web.util.StringToBaseEntityConverterFactory;
import com.insanedev.web.util.StringToUserConverter;

@Configuration
@ImportResource("classpath:/com/insanedev/config/root-context.xml")
@ComponentScan("com.insanedev.web")
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	StringToUserConverter stringToUserConverter
	
	@Autowired
	StringToAddressConverter stringToAddressConverter
	
	@Autowired
	StringToBaseEntityConverterFactory stringToBaseEntityConverter
	
	@Override
	public void addFormatters(FormatterRegistry formatterRegistry) {
//		formatterRegistry.addConverter(stringToUserConverter);
//		formatterRegistry.addConverter(stringToAddressConverter);
		formatterRegistry.addConverterFactory(stringToBaseEntityConverter)
	}
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(1)
		return viewResolver;
	}
	
	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		
		templateResolver.setPrefix("/WEB-INF/views/")
		templateResolver.setSuffix(".html")
		templateResolver.setTemplateMode("HTML5")
		templateResolver.setCacheable(false)
		
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		
		return templateEngine;
	}
	
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver()
		viewResolver.setTemplateEngine(templateEngine())
		viewResolver.setOrder(0)
		viewResolver.setViewNames("user*")
		
		return viewResolver
	}
	
/*	<bean id="templateResolver"
    class="org.thymeleaf.templateresolver.ServletContextTemplateResolver">
<property name="prefix" value="/WEB-INF/templates/" />
<property name="suffix" value=".html" />
<property name="templateMode" value="HTML5" />
</bean>
 
<bean id="templateEngine"
   class="org.thymeleaf.spring3.SpringTemplateEngine">
<property name="templateResolver" ref="templateResolver" />
</bean>
*/
}
