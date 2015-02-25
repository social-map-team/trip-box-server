package com.socialmap.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by yy on 2/23/15.
 */
@Configuration
@ComponentScan("com.socialmap")
@Import({MvcConfig.class, DatabaseConfig.class, SecurityConfig.class})
public class RootConfig {
}
