package cl.ms.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
* ResourceServer
* <p>
* <B>Todos los derechos reservados por Banco de Credito e Inversiones.</B>  
* </p>
*/
@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        	.authorizeRequests()
        		.antMatchers(HttpMethod.GET, "/v2/api-docs")
        		.permitAll()
        		.antMatchers(HttpMethod.POST, "/ms-desafio/login")
        		.permitAll()
        	.and()
        	.authorizeRequests().anyRequest().authenticated()
        	.and()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        	;
    }
}
