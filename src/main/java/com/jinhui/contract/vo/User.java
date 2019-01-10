package com.jinhui.contract.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Alias("user")
public class User {

  private long userId;
  private String userName;
  private String passWord;
  private boolean enabled;
  private boolean credential;
  private boolean locked;
  private boolean expired;
  private Date createTime;


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isCredential() {
    return credential;
  }

  public void setCredential(boolean credential) {
    this.credential = credential;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public boolean isExpired() {
    return expired;
  }

  public void setExpired(boolean expired) {
    this.expired = expired;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public boolean isAccountNonExpired() {
    return expired;
  }

  public boolean isAccountNonLocked() {
    return locked;
  }

  public boolean isCredentialsNonExpired() {
    return credential;
  }

  @Override
  public String toString() {
    return "User{" +
            "userId=" + userId +
            ", userName='" + userName + '\'' +
            ", passWord='" + passWord + '\'' +
            ", expired=" + expired +
            ", locked=" + locked +
            ", credential=" + credential +
            ", enabled=" + enabled +
            ", createTime=" + createTime +
            '}';
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    return authorities;
  }
}


