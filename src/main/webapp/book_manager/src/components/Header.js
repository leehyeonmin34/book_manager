import React from "react";
import { MdPerson, MdWork } from "react-icons/md";
import styled, { css } from "styled-components";
import Button from "../components/Button";
import { Link } from "react-router-dom";

const Container = styled.div`
    color: ${(props) => props.theme.colors.normal};
    height: 95px;
    border-bottom: 1px solid ${(props) => props.theme.colors.lightgray};
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    background: ${(props) => props.theme.colors.white};
`;

const Inner = styled.div`
    width: 1280px;
    height: 100%;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
`;

const Right = styled.div`
    display: flex;
`;

const iconStyle = css`
    cursor: pointer;
    margin-left: 24px;
    color: ${(props) => props.theme.colors.normal};
`;
const IconContainer = styled.div`
    ${"" /* size: 36px; */}
    padding: 10px;
    border-radius: 6px;
    &:hover {
        background: ${(props) => props.theme.colors.bg};
    }
`;

function Header() {
    return (
        <Container>
            <Inner>
                <Link to="/">
                    <img src="img/logo.svg" alt="Logo" />
                </Link>
                <Right>
                    <Link to="/cart">
                        <IconContainer>
                            <img src="img/cart.svg" />
                        </IconContainer>
                    </Link>
                    <div style={{ width: 24 }}></div>
                    <Link to="/mypage">
                        <IconContainer>
                            <img src="img/profile.svg" />
                        </IconContainer>
                    </Link>
                </Right>
            </Inner>
        </Container>
    );
}

export default Header;
