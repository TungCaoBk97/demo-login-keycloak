package com.example.gradkeycloak;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}
	//register a user session after successful authentication

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth){
//		SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
//		grantedAuthorityMapper.setPrefix("ROLE_");
//      //Member in Keycloak --> Role_Member in spring
//		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
//		auth.authenticationProvider(keycloakAuthenticationProvider);

		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}
//	Keycloak register with Spring Security as the Authentication Provider.

//	@Bean
//	@Override
//	@ConditionalOnMissingBean(HttpSessionManager.class)
//	protected HttpSessionManager httpSessionManager() {
//		return new HttpSessionManager();
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http
				.authorizeRequests()
				.antMatchers("/books").hasAnyRole("member", "librarian")
				.antMatchers("/manager").hasRole("librarian")
				.anyRequest().permitAll();
	}

}
