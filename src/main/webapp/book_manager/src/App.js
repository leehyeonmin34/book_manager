import Home from "./pages/Home";
import { Route, Routes } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import Detail from "./pages/Detail";
import Cart from "./pages/Cart";
import Pay from "./pages/Pay";
import MyPage from "./pages/MyPage";
import Login from "./pages/Login";

function App() {
    const colors = {
        black: "#2D313E",
        normal: "#5B616B",
        detail: "#9BAAB7",
        disabled: "#CED7E4",
        lightgray: "#EBF0F8",
        bg: "#F4F8FC",
        hoverBg: "#FAFCFF",
        white: "#FFFFFF",
        primary: "#30CF8C",
        yellow: "#FCC419",
    };

    return (
        <div>
            <ThemeProvider theme={{ colors }}>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/detail" element={<Detail />} />
                    <Route path="/cart" element={<Cart />} />
                    <Route path="/mypage" element={<MyPage />} />
                    <Route path="/pay" element={<Pay />} />
                    <Route path="/login" element={<Login />} />
                </Routes>
            </ThemeProvider>
        </div>
    );
}

export default App;
