import React from "react";
import styled from "styled-components";

const Container = styled.div`
    font-size: 30px;
    font-weight: 700;
    line-height: 36px;
    color: ${(props) => props.theme.colors.black};
    margin: 180px auto 40px;
    width: 1280px;
`;

function SectionTitle({ label, style }) {
    return <Container style={style}>{label || "섹션 타이틀"}</Container>;
}

export default SectionTitle;
