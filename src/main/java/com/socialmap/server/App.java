package com.socialmap.server;

import com.socialmap.server.config.AppConfig;
import com.socialmap.server.gui.ConsoleFrame;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.swing.*;
import java.util.EnumSet;

/**
 * Created by yy on 2/28/15.
 */
public class App {
    public static final String SECURITY_FILTER_NAME = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;
    private static final Logger log = LogManager.getLogger();
    private static ThreadGui tGui;
    private static String digestRealm = "Travel-Box Server";
    private static String digestKey = "93iehs02owjspss92jslahidkso9392iu";
    private static AnnotationConfigWebApplicationContext context;
    public static LocalSessionFactoryBean sessionFactoryBean;

    public static String realm() {
        return digestRealm;
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            server.setHandler(handler);

            context = new AnnotationConfigWebApplicationContext();
            context.register(AppConfig.class);

            handler.setContextPath("/");
            handler.addEventListener(new ContextLoaderListener(context));
            ServletHolder dispatcher = new ServletHolder(new DispatcherServlet(context));
            handler.addServlet(dispatcher, "/api/*");
            DelegatingFilterProxy proxy = new DelegatingFilterProxy(SECURITY_FILTER_NAME, context);
            FilterHolder filter1 = new FilterHolder(proxy);
            handler.addFilter(filter1, "/*", EnumSet.allOf(DispatcherType.class));
            CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
            encodingFilter.setEncoding("UTF-8");
            handler.addFilter(new FilterHolder(encodingFilter), "/*", EnumSet.allOf(DispatcherType.class));

            tGui = new ThreadGui(server);
            tGui.start();

        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~");
            System.out.println(ExceptionUtils.getRootCause(e).getMessage());
            Throwable root = ExceptionUtils.getRootCause(e);
            if (root instanceof HibernateException) {
                System.out.println("==============");
            }
        }
    }


    /**
     * Created by yy on 2/28/15.
     */
    public static class ThreadGui extends Thread {

        private Server server;

        public ThreadGui(Server server){
            super();
            this.server = server;
        }

        @Override
        public void run() {
            //TODO We set look&feel later in menus
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            //com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel
            //javax.swing.plaf.nimbus.NimbusLookAndFeel
            //com.sun.java.swing.plaf.motif.MotifLookAndFeel
            //com.sun.java.swing.plaf.windows.WindowsLookAndFeel

            //context.getBean(ConsoleFrame.class).setVisible(true);
            ConsoleFrame consoleFrame = new ConsoleFrame(server);
            consoleFrame.setVisible(true);
            // TODO close sessionFactory
        }
    }
}
