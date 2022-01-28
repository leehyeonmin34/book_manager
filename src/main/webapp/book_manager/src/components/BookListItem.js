import React from "react";
import styled from "styled-components";
import Button from "../components/Button";
import Counter from "../components/Counter";
import Reputation from "../components/Reputation";

const Container = styled.div`
    display: flex;
    width: 1280px;
    margin: 0 auto;
    padding: 40px;
    border-radius: 20px;

    & {
        margin-bottom: 100px;
    }
    &:hover {
        background: ${(props) => props.theme.colors.hoverBg};
    }
`;

const Left = styled.div`
    display: flex;
    flex: 1;
`;

const Image = styled.div`
    width: 147px;
    height: 216px;
    background: ${(props) => props.theme.colors.bg};
    margin-right: 30px;
`;

const Description = styled.div`
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
`;

const DescriptionTop = styled.div``;

const Line1 = styled.div`
    margin-bottom: 10px;
`;

const Title = styled.span`
    font-size: 26px;
    font-weight: 700;
    line-height: 31px;
    color: ${(props) => props.theme.colors.black};
    margin-right: 18px;
`;

const Author = styled.span`
    font-size: 20px;
    line-height: 24px;
    margin-right: 12px;
    color: ${(props) => props.theme.colors.normal};
`;

const Publisher = styled.span`
    font-size: 20px;
    line-height: 24px;
    color: ${(props) => props.theme.colors.detail};
`;

const Line2 = styled.div`
    font-size: 24px;
    font-weight: 700;
    color: ${(props) => props.theme.colors.detail};
    margin-bottom: 7px;
`;

const Price = styled.span`
    font-size: 30px;
    font-weight: 800;
    line-height: 36px;
    color: ${(props) => props.theme.colors.black};
`;

const Line3 = styled.div`
    font-size: 16px;
    font-weight: 500;
    line-height: 19px;
    color: ${(props) => props.theme.colors.detail};
    display: flex;
    align-items: center;
`;

const ReputeNum = styled.span`
    color: ${(props) => props.theme.colors.normal};
    font-weight: 600;
`;

const Line4 = styled.div`
    font-size: 16px;
    font-weight: 500;
    line-height: 29px;
    color: ${(props) => props.theme.colors.detail};
`;

const Right = styled.div`
    width: 150px;
    margin-left: 120px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
`;

const Buttons = styled.div``;

function BookListItem() {
    return (
        <a href="/detail" style={{ textDecoration: "none" }}>
            <Container>
                <Left>
                    <Image />
                    <Description>
                        <DescriptionTop>
                            <Line1>
                                <Title>책 제목</Title>
                                <Author>작가</Author>
                                <Publisher>출판사</Publisher>
                            </Line1>
                            <Line2>
                                <Price>16,020</Price>원
                            </Line2>
                            <Line3>
                                <Reputation
                                    score={4.3}
                                    style={{ marginRight: 10 }}
                                />
                                회원리뷰(<ReputeNum>8</ReputeNum>건)
                            </Line3>
                        </DescriptionTop>
                        <Line4>
                            무한 탐색의 시대에 꾸준히 전념하는 사람들의
                            이야기나와 세상을 바꾸는 『전념』의 놀라운 힘아마도
                            이런 경험이 있을 것이다. 늦은 밤, 볼거리를 찾아
                            넷플릭스를 뒤적이며 수많은 선택지를 살펴보지만, 결국
                            영화 한 편도 고르지 못한 채 스크롤만 내리다가 잠들어
                            버리는, 그런 경우 말이다.『전념』의 저자 피트
                            데이...
                        </Line4>
                    </Description>
                </Left>
                <Right>
                    <Counter />
                    <Buttons>
                        <Button label="바로 구매" />
                        <div style={{ height: 10 }}></div>
                        <Button type="secondary" label="장바구니에 담기" />
                    </Buttons>
                </Right>
            </Container>
        </a>
    );
}

export default BookListItem;
