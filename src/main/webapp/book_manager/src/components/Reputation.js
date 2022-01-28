import React from "react";
import styled from "styled-components";
import ReputationIcon from "../components/ReputationIcon";

const Container = styled.div`
    font-size: 16px;
    font-weight: 700;
    line-height: 19px;
    color: ${(props) => props.theme.colors.yellow};
    display: flex;
    align-items: center;
`;

function Reputation({ score, style }) {
    return (
        <Container style={style}>
            <ReputationIcon />
            <div style={{ marginRight: 3 }}></div>
            {score || "4.0"}
        </Container>
    );
}

export default Reputation;
