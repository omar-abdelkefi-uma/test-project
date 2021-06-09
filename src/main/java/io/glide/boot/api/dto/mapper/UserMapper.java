package io.glide.boot.api.dto.mapper;

import io.glide.boot.api.dto.AddressDto;
import io.glide.boot.api.dto.UserDto;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.domain.Address;
import io.glide.boot.domain.Department;
import io.glide.boot.domain.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User mapToUser(UserRegistrationDto userRegistrationDto){
        ModelMapper modelMapper = new ModelMapper();
        List<AddressDto> addresses = new ArrayList<>();
        if (userRegistrationDto.getPrincipalAddress() != null) {
            addresses.add(userRegistrationDto.getPrincipalAddress());
        }
        if (userRegistrationDto.getSecondaryAddress() != null) {
            addresses.add(userRegistrationDto.getSecondaryAddress());
        }
        Set<Address> result = addresses.stream()
                .filter(Objects::nonNull)
                .map(dto -> modelMapper.map(dto, Address.class))
                .collect(Collectors.toSet());
        User user = modelMapper.map(userRegistrationDto, User.class);
        user.setAddresses(result);
        user.setDepartment(new Department(userRegistrationDto.getDepartment()));
        return user;
    }

    public static UserDto mapToUserDto(User user){
        user.setAddresses(user.getAddresses().stream().filter(address -> address.getCity()!=null).collect(Collectors.toSet()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper.typeMap(User.class, UserDto.class).addMappings(mapper -> {
            mapper.<String>map(User::getFirstName, (dest, v) -> dest.getUserInfos().setFirstName(v));
            mapper.<String>map(User::getLastName, (dest, v) -> dest.getUserInfos().setLastName(v));
            mapper.<String>map(src -> src.getDepartment().getName(), (dest, v) -> dest.getUserInfos().setDepartment(v));
            mapper.<List<String>>map(User::getAddresses, (dest, v) -> dest.getUserInfos().setAdresses(v));
        }).addMapping(User::getId, UserDto::setId)
                .map(user);

    }

}
