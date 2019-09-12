package com.fedex;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
@ComponentScan("com.fedex.*")
//@PropertySource(value = { "classpath:application.properties" })
public class AppConfig extends WsConfigurerAdapter {

	/*
	 * @Bean public ServletRegistrationBean
	 * messageDispatcherServlet(ApplicationContext applicationContext) {
	 * System.out
	 * .println("Inside messageDispatcherServlet() *-**-*-*-*-*-*-*-*-*-*-*-*-*-*- "
	 * ); MessageDispatcherServlet mds = new MessageDispatcherServlet();
	 * mds.setApplicationContext(applicationContext);
	 * mds.setTransformWsdlLocations(true); return new
	 * ServletRegistrationBean(mds, "/ws/*");
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
	

	@Bean(name = "accounts")
	public DefaultWsdl11Definition defaultWsdl11Definition(
			XsdSchema accountschema) {
		System.out
				.println("Inside defaultWsdl11Definition() *-**-*-*-*-*-*-*-*-*-*-*-*-*-*- ");
		DefaultWsdl11Definition wsdldefinition = new DefaultWsdl11Definition();
		wsdldefinition.setPortTypeName("AccountsPort");
		wsdldefinition.setLocationUri("/ws");
		wsdldefinition.setTargetNamespace("http://fedex.com/TestService");
		System.out.println("accountschema ======>" + accountschema);
		wsdldefinition.setSchema(accountschema);
		return wsdldefinition;
	}

	@Bean
	public XsdSchema accountsSchema() {
		System.out
				.println("Inside accountsSchema() *-**-*-*-*-*-*-*-*-*-*-*-*-*-*- ");
		System.out.println("accountschema ======>"
				+ new ClassPathResource("accounts.xsd").toString());
		ClassPathResource resource = new ClassPathResource("accounts.xsd");
		SimpleXsdSchema schema = new SimpleXsdSchema();
		schema.setXsd(resource);
		// System.out.println("accountschema ======>"+new SimpleXsdSchema(new
		// ClassPathResource("accounts.xsd")));
		return schema;
	}

}
