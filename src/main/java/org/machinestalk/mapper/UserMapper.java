package org.machinestalk.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserDto.UserInfos;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.User;

public class UserMapper {

    public static UserDto mapToUsersDto(User user, UserDto UsersDto) {
    	if (user == null) {
			return null;
		}

		String id = user.getId() != null ? user.getId().toString() : null;
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String department = user.getDepartment() != null ? user.getDepartment().getName() : null;

		Set<Address> addresses = user.getAddresses();
		List<String> formattedAddresses = addresses != null ? addresses.stream()
				.map(address -> String
						.format("%s %s, %s %s, %s", address.getStreetNumber() != null ? address.getStreetNumber() : "",
								address.getStreetName() != null ? address.getStreetName() : "",
								address.getPostalCode() != null ? address.getPostalCode() : "",
								address.getCity() != null ? address.getCity().toUpperCase() : "",
								address.getCountry() != null ? address.getCountry().toUpperCase() : "")
						.replaceAll(" +", " ").trim())
				.collect(Collectors.toList()) : List.of();

		UserInfos infos = new UserInfos(firstName, lastName, department, formattedAddresses);
		return new UserDto(id, infos);
    }

    

}
