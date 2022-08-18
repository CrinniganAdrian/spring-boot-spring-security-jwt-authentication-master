package com.bezkoder.springjwt.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.bezkoder.springjwt.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;


  private Collection<? extends GrantedAuthority> authorities;

  public Collection<? extends GrantedAuthority> items;

  public List<? extends GrantedAuthority> services;

  public List<? extends GrantedAuthority> projects;


  public UserDetailsImpl(Long id, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities,
                         Collection<? extends GrantedAuthority> items,
                         List<GrantedAuthority> services,
                         List<GrantedAuthority> projects) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.items = items;
    this.services = services;
    this.projects = projects;
  }

  public static UserDetailsImpl build(User user) {

    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    List<GrantedAuthority> items = user.getItems().stream()
            .map(item -> new SimpleGrantedAuthority(item.getName()))
            .collect(Collectors.toList());

    List<GrantedAuthority> services = user.getServices().stream()
            .map(service -> new SimpleGrantedAuthority(service.getName()))
            .collect(Collectors.toList());

    List<GrantedAuthority> projects = user.getProjects().stream()
            .map(project -> new SimpleGrantedAuthority(project.getName()))
            .collect(Collectors.toList());


    return new UserDetailsImpl(
        user.getId(), 
        user.getUsername(), 
        user.getEmail(),
        user.getPassword(),
        authorities,
        items,
        services,
        projects);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Collection<? extends GrantedAuthority> getItems() {
    return items;
  }

  public List<? extends GrantedAuthority> getServices() {
    return services;
  }

  public List<? extends GrantedAuthority> getProjects() {
    return projects;
  }



  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
