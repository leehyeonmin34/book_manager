import React from "react";
import ReputationIcon from "./ReputationIcon";
import Button from "./Button";
import styled from "styled-components";

const Container = styled.div``;

const Top = styled.div`
    height: 30px;
    display: flex;
    ${"" /* align-items: center; */}
`;

const Title = styled.div`
    font-size: 16px;
    font-weight: 700;
    line-height: 24px;
    margin-right: 10px;
`;

const Down = styled.div`
    display: flex;
`;

const TextArea = styled.textarea`
    flex: 1;
    height: 120px;
    margin-right: 10px;
    background: ${(props) => props.theme.colors.bg};
    border: none;
    padding: 16px;
    box-size: border-box;
    border-radius: 10px;
`;

function WriteReview() {
    return (
        <Container>
            <Top>
                <Title>평점</Title>
                <ReputationIcon />
                <ReputationIcon />
                <ReputationIcon />
                <ReputationIcon inactive />
                <ReputationIcon inactive />
            </Top>
            <Down>
                <TextArea />
                <Button label="등록" style={{ width: 100, height: 60 }} />
            </Down>
        </Container>
    );
}

export default WriteReview;
