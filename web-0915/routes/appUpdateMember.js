var express = require('express');
var router = express.Router();

//增加引用函式
const {appUpdateMem} = require('./utility/user');

//---------------------------
//接收POST請求
router.post('/', function(req, res, next){

    var memNo = req.body.memNo;
    var memName = req.body.memName;
    var address = req.body.address;
    var phone = req.body.phone;

    var newData={
        memNo:memNo,
        memName:memName,
        address:address,
        phone:phone
    }

    appUpdateMem(newData).then(d => {
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