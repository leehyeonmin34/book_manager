package com.leehyeonmin.book_project.domain.util;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.dto.*;
import com.leehyeonmin.book_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ToEntity {

    final private ModelMapper modelMapper;

    final private OrderInfoRepository orderInfoRepository;

    final private OrderItemRepository orderItemRepository;

    final private UserRepository userRepository;

    final private BookRepository bookRepository;

    final private BookAndAuthorRepository bookAndAuthorRepository;

    final private BookReviewInfoRepository bookReviewInfoRepository;

    final private CartItemRepository cartItemRepository;

    final private PublisherRepository publisherRepository;

    final private ReviewRepository reviewRepository;

    final private UserHistoryRepository userHistoryRepository;

    final private AuthorRepository authorRepository;

    public OrderInfo from(OrderInfoDto dto){
        List<OrderItem> orderItems = dto.getOrderItems().stream()
                .map(item -> from(item)).collect(Collectors.toList());

        User user = userRepository.getById(dto.getUserId());

        OrderInfo entity = OrderInfo.builder()
                .orderDate(dto.getOrderDate())
                .id(dto.getId())
                .orderItems(orderItems)
                .user(user)
                .build();
        return entity;
    }

    public OrderItem from(OrderItemDto dto){

        OrderInfo orderInfo = orderInfoRepository.findById(dto.getOrderInfoId()).get();

        Book book = bookRepository.findById(dto.getBookId()).get();

        OrderItem entity = OrderItem.builder()
                .ea(dto.getEa())
                .arriveDate(dto.getArriveDate())
                .orderInfo(orderInfo)
                .book(book)
                .build();
        return entity;
    }

    public User from(UserDto dto){

        List<CartItem> cartItems = cartItemRepository.findByUserId(dto.getId());

        List<OrderInfo> orderInfos = orderInfoRepository.findByUserId(dto.getId());

        List<Review> reviews = reviewRepository.findByUserId(dto.getId());

        List<UserHistory> userHistories = userHistoryRepository.findByUserId(dto.getId());

        User entity = User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .gender(Gender.valueOf(dto.getGender()))
                .name(dto.getName())
                .companyAddress(modelMapper.map(dto.getCompanyAddress(), Address.class))
                .homeAddress(modelMapper.map(dto.getHomeAddress(), Address.class))
                .cartItems(cartItems)
                .orderInfos(orderInfos)
                .reviews(reviews)
                .userHistories(userHistories)
                .build();
        return entity;
    }

    public Book from(BookDto dto){
        List<BookAndAuthor> bookAndAuthors = bookRepository.findById(dto.getId()).get().getBookAndAuthors();
        Publisher publisher = publisherRepository.findById(dto.getPublisherId()).get();
        BookReviewInfo bookReviewInfo = bookReviewInfoRepository.findById(dto.getBookReviewInfoId()).get();

        Book entity = Book.builder()
                .id(dto.getId())
                .name(dto.getName())
                .category(dto.getCategory())
                .status(dto.getStatus())
                .bookAndAuthors(bookAndAuthors)
                .publisher(publisher)
                .bookReviewInfo(bookReviewInfo)
                .build();
        return entity;
    }

    public BookReviewInfo from(BookReviewInfoDto dto){

        BookReviewInfo entity = BookReviewInfo.builder()
                .id(dto.getId())
                .averageReviewScore(dto.getAverageReviewScore())
                .reviewCount(dto.getReviewCount())
                .build();
        return entity;
    }

    public Author from(AuthorDto dto){
        List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
        Author entity;
        if (dto == null){
            return null;
        } else if (dto.getId() != null) {
            bookAndAuthors = bookAndAuthorRepository.getByAuthorId(dto.getId());
        }
        entity = Author.builder()
            .id(dto.getId())
            .name(dto.getName())
            .country(dto.getCountry())
            .bookAndAuthors(bookAndAuthors)
            .build();

        return entity;
    }

    public CartItem from(CartItemDto dto){

        User user = userRepository.getById(dto.getUserId());

        Book book = bookRepository.getById(dto.getBookId());

        CartItem entity = CartItem.builder()
                .ea(dto.getEa())
                .id(dto.getId())
                .user(user)
                .book(book)
                .build();
        return entity;
    }

    public Review from(ReviewDto dto){

        User user = userRepository.getById(dto.getUserId());
        Book book = bookRepository.getById(dto.getBookId());

        Review entity = Review.builder()
                .content(dto.getContent())
                .id(dto.getId())
                .score(dto.getScore())
                .name(dto.getName())
                .user(user)
                .book(book)
                .build();
        return entity;
    }

    public Publisher from(PublisherDto dto){

        List<Book> books = bookRepository.findAll().stream().collect(Collectors.toList());

        Publisher entity = Publisher.builder()
                .id(dto.getId())
                .name(dto.getName())
                .books(books)
                .build();
        return entity;
    }

    public Address from(AddressDto dto){
        Address entity = Address.builder()
                .city(dto.getCity())
                .detail(dto.getAddressDetail())
                .district(dto.getDistrict())
                .zipCode(dto.getZipCode())
                .build();
        return entity;
    }




}
