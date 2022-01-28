import React from "react";
import Input from "../components/Input";
import styled from "styled-components";
import Button from "../components/Button";

const Page = styled.div`
    width: 100%;
    height: 100vh;
`;

const Container = styled.div`
    display: flex;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
`;

const Left = styled.div`
    display: flex;
    align-items: center;
    ${"" /* justify-content: center; */}
    width: 800px;
`;

const Right = styled.div`
    width: 472px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`;

const Inputs = styled.div`
    width: 100%;
    margin-bottom: 50px;
`;

const Links = styled.div`
    font-size: 16px;
    font-weight: 500;
    line-height: 19px;
    margin-top: 30px;
    a {
        text-decoration: none;
        color: ${(props) => props.theme.colors.detail};
        margin-right: 20px;
        &:hover {
            color: ${(props) => props.theme.colors.normal};
        }
    }
`;

function Login() {
    return (
        <Page>
            <Container>
                <Left>
                    <img
                        src="img/login_image.jpg"
                        alt="photo of a man with a jetpack"
                    />
                </Left>
                <Right>
                    <img
                        src="img/logo.svg"
                        style={{ width: 400, marginBottom: 40 }}
                    />
                    <Inputs>
                        <Input
                            name="id"
                            placeholder="아이디"
                            style={{
                                height: 60,
                                marginBottom: 10,
                                width: "100%",
                            }}
                        />
                        <Input
                            name="id"
                            placeholder="아이디"
                            style={{ height: 60, width: "100%" }}
                        />
                    </Inputs>
                    <Button label="로그인" size="medium" fullWidth />
                    <Links>
                        <a href="/findId">아이디 찾기</a>
                        <a href="/findPassword">비밀번호 찾기</a>
                        <a href="/signup">회원 가입</a>
                    </Links>
                </Right>
            </Container>
        </Page>
    );
}

export default Login;
