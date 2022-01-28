import React from "react";
import styled from "styled-components";
import BookOrderList from "./BookOrderList";

const Date = styled.div`
    font-size: 20px;
    font-weight: 800;
    line-height: 24px;
    color: ${(props) => props.theme.colors.primary};
    margin-bottom: 20px;
`;

const OrderListContainer = styled.div``;

function OrderHistory({ date }) {
    return (
        <div>
            <OrderListContainer>
                <Date>{date}</Date>
                <BookOrderList />
            </OrderListContainer>
        </div>
    );
}

export default OrderHistory;
