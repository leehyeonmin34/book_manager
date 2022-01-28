import React from "react";
import styled, { css } from "styled-components";
import { darken, lighten } from "polished";

const typeStyles = css`
    ${({ theme, color, type }) => {
        const tint = theme.colors[color];
        const white = theme.colors.white;
        const background = type == "primary" ? tint : white;
        const borderStyle = type == "secondary" && `0 0 0 1px ${tint} inset`;
        const fontStyle = type == "primary" ? white : tint;
        const hoverBGStyle =
            background == white
                ? darken(0.05, background)
                : lighten(0.1, background);
        return css`
            background: ${background};
            box-shadow: ${borderStyle};
            color: ${fontStyle};
            duration: 0.3s;
            cursor: pointer;
            &:hover {
                background: ${hoverBGStyle};
            }
            &:active {
                background: ${darken(0.1, background)};
            }
        `;
    }}
`;

const sizes = {
    large: {
        height: "80px",
    },
    medium: {
        height: "60px",
    },
    small: {
        height: "48px",
    },
};

const sizeStyles = css`
    ${({ size }) =>
        css`
            height: ${sizes[size].height};
        `}
`;

const fullWidth = css`
    ${({ fullWidth }) => {
        return (
            fullWidth &&
            css`
                width: 100%;
            `
        );
    }}
`;

const Container = styled.div`
    width: 150px;
    border-radius: 6px;
    border: none;
    box-shadow: none;
    font-size: 16px;
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: center;

    ${typeStyles}
    ${sizeStyles}
    ${fullWidth}
`;

function Button({ label, size, color, type, fullWidth, style }) {
    return (
        <Container
            color={color || "black"}
            type={type || "primary"}
            size={size || "medium"}
            fullWidth={fullWidth}
            style={style}
        >
            <span>{label}</span>
        </Container>
    );
}

export default Button;
