var express = require('express');
var router = express.Router();

//增加引用函式
const {login} = require('./utility/user');

//接收POST請求
router.post('/', function(req, res, next) {
    var memeNo = req.body.memNo;                 //取得帳號
    var memPassword = req.body.memPassword;
    var level= 1
    console.log(memeNo,memPassword,level)
    login(memeNo,memPassword,level).then(d => {
        if (d.data.length> 0){          
            res.render('addForm');  //傳至登入失敗
        }else{
            res.render('error');   //導向使用者
        }  
    })
});

module.exports = router;