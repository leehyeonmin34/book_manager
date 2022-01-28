import React from "react";
import Header from "../components/Header";
import SectionTitle from "../components/SectionTitle";
import CartListIndex from "../components/CartListIndex";
import CartListItem from "../components/CartListItem";
import styled from "styled-components";
import CartTotal from "../components/CartTotal";
import Button from "../components/Button";

const CartList = styled.div`
    width: 1280px;
    margin: 0 auto;
`;

const Buttons = styled.div`
    display: flex;
    width: 1280px;
    margin: 40px auto 100px;
`;

function Cart() {
    return (
        <div>
            <Header />
            <SectionTitle label="장바구니" />
            <CartList>
                <CartListIndex />
                <CartListItem />
                <CartListItem />
                <CartListItem />
                <CartTotal />
            </CartList>
            <Buttons>
                <Button
                    type="secondary"
                    size="large"
                    fullWidth
                    label="쇼핑 계속하기"
                />
                <div style={{ width: 60 }}></div>
                <Button size="large" fullWidth label="전체 주문하기" />
            </Buttons>
        </div>
    );
}

export default Cart;
