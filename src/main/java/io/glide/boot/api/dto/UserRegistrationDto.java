package io.glide.boot.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserRegistrationDto implements Serializable {

  @NotBlank private String firstName;

  @NotBlank private String lastName;

  @NotBlank private String department;

  @NotNull private AddressDto principalAddress;

  private AddressDto secondaryAddress;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(final String department) {
    this.department = department;
  }

  public AddressDto getPrincipalAddress() {
    return principalAddress;
  }

  public void setPrincipalAddress(final AddressDto principalAddress) {
    this.principalAddress = principalAddress;
  }

  public AddressDto getSecondaryAddress() {
    return secondaryAddress;
  }

  public void setSecondaryAddress(final AddressDto secondaryAddress) {
    this.secondaryAddress = secondaryAddress;
  }

  @Override
  public String toString() {
    return "UserRegistrationDto{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", department='" + department + '\'' +
            ", principalAddress=" + principalAddress +
            ", secondaryAddress=" + secondaryAddress +
            '}';
  }
}
