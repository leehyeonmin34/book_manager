import React from "react";
import styled, { css } from "styled-components";
import { lighten, darken } from "polished";

const Container = styled.div`
    width: 150px;
    font-size: 24px;
    font-weight: 600;
    text-align: center;
    color: ${(props) => props.theme.colors.detail};
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

const typeStyles = css`
    ${({ theme }) => {
        const background = theme.colors.lightgray;
        return css`
            background: ${background};
            cursor: pointer;
            &:hover {
                background: ${darken(0.03, background)};
            }
            &:active {
                background: ${darken(0.1, background)};
            }
        `;
    }}
`;

const Button = styled.div`
    width: 36px;
    height: 36px;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
    ${typeStyles}
`;

function Counter() {
    return (
        <Container>
            <Button>
                <img src="img/minus.svg" alt="minus" />
            </Button>
            1
            <Button>
                <img src="img/plus.svg" alt="plus" />
            </Button>
        </Container>
    );
}

export default Counter;
