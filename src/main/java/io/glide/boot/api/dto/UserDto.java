package io.glide.boot.api.dto;

import java.util.List;

public class UserDto {

  /** User id */
  public String id;

  public UserInfos userInfos;

  public UserDto() {
  }

  public UserDto(final String id, String firstName, String lastName, String department, List<String> adresses) {
    this.id = id;
    this.userInfos = new UserInfos(firstName, lastName, department, adresses);
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public UserInfos getUserInfos() {
    return userInfos;
  }

  public void setUserInfos(final UserInfos userInfos) {
    this.userInfos = userInfos;
  }

  public static class UserInfos {

    public String firstName;

    public String lastName;

    public String department;

    /**
     * List of all known user formatted addresses.<br>
     * Example of formatted address: "23 rue de voltaire, 75015 PARIS, FRANCE"
     */
    public List<String> adresses;

    public UserInfos() {}

    public UserInfos(
         String firstName, String lastName, String department, List<String> adresses) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.department = department;
      this.adresses = adresses;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getDepartment() {
      return department;
    }

    public void setDepartment(String department) {
      this.department = department;
    }

    public List<String> getAdresses() {
      return adresses;
    }

    public void setAdresses(List<String> adresses) {
      this.adresses = adresses;
    }

    @Override
    public String toString() {
      return "{" +
              "firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", department='" + department + '\'' +
              ", adresses=" + adresses +
              '}';
    }
  }

  @Override
  public String toString() {
    return "UserDto{" +
            "id='" + id + '\'' +
            ", userInfos=" + userInfos +
            '}';
  }
}
