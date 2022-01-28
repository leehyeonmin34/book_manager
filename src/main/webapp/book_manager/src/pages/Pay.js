import React from "react";
import Header from "../components/Header";
import SectionTitle from "../components/SectionTitle";
import Border from "../components/Border";
import styled from "styled-components";
import RadioChoice from "../components/RadioChoice";
import SectionSubTitle from "../components/SectionSubTitle";
import InputField from "../components/InputField";
import BookOrderList from "../components/BookOrderList";
import Button from "../components/Button";

const Delivery = styled.div`
    width: 1280px;
    margin: 0 auto;
    display: flex;
`;

const Destination = styled.div`
    width: 800px;
    margin-right: 40px;
`;

const SubTitleSection = styled.div`
    display: flex;
`;

const Body = styled.div`
    padding: 24px 0;
    bos-size: border-box;
    height: 380px;
`;

const Address = styled.div`
    margin: 30px 0;
`;

const Orderer = styled.div`
    flex: 1;
    height: 100%;
`;

const OrderListContainer = styled.div`
    width: 1280px;
    margin: 0 auto 300px;
    position: relative;
`;

function Pay() {
    return (
        <div>
            <Header />
            <SectionTitle label="배송 정보" />
            <Delivery>
                <Destination>
                    <Border />
                    <SubTitleSection>
                        <SectionSubTitle
                            label="배송지"
                            style={{ width: 146 }}
                        />
                        <RadioChoice
                            options={["집", "회사", "직접입력"]}
                            value={"집"}
                        />
                    </SubTitleSection>
                    <Border />
                    <Body>
                        <InputField
                            placeholder="이름"
                            leftWidth={146}
                            label="이름"
                            name="name"
                            inputWidth={135}
                        />
                        <Address>
                            <InputField
                                placeholder="도, 시"
                                leftWidth={146}
                                label="주소"
                                name="city"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="군/구"
                                leftWidth={146}
                                name="district"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="상세 주소"
                                leftWidth={146}
                                label=""
                                name="detail"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="우편 번호"
                                leftWidth={146}
                                label=""
                                name="zipCode"
                            />
                        </Address>
                        <InputField
                            placeholder="ex)01012345678"
                            leftWidth={146}
                            label="전화번호"
                            name="phone_number"
                            inputWidth={210}
                        />
                    </Body>
                    <Border />
                </Destination>
                <Orderer>
                    <SectionSubTitle border label="주문자 정보" />
                    <Body>
                        <InputField
                            placeholder="이름"
                            leftWidth={146}
                            label="이름"
                            name="orderer_name"
                            inputWidth={135}
                            value="이현민"
                            style={{ marginBottom: 10 }}
                        />
                        <InputField
                            placeholder="ex)01012345678"
                            leftWidth={146}
                            label="전화번호"
                            value="01056517935"
                            name="orderer_phone_number"
                            style={{ marginBottom: 10 }}
                        />
                        <InputField
                            placeholder="example@email.com"
                            leftWidth={146}
                            label="이메일"
                            name="orderer_email"
                            value="leehyeonmin34@gmail.com"
                        />
                    </Body>
                    <Border />
                </Orderer>
            </Delivery>
            <SectionTitle label="책 리스트" />
            <OrderListContainer>
                <BookOrderList />
                <Button
                    size="large"
                    label="주문하기"
                    style={{
                        width: 300,
                        marginTop: 60,
                        position: "absolute",
                        right: 0,
                    }}
                />
            </OrderListContainer>
        </div>
    );
}

export default Pay;
