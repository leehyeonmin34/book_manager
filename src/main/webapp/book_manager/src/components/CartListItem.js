import React from "react";
import Checkbox from "./Checkbox";
import Counter from "./Counter";
import styled from "styled-components";
import Button from "./Button";

const Container = styled.div`
    color: ${(props) => props.theme.colors.normal};
    width: 100%;
    height: 220px;
    display: flex;
    align-items: center;
    justify-content: center;
`;

const Section1 = styled.div`
    width: 574px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    font-size: 20px;
    font-weight: 500;
    line-height: 24px;
`;

const Image = styled.div`
    height: 159px;
    width: 108px;
    background: ${(props) => props.theme.colors.bg};
    margin: 0 25px 0 30px;
`;

const Section2 = styled.div`
    width: 254px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
`;
const Section3 = styled.div`
    width: 254px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    font-weight: 800;
    line-height: 22px;
    color: ${(props) => props.theme.colors.black};
`;
const Section4 = styled.div`
    width: 198px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
`;

function CartListItem() {
    return (
        <>
            <Container>
                <Section1>
                    <Checkbox active />
                    <Image />
                    <span>책 이름</span>
                </Section1>
                <Section2>
                    <Counter />
                </Section2>
                <Section3>
                    <span>10,000원</span>
                </Section3>
                <Section4>
                    <Button
                        label="주문하기"
                        size="small"
                        style={{ marginBottom: 10 }}
                    />
                    <Button type="secondary" label="삭제" size="small" />
                </Section4>
            </Container>
        </>
    );
}

export default CartListItem;
