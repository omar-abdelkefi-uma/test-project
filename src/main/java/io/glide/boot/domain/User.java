package io.glide.boot.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

  @Id private Long id;

  private String firstName;

  private String lastName;

  private Department department;

  private Set<Address> addresses;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

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

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(final Department department) {
    this.department = department;
  }

  public Set<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(final Set<Address> addresses) {
    this.addresses = addresses;
  }
}
