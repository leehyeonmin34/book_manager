package com.leehyeonmin.book_project.domain.utils;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.dto.*;
import com.leehyeonmin.book_project.repository.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ToEntity{

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

//    public BaseEntity from(BaseDto dto){
//        return null;

//    }

    public <E extends BaseEntity, D extends BaseDto> E from(D dto) {
        return from(dto);
    }

    private OrderInfo from(OrderInfoDto dto){
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

    private OrderItem from(OrderItemDto dto){

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

    private User from(UserDto dto){

        List<CartItem> cartItems = cartItemRepository.findByUserId(dto.getId());

        List<OrderInfo> orderInfos = orderInfoRepository.findByUserId(dto.getId());

        List<Review> reviews = reviewRepository.getByUserId(dto.getId());

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

    private Book from(BookDto dto){
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

    private BookReviewInfo from(BookReviewInfoDto dto){

        BookReviewInfo entity = BookReviewInfo.builder()
                .id(dto.getId())
                .averageReviewScore(dto.getAverageReviewScore())
                .reviewCount(dto.getReviewCount())
                .build();
        return entity;
    }

    private Author from(AuthorDto dto){
        List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
        Author entity;
        if (dto == null){
            return null;
        } else if (dto.getId() != null) {
            bookAndAuthors = bookAndAuthorRepository.findByAuthorId(dto.getId());
        }
        entity = Author.builder()
            .id(dto.getId())
            .name(dto.getName())
            .country(dto.getCountry())
            .bookAndAuthors(bookAndAuthors)
            .build();

        return entity;
    }

    private CartItem from(CartItemDto dto){

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

    private Review from(ReviewDto dto){

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

    private Publisher from(PublisherDto dto){

        List<Book> books = bookRepository.findAll().stream().collect(Collectors.toList());

        Publisher entity = Publisher.builder()
                .id(dto.getId())
                .name(dto.getName())
                .books(books)
                .build();
        return entity;
    }

    private Address from(AddressDto dto){
        Address entity = Address.builder()
                .city(dto.getCity())
                .detail(dto.getAddressDetail())
                .district(dto.getDistrict())
                .zipCode(dto.getZipCode())
                .build();
        return entity;
    }

}
