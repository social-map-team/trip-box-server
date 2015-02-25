package com.socialmap.server;

import com.socialmap.server.config.RootConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by yy on 2/23/15.
 */
public class ApplicationInitializer implements WebApplicationInitializer{
    public static final String SECURITY_FILTER_NAME = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;
    public static final String DISPATCHER_SERVLET_NAME = AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
    private static Logger log = LogManager.getLogger();
    
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        log.trace("Starting application initialization");
        
        // Create spring application context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(RootConfig.class);

        // ContextLoaderListener: manage the lifecycle of the context above
        container.addListener(new ContextLoaderListener(context));

        // Spring MVC: DispatcherServlet
        ServletRegistration.Dynamic dispatcher =
                container.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/api/*");

        // Spring Security: DelegatingFilterProxy
        DelegatingFilterProxy proxy = new DelegatingFilterProxy(SECURITY_FILTER_NAME, context);
        FilterRegistration.Dynamic filter = container.addFilter(SECURITY_FILTER_NAME, proxy);
        filter.setAsyncSupported(true);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");

        // we usually use CharacterEncodingFilter to avoid character encoding problem
        // especially in POST request
        // but we also have no need to add URIEncoding=UTF-8 to <Connector port="8080".../>
        // in Tomcat's server.xml file
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        container.addFilter("characterEncodingFilter", encodingFilter)
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
    }
}
