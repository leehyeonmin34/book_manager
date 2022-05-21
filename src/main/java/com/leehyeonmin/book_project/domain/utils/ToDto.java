package com.leehyeonmin.book_project.domain.utils;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.dto.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ToDto {

    public AuthorDto from(Author entity){
        if(entity != null){
            return null;
        }
        System.out.println(entity);
        AuthorDto dto = AuthorDto.builder()
                .country(entity.getCountry())
                .id(entity.getId())
                .name(entity.getName())
                .build();
        return dto;
    }

    public BookReviewInfoDto from(BookReviewInfo entity){
        if(entity != null){
            return null;
        }
        BookReviewInfoDto dto = new BookReviewInfoDto();
        dto.setAverageReviewScore(entity.getAverageReviewScore());
        dto.setReviewCount(entity.getReviewCount());
        dto.setId(entity.getId());

        return dto;
    }

    public BookDto from(Book entity){
        if(entity != null){
            return null;
        }
        BookDto dto = new BookDto();
        dto.setCategory(entity.getCategory());
        dto.setBookReviewInfoId(entity.getBookReviewInfo().getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus().getCode());
        dto.setPublisherId(entity.getPublisher().getId());
        dto.setId(entity.getId());

        return dto;
    }

    public CartItemDto from(CartItem entity){
        if(entity != null){
            return null;
        }
        CartItemDto dto = new CartItemDto();
        dto.setEa(entity.getEa());
        dto.setUserId(entity.getUser().getId());
        dto.setBookId(entity.getBook().getId());
        dto.setId(entity.getId());

        return dto;
    }

    public OrderInfoDto from(OrderInfo entity){
        if(entity != null){
            return null;
        }
        OrderInfoDto dto = new OrderInfoDto();
        dto.setId(entity.getId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setOrderItems(entity.getOrderItems().stream().map(item -> from(item)).collect(Collectors.toList()));
        dto.setUserId(entity.getUser().getId());

        return dto;
    }

    public OrderItemDto from(OrderItem entity){
        if(entity != null){
            return null;
        }
        OrderItemDto dto = new OrderItemDto();
        dto.setArriveDate(entity.getArriveDate());
        dto.setEa(entity.getEa());
        dto.setBookId(entity.getBook().getId());
        dto.setOrderInfoId(entity.getOrderInfo().getId());
        dto.setId(entity.getId());

        return dto;
    }

    public PublisherDto from(Publisher entity){
        if(entity != null){
            return null;
        }
        PublisherDto dto = new PublisherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    public ReviewDto from(Review entity){
        if(entity != null){
            return null;
        }
        ReviewDto dto = new ReviewDto();
        dto.setContent(entity.getContent());
        dto.setScore(entity.getScore());
        dto.setBookId(entity.getBook().getId());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUserId(entity.getUser().getId());

        return dto;
    }


    public UserDto from(User entity){
        if(entity != null){
            return null;
        }
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setGender(entity.getGender().name());
        dto.setCompanyAddress(from(entity.getCompanyAddress()));
        dto.setHomeAddress(from(entity.getHomeAddress()));
        dto.setName(entity.getName());
        dto.setId(entity.getId());

        return dto;
    }

    public AddressDto from(Address entity){
        if(entity != null){
            return null;
        }
        AddressDto dto = new AddressDto();
        dto.setCity(entity.getCity());
        dto.setDistrict(entity.getDistrict());
        dto.setAddressDetail(entity.getDetail());
        dto.setZipCode(entity.getZipCode());

        return dto;
    }

}
