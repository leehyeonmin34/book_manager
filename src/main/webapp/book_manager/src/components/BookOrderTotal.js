import React from "react";
import styled from "styled-components";
import Border from "./Border";
import TotalBookNum from "./TotalBookNum";
import TotalPrice from "./TotalPrice";

const Container = styled.div`
    width: 1280px;
    height: 86px;
    display: flex;
    align-items: center;
    justify-content: center;
`;
const Section1 = styled.div`
    flex: 1;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
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
    justify-content: flex-end;
`;

function BookOrderTotal() {
    return (
        <div>
            <Border />
            <Container>
                <Section1></Section1>
                <Section2></Section2>
                <Section3>
                    <TotalBookNum />
                </Section3>
                <Section4></Section4>
                <Section5>
                    <TotalPrice />
                </Section5>
            </Container>
            <Border />
        </div>
    );
}

export default BookOrderTotal;
