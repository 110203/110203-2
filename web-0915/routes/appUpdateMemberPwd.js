var express = require('express');
var router = express.Router();

//增加引用函式
const {appUpdateMemPwd} = require('./utility/user');

//---------------------------
//接收POST請求
router.post('/', function(req, res, next){

    var memNo = req.body.memNo;
    var memOldPassword = req.body.memOldPassword;
    var memNewPassword = req.body.memNewPassword;

    var newData={
        memNo:memNo,
        memOldPassword:memOldPassword,
        memNewPassword:memNewPassword
    }

    appUpdateMemPwd(newData).then(d => {
        if (d.code==0&&d.data==1){
            res.status(201).json({
                status:'success'
            })
        }else{
            res.status(400).json({
                status:'fail'
            })
        }
    })
});

module.exports = router;