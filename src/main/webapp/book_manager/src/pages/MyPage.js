import React from "react";
import Header from "../components/Header";
import SectionTitle from "../components/SectionTitle";
import Border from "../components/Border";
import styled from "styled-components";
import SectionSubTitle from "../components/SectionSubTitle";
import InputField from "../components/InputField";
import BookOrderList from "../components/BookOrderList";
import Button from "../components/Button";
import OrderHistory from "../components/OrderHistory";

const Page = styled.div``;

const UserInfo = styled.div`
    display: flex;
    width: 1280px;
    margin: 0 auto;
`;

const BasicInfo = styled.div`
    flex: 1;
    margin-right: 60px;
`;

const Body = styled.div`
    padding: 20px 0;
    box-size: border-box;
    height: 520px;
`;

const DeliveryInfo = styled.div`
    flex: 1;
`;

const Address = styled.div``;

const OrderHistoriesContainer = styled.div`
    width: 1280px;
    margin: 0 auto 300px;
`;

function MyPage() {
    return (
        <Page>
            <Header />
            <SectionTitle label="사용자 정보" />
            <UserInfo>
                <BasicInfo>
                    <SectionSubTitle label="기본 정보" border />
                    <Body>
                        <InputField
                            placeholder="이름"
                            leftWidth={100}
                            label="이름"
                            name="orderer_name"
                            inputWidth={135}
                            value="이현민"
                            style={{ marginBottom: 20 }}
                        />
                        <InputField
                            placeholder="ex)01012345678"
                            leftWidth={100}
                            label="전화번호"
                            value="01056517935"
                            name="orderer_phone_number"
                            style={{ marginBottom: 20 }}
                        />
                        <InputField
                            placeholder="example@email.com"
                            leftWidth={100}
                            label="이메일"
                            name="orderer_email"
                            value="leehyeonmin34@gmail.com"
                        />
                        <Button
                            label="비밀번호 변경하기"
                            style={{
                                marginLeft: 100,
                                marginTop: 20,
                                width: 410,
                            }}
                            size="small"
                            type="secondary"
                        />
                    </Body>
                    <Border />
                </BasicInfo>
                <DeliveryInfo>
                    <SectionSubTitle label="배송지 정보" border />
                    <Body>
                        <Address style={{ marginBottom: 60 }}>
                            <InputField
                                placeholder="도, 시"
                                leftWidth={100}
                                label="집 주소"
                                name="home_city"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="군/구"
                                leftWidth={100}
                                name="home_district"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="상세 주소"
                                leftWidth={100}
                                label=""
                                name="home_detail"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="우편 번호"
                                leftWidth={100}
                                label=""
                                name="home_zipCode"
                            />
                        </Address>
                        <Address>
                            <InputField
                                placeholder="도, 시"
                                leftWidth={100}
                                label="회사 주소"
                                name="company_city"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="군/구"
                                leftWidth={100}
                                name="company_district"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="상세 주소"
                                leftWidth={100}
                                label=""
                                name="company_detail"
                                style={{ marginBottom: 10 }}
                            />
                            <InputField
                                placeholder="우편 번호"
                                leftWidth={100}
                                label=""
                                name="company_zipCode"
                            />
                        </Address>
                    </Body>
                    <Border />
                </DeliveryInfo>
            </UserInfo>
            <SectionTitle label="주문 기록" />
            <OrderHistoriesContainer>
                <OrderHistory date={"2020.02.01 (목)"} />
            </OrderHistoriesContainer>
        </Page>
    );
}

export default MyPage;
