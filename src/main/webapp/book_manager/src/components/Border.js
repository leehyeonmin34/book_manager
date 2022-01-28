import React from "react";
import styled from "styled-components";

const Container = styled.div`
    height: 1px;
    background: ${(props) => props.theme.colors.lightgray};
`;

function Border({ style }) {
    return <Container style={style}></Container>;
}

export default Border;
