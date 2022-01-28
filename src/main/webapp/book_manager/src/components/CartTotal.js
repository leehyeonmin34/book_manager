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
    width: 574px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
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

function CartTotal() {
    return (
        <div>
            <Border />
            <Container>
                <Section1></Section1>
                <Section2>
                    <TotalBookNum />
                </Section2>
                <Section3>
                    <TotalPrice />
                </Section3>
                <Section4></Section4>
            </Container>
            <Border />
        </div>
    );
}

export default CartTotal;
