import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import "./MarketBuyItem.css";
import jwt_decode from "jwt-decode";
import {
  createStyles,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import MuiDialogTitle from "@material-ui/core/DialogTitle";
import MuiDialogContent from "@material-ui/core/DialogContent";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import LoadingButton from "@mui/lab/LoadingButton";
import TextField from "@mui/material/TextField";
import DialogActions from "@mui/material/DialogActions";
import DialogContentText from "@mui/material/DialogContentText";
import Typography from "@mui/material/Typography";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";
import axios from "axios";
import { contractAbi } from "../abi";

export interface DialogTitleProps extends WithStyles<typeof styles> {
  id: string;
  children: React.ReactNode;
  onClose: () => void;
}
const styles = (theme: Theme) =>
  createStyles({
    root: {
      margin: 0,
      padding: theme.spacing(2),
    },
    closeButton: {
      position: "absolute",
      right: theme.spacing(1),
      top: theme.spacing(1),
      color: theme.palette.grey[500],
    },
  });
const DialogTitle = withStyles(styles)((props: DialogTitleProps) => {
  const { children, classes, onClose, ...other } = props;
  return (
    <MuiDialogTitle disableTypography className={classes.root} {...other}>
      <Typography variant="h6">{children}</Typography>
      {onClose ? (
        <IconButton
          aria-label="close"
          className={classes.closeButton}
          onClick={onClose}
        >
          <CloseIcon />
        </IconButton>
      ) : null}
    </MuiDialogTitle>
  );
});

const DialogContent = withStyles((theme: Theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}))(MuiDialogContent);

