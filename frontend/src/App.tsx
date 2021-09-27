import React from "react";
import Navbar from "./components/main/Navbar";
import { Route, Switch } from "react-router-dom";
import Main from "./components/pages/Main";
import MyPage from "./components/pages/MyPage";
import MainMarket from "./components/pages/MainMarket";
import Gallery from "./components/pages/Gallery";
import CardPackDetail from "./components/pages/CardPackDetail";
import Footer from "./components/main/Footer";
import Service from "./components/pages/Service";
import Join from "./components/pages/Join";
import MainMarketItem from "./components/pages/MainMarketItem";
import MyPageUpdate from "./components/pages/MyPageUpdate";
import CardPackShop from "./components/pages/CardPackShop";
import GalleryBoard from "./components/pages/GalleryBoard";
import MainCeleb from "./components/pages/MainCeleb";

declare module "axios" {
  interface AxiosRequestConfig {
    cardpackNo?: number;
    reviewContent?: string;
    salesNo?: number;
    cardpackId?: number;
    keyword?: string;
  }
}

function App(): JSX.Element {
  return (
    <div>
      <Navbar />
      <Switch>
        <Route path="/" exact>
          <Main />
        </Route>
        <Route path="/mypage">
          <MyPage />
        </Route>
        <Route path="/market">
          <MainMarket />
        </Route>
        <Route path="/mainceleb/:no?">
          <MainCeleb />
        </Route>
        <Route path="/cardpackshop/">
          <CardPackShop />
        </Route>
        <Route path="/gallery/:id">
          <Gallery />
        </Route>
        <Route path="/cardpackdetail/:cardpackid">
          <CardPackDetail />
        </Route>
        <Route path="/service/:id">
          <Service />
        </Route>
        <Route path="/join">
          <Join />
        </Route>
        <Route path="/marketitem/:id">
          <MainMarketItem />
        </Route>
        <Route path="/update">
          <MyPageUpdate />
        </Route>
        <Route path="/gboard">
          <GalleryBoard />
        </Route>
        <Route path="/gboard">
          <GalleryBoard />
        </Route>
      </Switch>
      <Footer />
    </div>
  );
}

export default App;
