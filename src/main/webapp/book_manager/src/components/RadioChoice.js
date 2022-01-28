import React from "react";
import RadioButton from "./RadioButton";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
`;

const style = {
    marginRight: 30,
};

function RadioChoice({ options, value: selected }) {
    return (
        <Container>
            {options.map((option) => (
                <RadioButton
                    label={option}
                    active={selected === option}
                    style={style}
                />
            ))}
        </Container>
    );
}

export default RadioChoice;
