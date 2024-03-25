package ru.geegbrain.hw8Sem8SpringAOP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.geegbrain.hw8Sem8SpringAOP.aspects.UserActionAspect;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class ProjectConfig {
    @Bean
    public UserActionAspect userActionAspect(){
        return  new UserActionAspect();
    }
}