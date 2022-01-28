import React from "react";
import Reputation from "./Reputation";
import styled from "styled-components";

const Container = styled.div`
    margin-bottom: 40px;
`;

const Title = styled.div`
    font-size: 20px;
    font-weight: 700;
    line-height: 30px;
    margin-bottom: 4px;
    color: ${(props) => props.theme.colors.black};
`;

const Line2 = styled.div`
    font-size: 15px;
    font-weight: 500;
    line-height: 23px;
    margin-bottom: 15px;
    color: ${(props) => props.theme.colors.detail};
    display: flex;
    align-items: center;
`;

const Description = styled.div`
    font-size: 16px;
    font-weight: 500;
    line-height: 32px;
    color: ${(props) => props.theme.colors.normal};
`;

function ReviewItem() {
    return (
        <Container>
            <Title>나는 나답게</Title>
            <Line2>
                <Reputation score={"4.0"} /> <div style={{ width: 10 }}></div>
                2020-01-05
            </Line2>
            <Description>
                좋은 구절이 너무나도 많다. 내가 어떤 사람인지 고민하는 일은
                반드시 필요하다. 또한 내 모습을 한 가지로 규정해둘 필요도 없다.
                어떤 상황에서 나답지 않았다고 자책할 필요도 없다. 내가 편안함을
                느끼는 순간, 다소 불편하지만 상황에 맞게 나를 포장하는 순간,
                상대에 따라 새로운 나를 보여주는 순간 등 모든 순간의 내 모습
                역시 나이기 때문이다.
            </Description>
        </Container>
    );
}

export default ReviewItem;
