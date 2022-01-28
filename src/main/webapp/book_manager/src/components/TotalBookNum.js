import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-end;
`;

const Text = styled.div`
    font-size: 16px;
    font-weight: 500;
    line-height: 19px;
    color: ${(props) => props.theme.colors.detail};
`;

const PriceSection = styled.div`
    font-size: 16px;
    font-weight: 800;
    line-height: 24px;
    color: ${(props) => props.theme.colors.detail};
    display: flex;
    align-items: flex-end;
`;

const Price = styled.div`
    font-size: 24px;
    font-weight: 800;
    line-height: 30px;
    color: ${(props) => props.theme.colors.normal};
`;

function TotalPrice() {
    return (
        <Container>
            <Text>총 권수</Text>
            <PriceSection>
                <Price>3</Price>
                <div style={{ marginRight: 4 }}></div>권
            </PriceSection>
        </Container>
    );
}

export default TotalPrice;
