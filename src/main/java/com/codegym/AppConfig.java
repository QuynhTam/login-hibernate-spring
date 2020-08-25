package com.codegym;


import com.codegym.dao.UserDaoImpl;
import com.codegym.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("com.codegym.controller")
@PropertySource("classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    Environment evn;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(evn.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(evn.getProperty("jdbc.url"));
        dataSource.setUsername(evn.getProperty("jdbc.username"));
        dataSource.setPassword(evn.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(new String[]{"com.codegym.model"});
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    @Autowired
    public UserDaoImpl userDao(SessionFactory sessionFactory) {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setSessionFactory(sessionFactory);
        return userDao;
    }

    @Bean
    @Autowired
    public UserService userService(SessionFactory sessionFactory) {
        return new UserService();
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", evn.getProperty("hibernate.dialect"));
                setProperty("hibernate.format_sql", evn.getProperty("hibernate.format_sql"));
                setProperty("hibernate.show_sql", evn.getProperty("hibernate.show_sql"));
                setProperty("hibernate.hbm2ddl.auto", evn.getProperty("hibernate.hbm2ddl.auto"));
            }
        };
    }
}
