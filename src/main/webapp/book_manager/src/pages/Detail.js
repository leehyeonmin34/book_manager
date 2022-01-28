import React from "react";
import Header from "../components/Header";
import styled from "styled-components";
import Button from "../components/Button";
import Counter from "../components/Counter";
import Reputation from "../components/Reputation";
import ReviewItem from "../components/ReviewItem";
import WriteReview from "../components/WriteReview";

const Container = styled.div`
    display: flex;
    width: 1280px;
    margin: 130px auto 500px;
`;

const Image = styled.div`
    background: ${(props) => props.theme.colors.bg};
    height: 620px;
    width: 422px;
    position: fixed;
    ${"" /* position: relative; */}
`;

const Right = styled.div`
    width: 1280px;
    margin: 0 auto;
    flex: 1;
`;

const RightInner = styled.div`
    padding-left: 463px;
    box-size: border-box;
`;

const Info = styled.div`
    width: 100%;
    display: flex;
    height: 620px;
`;

const TextInfo = styled.div`
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
`;

const Up = styled.div``;

const BookInfo = styled.div`
    padding: 30px 0;
    box-size: border-box;
    border-bottom: 1px solid ${(props) => props.theme.colors.lightgray};
`;

const Title = styled.div`
    font-size: 26px;
    font-weight: 700;
    line-height: 31px;
    color: ${(props) => props.theme.colors.black};
    margin-right: 18px;
    margin-bottom: 16px;
`;

const Line2 = styled.div`
    margin-right: 12px;
    margin-bottom: 16px;
    font-size: 14px;
    font-weight: 500;
    line-height: 17px;
    color: ${(props) => props.theme.colors.normal};
`;
const Author = styled.span`
    margin-right: 12px;
`;

const Publisher = styled.span`
    color: ${(props) => props.theme.colors.detail};
`;

const Line3 = styled.div`
    font-size: 16px;
    font-weight: 500;
    line-height: 19px;
    display: flex;
    align-items: center;
    color: ${(props) => props.theme.colors.detail};
`;

const ReputeNum = styled.span`
    color: ${(props) => props.theme.colors.normal};
    font-weight: 700;
`;

const PriceSection = styled.div`
    font-size: 20px;
    font-weight: 700;
    line-height: 24px;
    display: flex;
    align-items: flex-end;
    color: ${(props) => props.theme.colors.detail};
    margin: 22px 0 29px;
`;

const Price = styled.div`
    font-size: 30px;
    font-weight: 800;
    line-height: 36px;
    margin-right: 4px;
    color: ${(props) => props.theme.colors.black};
`;

const Description = styled.div`
    font-size: 16px;
    font-weight: 500;
    line-height: 32px;
    color: ${(props) => props.theme.colors.normal};
    display: flex;
    align-items: center;
`;

const Down = styled.div``;

const Buttons = styled.div`
    display: flex;
    margin-top: 27px;
`;

const ReviewSection = styled.div`
    margin: 120px auto 0;
    width: 100%;
`;

const ReviewTitle = styled.div`
    font-size: 28px;
    font-weight: 700;
    line-height: 42px;
    margin-bottom: 26px;
    color: ${(props) => props.theme.colors.black};
`;

function Detail() {
    return (
        <div>
            <Header />
            <Container>
                <Image />
                <Right>
                    <RightInner>
                        <Info>
                            <TextInfo>
                                <Up>
                                    <BookInfo>
                                        <Title>책 제목</Title>
                                        <Line2>
                                            <Author>작가</Author>
                                            <Publisher>출판사</Publisher>
                                        </Line2>
                                        <Line3>
                                            <Reputation score={4.3} />
                                            <div style={{ width: 8 }}></div>
                                            회원리뷰(<ReputeNum>8</ReputeNum>건)
                                        </Line3>
                                    </BookInfo>
                                    <PriceSection>
                                        <Price>16,020</Price>원
                                    </PriceSection>
                                    <Description>
                                        『오늘은 이만 좀 쉴게요』의 저자
                                        손힘찬이 있는 그대로의 자기 모습으로
                                        살아가도록 돕는 『나는 나답게 살기로
                                        했다』로 3년 만에 독자들을 만난다.
                                        전작이 안온한 쉼의 문장으로 많은 사람을
                                        위로했다면 『나는 나답게 살기로 했다』는
                                        자신과 삶을 깊이 성찰해 본래의 자신을
                                        받아들이고 더 적극적으로 나답게
                                        살아가라는 지침을 담았다. 과거의
                                        트라우마를 극복하는 과정으로 자신의
                                        정체성을 자세히 들여다본 후에는 독서,
                                        글쓰기, 운동, 명상 등 나답게 살아가기
                                        위해 저자가 제시하는 구체적인 방법을
                                        따라가보자. 결국 나답게 살아갈 수 있어야
                                        타인을 이해할 수 있고 나아가 세상을
                                        이해할 수 있음을 저자는 자신의 삶에서
                                        우러나온 깨달음을 토대로 역설한다.
                                    </Description>
                                </Up>
                                <Down>
                                    <Counter />
                                    <Buttons>
                                        <Button
                                            label="바로구매"
                                            style={{ marginRight: 20 }}
                                        />
                                        <Button
                                            type="secondary"
                                            label="카트에 담기"
                                        />
                                    </Buttons>
                                </Down>
                            </TextInfo>
                        </Info>
                        <ReviewSection>
                            <ReviewTitle>리뷰</ReviewTitle>
                            <ReviewItem />
                            <ReviewItem />
                            <ReviewItem />
                            <ReviewItem />
                            <WriteReview />
                        </ReviewSection>
                    </RightInner>
                </Right>
            </Container>
        </div>
    );
}

export default Detail;
