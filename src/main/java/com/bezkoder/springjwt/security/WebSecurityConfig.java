package com.bezkoder.springjwt.security;

import com.bezkoder.springjwt.security.jwt.AuthTokenFilter;
import com.bezkoder.springjwt.security.services.UserDetailsSImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bezkoder.springjwt.security.jwt.AuthEntryPointJwt;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsSImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
      .antMatchers("/api/test/**").permitAll()
      .antMatchers("/ccservices/**").permitAll()
      .antMatchers("/items/**").permitAll()
      .antMatchers("/users/{userId}/items/{itemName}").permitAll()
      .antMatchers("/projects/**").permitAll()
      .antMatchers("/favourites/**").permitAll()
      .antMatchers("/users/**").permitAll()
      .antMatchers(HttpMethod.DELETE,"/ccservices/**").permitAll()
      .antMatchers(HttpMethod.DELETE,"/projects/**").permitAll()
      .antMatchers(HttpMethod.DELETE,"/items/**").permitAll()
      .antMatchers(HttpMethod.DELETE,"/favourites/**").permitAll()
      .antMatchers(HttpMethod.PUT,"/ccservices/**").permitAll()
      .antMatchers(HttpMethod.PUT,"/projects/**").permitAll()
      .antMatchers(HttpMethod.PUT,"/items/**").permitAll()
      .antMatchers(HttpMethod.POST,"/ccservices/**").permitAll()
      .antMatchers(HttpMethod.POST,"/projects/**").permitAll()
      .antMatchers(HttpMethod.POST,"/items/**").permitAll()
      .antMatchers(HttpMethod.POST,"/favourites/**").permitAll()

      .anyRequest().authenticated();

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
