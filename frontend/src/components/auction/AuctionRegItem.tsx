import React, { useState, useEffect } from "react";
import "./AuctionRegItem.css";

import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import CloseIcon from "@material-ui/icons/Close";
import Slide from "@material-ui/core/Slide";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import { TransitionProps } from "@material-ui/core/transitions";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
    },
    paper: {
      padding: theme.spacing(2),
      textAlign: "center",
      color: theme.palette.text.secondary,
      height: "370px",
    },
    appBar: {
      position: "relative",
    },
    title: {
      marginLeft: theme.spacing(2),
      flex: 1,
    },
    container: {
      width: "300px",
      position:"fixed",
      textAlign:"center"
    },
  })
);
const Transition = React.forwardRef(function Transition(
  props: TransitionProps & { children?: React.ReactElement },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const tempGallery: Array<temp> = [
  {
    id: 1,
    imgUrl:
      "https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/XG2MW2H3ZRW5FHDVSOMF6FDT3E.jpg",
    title: "아이유꽃",
    level: "S",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 2,
    imgUrl:
      "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
    title: "여신",
    level: "A",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 3,
    imgUrl:
      "https://img4.yna.co.kr/photo/cms/2019/05/02/02/PCM20190502000402370_P2.jpg",
    title: "연합뉴스",
    level: "A",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 4,
    imgUrl:
      "http://image.kmib.co.kr/online_image/2020/1008/611811110015088768_1.jpg",
    title: "똥머리",
    level: "B",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 5,
    imgUrl:
      "https://cdn.dailyimpact.co.kr/news/photo/201901/50650_10024_2221.jpg",
    title: "흑백",
    level: "B",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 6,
    imgUrl:
      "http://www.polinews.co.kr/data/photos/20200834/art_15980031118376_e6a761.jpg",
    title: "정장",
    level: "B",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 7,
    imgUrl: "https://newsimg.sedaily.com/2021/03/24/22JXKJ0JJZ_1.jpg",
    title: "블랙야크",
    level: "B",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 8,
    imgUrl: "https://pbs.twimg.com/media/E9jOv0aUYAw6SIA.jpg",
    title: "하얀색",
    level: "C",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 9,
    imgUrl: "https://cdn.baccro.com/news/photo/202103/25534_57525_3959.jpeg",
    title: "라일락",
    level: "C",
    celeb: "1",
    price: "1btc",
  },
  {
    id: 10,
    imgUrl:
      "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/7mo5/image/RhMj77_UZ1G9smD_INrbLKRVVoc.jpg",
    title: "블루밍",
    level: "C",
    celeb: "1",
    price: "1btc",
  },
];

export type temp = {
  id: number;
  imgUrl: string;
  title: string;
  level: string;
  celeb: string;
  price: string;
};

function AuctionRegItem() {
  const [mycards, setmycards] = useState<any[]>([]);
  const [selectcard, setselectcard] = useState(0);
  const [selectcardimage, setselectcardimage] = useState<string>("");
  const [selectcardtitle, setselectcardtitle] = useState<string>("");
  const [selectcardlevel, setselectcardlevel] = useState<string>("");
  const [price, setprice] = useState<number>(0);

  function createData() {
    return {};
  }

  useEffect(() => {
    //    내 카드목록 불러오기
  });

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const registerAuction = () => {
    console.log(selectcard);
    console.log(price);
  };
  const classes = useStyles();

  return (
    <div style={{ textAlign: "center" }}>
      <Button variant="text" onClick={handleClickOpen}>
        물품 등록
      </Button>
      <Dialog
        fullScreen
        open={open}
        onClose={handleClose}
        TransitionComponent={Transition}
      >
        <AppBar className={classes.appBar}>
          <Toolbar>
            <IconButton
              edge="start"
              color="inherit"
              onClick={handleClose}
              aria-label="close"
            >
              <CloseIcon />
            </IconButton>
            <Typography variant="h6" className={classes.title}>
              판매 카드 등록
            </Typography>
            <Button autoFocus color="inherit" onClick={registerAuction}>
              Register
            </Button>
          </Toolbar>
        </AppBar>

        <div style={{ margin: "20px" }}>
          <Container className={classes.container}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <Paper
                  className={classes.paper}
                >
                  <img
                    src={selectcardimage}
                    alt=""
                    width="100%"
                    height="300px"
                  />
                  <div className="itemcardinfo">
                    <span className="producttitle">{selectcardtitle}</span>
                    <span className="productprice">{selectcardlevel}</span>
                  </div>
                </Paper>
                <br />
                <TextField
                  fullWidth
                  placeholder="가격을 입력해주세요"
                  type="number"
                  onChange={(e: any) => {
                    setprice(e.target.value);
                  }}
                ></TextField>
              </Grid>
            </Grid>
          </Container>
        </div>

        <Container>
          <Grid container spacing={3}>
            {tempGallery.map((image,i) => (
              <Grid item xs={3} sm={3} key={i}>
                <Paper
                  className={classes.paper}
                  style={{ cursor: "pointer" }}
                  onClick={() => {
                    setselectcard(image.id);
                    setselectcardimage(image.imgUrl);
                    setselectcardtitle(image.title);
                    setselectcardlevel(image.level);
                  }}
                >
                  <img src={image.imgUrl} alt="" width="100%" height="300px" />
                  <div className="itemcardinfo">
                    <span className="producttitle">{image.title}</span>
                    <span className="productprice">{image.price}</span>
                  </div>
                </Paper>
              </Grid>
            ))}
          </Grid>
        </Container>
      </Dialog>
    </div>
  );
}

export default AuctionRegItem;