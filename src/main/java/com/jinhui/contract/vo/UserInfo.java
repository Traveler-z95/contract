package com.jinhui.contract.vo;

import org.apache.ibatis.type.Alias;

@Alias("userInfo")
public class UserInfo {

  private String userName;
  private String avatar;
  private String nickName;
  private String phone;
  private String eMail;
  private String signature;
  private String address;


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEMail() {
    return eMail;
  }

  public void setEMail(String eMail) {
    this.eMail = eMail;
  }


  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
            "userName='" + userName + '\'' +
            ", avatar='" + avatar + '\'' +
            ", nickName='" + nickName + '\'' +
            ", phone='" + phone + '\'' +
            ", eMail='" + eMail + '\'' +
            ", signature='" + signature + '\'' +
            ", address='" + address + '\'' +
            '}';
  }
}
