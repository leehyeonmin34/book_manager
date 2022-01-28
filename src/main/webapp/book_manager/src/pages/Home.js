import React from "react";
import Header from "../components/Header";
import BookListItem from "../components/BookListItem";
import styled from "styled-components";

const SectionTitleSection = styled.div`
    margin: 180px auto 40px;
    width: 1280px;
    display: flex;
    align-items: center;
`;

const SectionTitle = styled.div`
    font-size: 30px;
    font-weight: 700;
    line-height: 36px;
    color: ${(props) => props.theme.colors.black};
    margin-right: 20px;
`;

const Links = styled.div`
    font-size: 24px;
    font-weight: 600;
    line-height: 29px;
    text-align: center;
    display: flex;
    a {
        color: ${(props) => props.theme.colors.primary};
        margin-right: 10px;
    }
`;

const LinkContainer = styled.div``;

function Home() {
    return (
        <div>
            <Header />
            <SectionTitleSection>
                <SectionTitle>책 목록</SectionTitle>
                <Links>
                    <a href="/1">1</a>
                    <a href="/2">2</a>
                    <a href="/3">3</a>
                    <a href="/4">4</a>
                </Links>
            </SectionTitleSection>
            <BookListItem />
            <BookListItem />
            <BookListItem />
            <BookListItem />
            <BookListItem />
        </div>
    );
}

export default Home;
