import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    align-items: center;
    margin-right: 20px;
`;

function RadioButton({ active, label }) {
    return (
        <Container>
            {active ? (
                <img src="img/radio_active.svg" />
            ) : (
                <img src="img/radio_inactive.svg" />
            )}
            <div style={{ marginLeft: 5, width: "max-content" }}>{label}</div>
        </Container>
    );
}

export default RadioButton;