function MarketBuyItem(props: any): JSX.Element {
  // ???????????? ????????? ??????
  const [Iam, setIam] = useState(0);
  // web3 ??????
  const Web3 = require("web3");
  const web3 = new Web3("http://13.125.37.55:8548");
  // contract ??????
  const myContractAddress = "0xf1C563Ad18747384222dD4F8D21445bb0Fe4F51D";
  const admin = "0x8BBa1857fD94CF79c78BBE90f977055be015E17E";
  const myContract = new web3.eth.Contract(contractAbi, myContractAddress);
  const [open, setOpen] = React.useState(false);
  const [userAddress, setAddress] = useState<string>("");
  const [userBalance, setBalance] = useState<string>("0");
  const walletCheck = async () => {
    try {
      const res = await axios.get("/api/wallet/", {
        headers: { Authorization: localStorage.getItem("token") },
      });
      if (res.data.success == true) {
        setAddress(res.data.address);
        setBalance(res.data.walletBal);
      }
    } catch {}
  };
  useEffect(() => {
    var token = localStorage.getItem("token");
    if (token) {
      var decoded: any | unknown = jwt_decode(token);
      setIam(decoded.sub);
    }
    walletCheck();
  });
  const handleClickOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const [loading, setloading] = useState(false);

  // ????????????
  const pay2 = async () => {
    await walletCheck();
    if (userAddress) {
      if (props.sellerwallet) {
        if (parseFloat(userBalance) > props.price + 0.01) {
          // ?????? ??????
          setloading(true);
          const tokenSer = parseInt(props.itemtoken);
          try {
            // ??????
            const myunlock = await web3.eth.personal.unlockAccount(
              userAddress,
              "123",
              10000
            );
            console.log(myunlock);
            const yourUnlock = await web3.eth.personal.unlockAccount(
              props.sellerwallet,
              "123",
              10000
            );
            console.log(yourUnlock);
            // ?????? api??????
            const res = await axios.post(
              "/api/auction/buy",
              {
                auctionNo: parseInt(props.auctionNo),
              },
              { headers: { Authorization: localStorage.getItem("token") } }
            );
            console.log(res);
            // ??????
            if (res.data.success) {
              const payEth = await myContract.methods.buyCard(tokenSer).send({
                from: userAddress,
                value: props.price * Math.pow(10, 18),
              });
              console.log(payEth);
              setOpen(false);
              setloading(false);
              // ????????? ??????
              const change = await myContract.methods
                .transferFrom(props.sellerwallet, userAddress, tokenSer)
                .send({
                  from: props.sellerwallet,
                });
              console.log(change);
            } else {
              alert(res.data.msg);
            }
            history.push({
              pathname: "/market",
            });
          } catch (err) {
            console.log(err);
            setOpen(false);
            setloading(false);
            alert("?????? ??????");
          }
        } else {
          alert("????????? ???????????????.");
        }
      } else {
        alert("?????? ????????? ?????????.");
      }
    } else {
      alert("????????? ????????? ?????????.");
    }
  };
  let history = useHistory();
  function makewallet() {
    history.push({
      pathname: "/mypage",
    });
  }
  // ??????
  function setinfo() {
    setnewtitle(props.title);
    setnewdetail(props.detail);
    setnewprice(props.price);
  }
  const handleOpenedit = () => {
    setinfo();
    setopenedit(true);
  };
  const handleCloseedit = () => {
    setopenedit(false);
  };
  const [openedit, setopenedit] = useState<boolean>(false);
  const [newtitle, setnewtitle] = useState<string>("");
  const [errortitle, seterrortitle] = useState(false);
  const [newdetail, setnewdetail] = useState<string>("");
  const [errordetail, seterrordetail] = useState(false);
  const [newprice, setnewprice] = useState<number>(0);
  const [errorprice, seterrorprice] = useState(false);
  useEffect(() => {
    setinfo();
  }, []);
  const changetitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setnewtitle(e.target.value.trim());
  };
  const changedetail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setnewdetail(e.target.value.trim());
  };
  const changeprice = (e: React.ChangeEvent<HTMLInputElement>) => {
    setnewprice(parseInt(e.target.value.trim()));
  };
  function editfunction() {
    axios
      .put(
        "/api/auction/edit",
        {
          auctionNo: props.auctionNo,
          auctionDetail: newdetail,
          auctionTitle: newtitle,
          price: newprice,
        },
        {
          headers: { Authorization: localStorage.getItem("token") },
        }
      )
      .then((res) => {
        setopenedit(false);
        window.location.reload();
      });
  }
  function edit() {
    setopenedit(true);
    if (newtitle) {
      if (newdetail) {
        if (newprice > 0) {
          editfunction();
        } else {
          seterrorprice(true);
          setTimeout(() => {
            setnewprice(props.price);
            seterrorprice(false);
          }, 2000);
        }
      } else {
        seterrordetail(true);
        setTimeout(() => {
          setnewdetail(props.detail);
          seterrordetail(false);
        }, 2000);
      }
    } else {
      seterrortitle(true);
      setTimeout(() => {
        setnewtitle(props.title);
        seterrortitle(false);
      }, 2000);
    }
  }

  const [opendelete, setopendelete] = useState(false);

  const handleClickOpendelete = () => {
    setopendelete(true);
  };

  const handleClosedelete = () => {
    setopendelete(false);
  };

  function deleteitem() {
    axios
      .delete("/api/auction/delete", {
        data: {
          auctionNo: props.auctionNo,
        },
        headers: { Authorization: localStorage.getItem("token") },
      })
      .then(() => {
        setopendelete(false);
        history.push({
          pathname: "/market",
        });
      });
  }

  return (
    <div>
      {parseInt(props.memberNo) === parseInt(props.Iam) ? (
        <div>
          <Button fullWidth onClick={handleOpenedit}>
            ??????
          </Button>
          {/* ?????? */}
          <Dialog open={openedit} onClose={handleCloseedit}>
            <DialogContent>
              <DialogContentText>
                ??????????????? ??????????????????. ???????????? ??????????????? ???????????????.
              </DialogContentText>
              <TextField
                value={newtitle}
                margin="dense"
                id="name"
                label="Title"
                type="text"
                fullWidth
                variant="standard"
                onChange={changetitle}
                inputProps={{
                  maxlength: 10,
                }}
              />
              <TextField
                autoFocus
                margin="dense"
                value={newdetail}
                id="name"
                label="Detail"
                type="text"
                fullWidth
                variant="standard"
                multiline
                onChange={changedetail}
              />
              <TextField
                autoFocus
                margin="dense"
                id="name"
                value={newprice}
                label="Price"
                type="number"
                fullWidth
                variant="standard"
                onChange={changeprice}
                inputProps={{
                  min: 0,
                }}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCloseedit}>Cancel</Button>
              <Button onClick={edit}>save</Button>
            </DialogActions>
            {errortitle ? (
              <Alert severity="error">
                <AlertTitle>Error</AlertTitle>
                <strong>????????? ????????? ??????????????????</strong>
              </Alert>
            ) : null}
            {errordetail ? (
              <Alert severity="error">
                <AlertTitle>Error</AlertTitle>
                <strong>????????? ??????????????? ??????????????????</strong>
              </Alert>
            ) : null}
            {errorprice ? (
              <Alert severity="error">
                <AlertTitle>Error</AlertTitle>
                <strong>?????? ????????? 0????????? ????????? ??????????????????</strong>
              </Alert>
            ) : null}
          </Dialog>
          {/* ?????? */}
          <Button fullWidth onClick={handleClickOpendelete}>
            ??????
          </Button>
          <Dialog
            open={opendelete}
            onClose={handleClosedelete}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
          >
            <DialogContent>
              <DialogContentText id="alert-dialog-description">
                ?????? ???????????? ?????????????????????????
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleClosedelete}>cancel</Button>
              <Button onClick={deleteitem} autoFocus>
                delete
              </Button>
            </DialogActions>
          </Dialog>
        </div>
      ) : (
        <Button fullWidth onClick={handleClickOpen}>
          {props.price} coin ??????
        </Button>
      )}
      <Dialog
        onClose={handleClose}
        aria-labelledby="customized-dialog-title"
        open={open}
      >
        <DialogTitle id="customized-dialog-title" onClose={handleClose}>
          ????????????
        </DialogTitle>
        <DialogContent dividers>
          <div style={{ width: "500px", height: "100%" }}>
            {userAddress ? null : (
              <Button autoFocus onClick={makewallet} color="primary" fullWidth>
                <h3 style={{ color: "black" }}>?????? ??????????????????</h3>
              </Button>
            )}

            {loading ? (
              <LoadingButton fullWidth>
                <div id="floatingCirclesG">
                  <div className="f_circleG" id="frotateG_01" />
                  <div className="f_circleG" id="frotateG_02" />
                  <div className="f_circleG" id="frotateG_03" />
                  <div className="f_circleG" id="frotateG_04" />
                  <div className="f_circleG" id="frotateG_05" />
                  <div className="f_circleG" id="frotateG_06" />
                  <div className="f_circleG" id="frotateG_07" />
                  <div className="f_circleG" id="frotateG_08" />
                </div>
              </LoadingButton>
            ) : (
              <Button autoFocus onClick={pay2} color="primary" fullWidth>
                <h1 style={{ color: "black" }}>pay</h1>
              </Button>
            )}
            <Button
              autoFocus
              onClick={() => {
                setOpen(false);
                setloading(false);
              }}
              color="primary"
              fullWidth
            >
              <h4 style={{ color: "black" }}>cancel</h4>
            </Button>
            <h6>* ?????????, ?????? ???????????? ???????????????.</h6>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
export default MarketBuyItem;
