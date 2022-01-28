import React from "react";
import styled from "styled-components";

const Container = styled.div`
    color: ${(props) => props.theme.colors.normal};
    width: 100%;
    height: 220px;
    display: flex;
    align-items: center;
    justify-content: center;
`;

const Section1 = styled.div`
    flex: 1;
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
    margin-right: 30px;
`;

const Section2 = styled.div`
    width: 110px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 600;
    color: ${(props) => props.theme.colors.normal};
`;
const Section3 = styled.div`
    width: 110px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 600;

    color: ${(props) => props.theme.colors.normal};
`;
const Section4 = styled.div`
    font-weight: 800;

    width: 110px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: ${(props) => props.theme.colors.black};
`;

const Section5 = styled.div`
    width: 110px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

function BookOrderListItem() {
    return (
        <Container>
            <Section1>
                <Image />
                <span>책 이름</span>
            </Section1>
            <Section2>
                <span>12,210원</span>
            </Section2>
            <Section3>
                <span>1</span>
            </Section3>
            <Section4>
                <span>10,000원</span>
            </Section4>
            <Section5>
                <span style={{ textAlign: "center" }}>
                    <b>2 / 3 (목)</b> <br />
                    도착예정
                </span>
            </Section5>
        </Container>
    );
}

export default BookOrderListItem;
