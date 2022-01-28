import React from "react";
import styled from "styled-components";
import Border from "./Border";

const Container = styled.div``;

const SubTitleSection = styled.div`
    height: 54px;
    display: flex;
    align-items: center;
`;

const Title = styled.div`
    line-height: 100%;
    font-size: 18px;
    font-weight: 700;
`;

function SectionSubTitle({ border, label, style }) {
    return (
        <Container style={style}>
            {border && <Border />}
            <SubTitleSection>
                <Title>{label}</Title>
            </SubTitleSection>
            {border && <Border />}
        </Container>
    );
}

export default SectionSubTitle;
