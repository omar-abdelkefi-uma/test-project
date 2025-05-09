package org.machinestalk.mapper;

import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.domain.Address;

public class AddressMapper {

	 public static Address mapToAddress(AddressDto dto) {
	        if (dto == null) {
	            return null;  // Handle null case
	        }

	        Address address = new Address();
			address.setStreetNumber(dto.getStreetNumber());
			address.setStreetName(dto.getStreetName());
			address.setCity(dto.getCity());
			address.setPostalCode(dto.getPostalCode());
			address.setCountry(dto.getCountry());
			return address;
	    }

    

}
