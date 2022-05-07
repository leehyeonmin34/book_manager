// book--------------------

// GET books
const getBooks = {
    books: [
        {
            id: 1,
            category: "자기계발서",
            price: 22000,
            name: "책 제목",
            author: ["작가"],
            publisher: "출판사",
            description: "책 설명",
            review: {
                score: 4.3, //평균 평점
                num: 8, //리뷰 갯수
            },
        },
    ],
    total: 32,
};

// GET book/{id}
const getBookById = {
    id: 1,
    category: "자기계발서",
    price: 22000,
    name: "책 제목",
    author: ["작가"],
    publisher: "출판사",
    description: "책 설명",
    review_info: {
        average_score: 4.3, //평균 평점
        review_num: 8, //리뷰 갯수
        reviews: [
            {
                id: 1,
                user_id: 1,
                user_name: "유저이름",
                score: 4.5,
                date: "2020.2.15",
                text: "리뷰 내용",
            },
        ],
    },
};

// cart---------------------

// GET cart_items/{user_id}

const getCartItemsByUserId = {
    items: [
        {
            id: 1,
            ea: 2,
            active: true,
            book: {
                name: "책 이름",
                price: 22000,
            },
        },
    ],
};

// POST cart_item/{user_id}/{book_id}

// UPDATE cart_items?id={id}&ea={ea}&active={active} (갯수, 포함정보)

// DELETE cart_items/{id}

// order-------------------

// GET orders/{user_id}

const getOrders = [
    {
        id: 1,
        order_date: "2020.12.31",
        books: [
            {
                id: 1,
                name: "책 이름",
                img: "/src/image.jpg",
                price: 22000,
                ea: 2,
                arrive_at: "2020.12.31",
                delivery_status: "배송 중",
            },
        ],
    },
];

// POST order

const postOrder = {
    orderInfos: [
        {
            book_id: 1,
            price: 22000,
            ea: 2,
        }, // List<OrderItemUpdateRequest>
    ],
    totalPrice: 54000,
    totalNum: 4,
    userId: 1,
};

// user--------------------

// GET user/{id}

const getUserById = {
    user_info: {
        email: "example@email.com",
        gender: "MALE",
        home_address: {
            city: "집 주소 도,시",
            district: "집 주소 시/군",
            detail_address: "집 상세주소",
            zip_code: "집 우편번호",
        },
        company_address: {
            city: "회사 주소 도,시",
            district: "회사 주소 시/군",
            detail_address: "회사 상세주소",
            zip_code: "회사 우편번호",
        },
    },
};

// POST user (회원가입 시)

const postUser = {
    email: "example@email.com",
    name: "이름",
    gender: "MALE",
    home_address: {
        city: "집 주소 도,시",
        district: "집 주소 시/군",
        detail_address: "집 상세주소",
        zip_code: "집 우편번호",
    },
    company_address: {
        city: "회사 주소 도,시",
        district: "회사 주소 시/군",
        detail_address: "회사 상세주소",
        zip_code: "회사 우편번호",
    },
};

// UPDATE users/{id} (마이페이지 수정 시)

const updateUserById = {
    email: "example@email.com",
    name: "이름",
    gender: "MALE",
    home_address: {
        city: "집 주소 도,시",
        district: "집 주소 시/군",
        detail_address: "집 상세주소",
        zip_code: "집 우편번호",
    },
    company_address: {
        city: "회사 주소 도,시",
        district: "회사 주소 시/군",
        detail_address: "회사 상세주소",
        zip_code: "회사 우편번호",
    },
};

// DELETE users/{id} (회원 탈퇴 시)

// review --------------------------------------

// GET reviews/{book_id}

const getReviews = {
    reviews: [
        {
            id: 1,
            text: "리뷰내용",
            score: 4.2,
            book_id: 1,
            date: "2021년  2월 2일",
            user_id: 1,
            user_name: "유저 이름",
        },
    ],
    total: 2,
};

// POST review/

const postReview = {
    text: "리뷰내용",
    score: 4.5,
    book_id: 1,
    user_id: 1,
};

// author ----------------------------------------------------------

// GET author/{author_id}

const getAuthors = {
    authors: [
        {
            id: 1,
            name: "이름",
        },
    ],
    total: 2,
};

// POST author

const postAuthor = {
    id: 1,
    name: "이름",
};
