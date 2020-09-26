package com.waracle.cakemgr.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class SpringConfiguration {

    private static final String GITHUB_CLIENT_ID = "github.client.id";
    private static final String GITHUB_CLIENT_SECRET = "github.client.secret";

    @Value("${db.driver.class.name}")
    private String dbDriverClassName;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${db.url}")
    private String dbUrl;

    private final Environment environment;

    @Autowired
    public SpringConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(dbDriverClassName);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setUrl(dbUrl);

        return dataSource;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration registration = CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId(environment.getProperty(GITHUB_CLIENT_ID))
                .clientSecret(environment.getProperty(GITHUB_CLIENT_SECRET))
                .build();

        return new InMemoryClientRegistrationRepository(Arrays.asList(registration));
    }
}
