package com.square.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.square.jwtutil.JwtFilters;


/*
 * Step 1: Make a configuration class that extends WebSecurityConfigurerAdapter
 * 
 * @Override two main method to initiate AuthenticationManager using AuthenticationManagerBuilder for authentication
 * and for Authorization HttpSecurity from WebSecurityConfigurerAdapter
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetailsService;
	
	 @Autowired
	 private JwtFilters jwtFilters;
	 
	//--------- Authentication -------------------------------
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	//--------- Authorization --------------------------------
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/authenticate","/").permitAll()
			.antMatchers("/admin", "/user/delete/{id}", "/user/approve/{id}","/user/block/{id}", "/blog/approve/{id}"
					,"/blog/remove/{id}", "/blog/create_by_admin").hasRole("ADMIN")
			.antMatchers("/user", "/blog/create_by_user","/blog/remove_by_user/{id}").hasAnyRole("ADMIN", "USER")
			.antMatchers("/user/add").permitAll()
			.anyRequest()
            .authenticated()
            .and()
	        .formLogin()
	        .loginPage("/login")
	        .defaultSuccessUrl("/user")
	        .permitAll()
        .and()
	        .logout()
	        .invalidateHttpSession(true)
	        .clearAuthentication(true)
	        .logoutSuccessUrl("/")
	        .permitAll()
	    .and()
	    	.exceptionHandling().accessDeniedPage("/noaccess")
        .and()
	        .csrf()
	        .disable();
    http.addFilterBefore(jwtFilters, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public PasswordEncoder getPAsswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
}
