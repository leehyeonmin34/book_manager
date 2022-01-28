import React from "react";
import styled from "styled-components";

const Container = styled.input`
    background: ${(props) => props.theme.colors.bg};
    padding: 0 20px;
    border: none;
    width: 370px;
    height: 48px;
    border-radius: 6px;
    width: ${(props) => props.width}px;
    font-size: 16px;
    font-weight: 500;
    box-sizing: border-box;
    color: ${(props) => props.theme.colors.normal};
    &::placeholder {
        color: ${(props) => props.theme.colors.detail};
    }
    &:focus {
        outline: ${(props) => props.theme.colors.primary} solid 2px;
    }
`;

function Input({ style, placeholder, value, name, width }) {
    return (
        <Container
            style={style}
            placeholder={placeholder}
            value={value}
            name={name}
            width={width}
        />
    );
}

export default Input;
