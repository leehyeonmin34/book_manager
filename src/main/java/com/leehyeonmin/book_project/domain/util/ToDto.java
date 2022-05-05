package com.leehyeonmin.book_project.domain.util;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.dto.*;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ToDto {


    static private ModelMapper modelMapper;

    public static AuthorDto from(Author entity){
        System.out.println(entity);
        AuthorDto dto = AuthorDto.builder()
                .country(entity.getCountry())
                .id(entity.getId())
                .name(entity.getName())
                .build();
        return dto;
    }

    public static BookReviewInfoDto from(BookReviewInfo entity){
        BookReviewInfoDto dto = new BookReviewInfoDto();
        dto.setAverageReviewScore(entity.getAverageReviewScore());
        dto.setReviewCount(entity.getReviewCount());
        dto.setId(entity.getId());

        return dto;
    }

    public static BookDto from(Book entity){
        BookDto dto = new BookDto();
        dto.setCategory(entity.getCategory());
        dto.setBookReviewInfoId(entity.getBookReviewInfo().getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setPublisherId(entity.getPublisher().getId());
        dto.setId(entity.getId());

        return dto;
    }

    public static CartItemDto from(CartItem entity){
        CartItemDto dto = new CartItemDto();
        dto.setEa(entity.getEa());
        dto.setUserId(entity.getUser().getId());
        dto.setBookId(entity.getBook().getId());
        dto.setId(entity.getId());

        return dto;
    }

    public static OrderInfoDto from(OrderInfo entity){
        OrderInfoDto dto = new OrderInfoDto();
        dto.setId(entity.getId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setOrderItems(entity.getOrderItems().stream().map(item -> ToDto.from(item)).collect(Collectors.toList()));
        dto.setUserId(entity.getUser().getId());

        return dto;
    }

    public static OrderItemDto from(OrderItem entity){
        OrderItemDto dto = new OrderItemDto();
        dto.setArriveDate(entity.getArriveDate());
        dto.setEa(entity.getEa());
        dto.setBookId(entity.getBook().getId());
        dto.setOrderInfoId(entity.getOrderInfo().getId());
        dto.setId(entity.getId());

        return dto;
    }

    public static PublisherDto from(Publisher entity){
        PublisherDto dto = new PublisherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    public static ReviewDto from(Review entity){
        ReviewDto dto = new ReviewDto();
        dto.setContent(entity.getContent());
        dto.setScore(entity.getScore());
        dto.setBookId(entity.getBook().getId());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUserId(entity.getUser().getId());

        return dto;
    }


    public static UserDto from(User entity){
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setGender(entity.getGender().name());
        dto.setCompanyAddress(ToDto.from(entity.getCompanyAddress()));
        dto.setHomeAddress(ToDto.from(entity.getHomeAddress()));
        dto.setName(entity.getName());
        dto.setId(entity.getId());

        return dto;
    }

    public static AddressDto from(Address entity){
        AddressDto dto = new AddressDto();
        dto.setCity(entity.getCity());
        dto.setDistrict(entity.getDistrict());
        dto.setAddressDetail(entity.getDetail());
        dto.setZipCode(entity.getZipCode());

        return dto;
    }

}
