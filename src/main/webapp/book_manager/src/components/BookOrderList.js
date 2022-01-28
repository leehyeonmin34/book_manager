import React from "react";
import BookOrderListItem from "./BookOrderListItem";
import BookOrderListIndex from "./BookOrderListIndex";
import BookOrderTotal from "./BookOrderTotal";

function BookOrderList() {
    return (
        <div>
            <BookOrderListIndex />
            <BookOrderListItem />
            <BookOrderListItem />
            <BookOrderListItem />
            <BookOrderTotal />
        </div>
    );
}

export default BookOrderList;
