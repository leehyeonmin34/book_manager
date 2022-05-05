package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddressDto {
    private String city;

    private String district;

    private String addressDetail;

    private String zipCode;

    public AddressDto(Address address){
        city = address.getCity();
        district = address.getDistrict();
        addressDetail = address.getDetail();
        zipCode = address.getZipCode();
    }

}
