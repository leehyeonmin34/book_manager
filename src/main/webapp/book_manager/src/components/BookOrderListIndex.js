import React from "react";
import styled from "styled-components";
import Border from "../components/Border";

const Container = styled.div`
    color: ${(props) => props.theme.colors.detail};
    width: 1280px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
`;
const Section1 = styled.div`
    flex: 1;
    height: 100%;
    display: flex;
    align-items: center;
`;
const Section2 = styled.div`
    width: 110px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
`;
const Section3 = styled.div`
    width: 110px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
`;
const Section4 = styled.div`
    width: 110px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
`;

const Section5 = styled.div`
    width: 110px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
`;

function BookListIndex() {
    return (
        <div>
            <Border />
            <Container>
                <Section1>
                    <span>책</span>
                </Section1>
                <Section2>
                    <span>가격</span>
                </Section2>
                <Section3>
                    <span>수량</span>
                </Section3>
                <Section4>
                    <span>합계</span>
                </Section4>
                <Section5>
                    <span>배송일</span>
                </Section5>
            </Container>
            <Border />
        </div>
    );
}

export default BookListIndex;
