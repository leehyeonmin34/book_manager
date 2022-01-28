import React from "react";
import styled from "styled-components";
import Border from "./Border";

const Container = styled.div`
    color: ${(props) => props.theme.colors.detail};
    width: 1280px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
`;
const Section1 = styled.div`
    width: 574px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    ${"" /* background: red; */}
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
`;
const Section4 = styled.div`
    width: 198px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
`;

function CartListIndex() {
    return (
        <>
            <Border />
            <Container>
                <Section1>
                    <span>상품정보</span>
                </Section1>
                <Section2>
                    <span>수량</span>
                </Section2>
                <Section3>
                    <span>상품금액</span>
                </Section3>
                <Section4></Section4>
            </Container>
            <Border />
        </>
    );
}

export default CartListIndex;
