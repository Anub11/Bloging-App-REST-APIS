package com.anubhav.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.anubhav.blog.security.CustomUserDetailService;
import com.anubhav.blog.security.JwtAuthenticationEnteryPoint;
import com.anubhav.blog.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor // importent for 
                    //Error creating bean with name 'springSecurityFilterChain'
					// defined in class path resource
					// [org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.class]:
					// Bean instantiation via factory method failed; nested exception is
					// org.springframework.beans.BeanInstantiationException: Failed to instantiate
					// [javax.servlet.Filter]: Factory method 'springSecurityFilterChain' threw
					// exception; nested exception is java.lang.NullPointerException: Cannot invoke
					// "Object.getClass()" because "filter" is null 2022-12-31 20:05:38.906 INFO
					// 21124 --- [ restartedMain] j.LocalContainerEntityManagerFactoryBean : Closing
					// JPA EntityManagerFactory for persistence unit 'default'


public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtAuthenticationEnteryPoint jwtAuthenticationEnteryPoint;

	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//		.csrf()
//		.disable()
//		.authorizeHttpRequests()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.httpBasic();

		http.csrf().disable().authorizeHttpRequests().antMatchers("/api/auth/login").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEnteryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
