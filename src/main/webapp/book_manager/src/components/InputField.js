import React from "react";
import styled from "styled-components";
import Input from "../components/Input";

const Container = styled.div`
    display: flex;
    align-items: center;
`;

const FieldName = styled.div`
    color: ${(props) => props.theme.colors.normal};
    font-size: 16px;
    font-weight: 500;
    line-height: 19px;
`;

function InputField({
    leftWidth,
    name,
    label,
    placeholder,
    value,
    inputWidth,
    style,
}) {
    return (
        <Container style={style}>
            <FieldName style={{ width: leftWidth }}>{label}</FieldName>
            <Input
                value={value}
                width={inputWidth}
                placeholder={placeholder}
                label={label}
                name={name}
            />
        </Container>
    );
}

export default InputField;
