var express = require('express');
var router = express.Router();

//增加引用函式
const {OneOrderRecord} = require('./utility/order_record');
var moment = require('moment');
//接收GET請求
router.get('/:orNo', function(req, res, next) {
    var orNo = req.params.orNo;

    OneOrderRecord(orNo).then(d => {      
        if (d.data.length > 0){          
            var data = {
                orNo: d.data[0].orNo,
                memNo: d.data[0].memNo,
                orTime: moment(d.data[0].orTime).format("YYYY-MM-DD.HH:mm:ss"),
                state:d.data[0].state,
                orAddress:d.data[0].orAddress,
                orPayment:d.data[0].orPayment,
                orTel:d.data[0].orTel,
                orTotalPrice:d.data[0].orTotalPrice
            }
            console.log(data)
            res.render('verifyOrderRecord', {items:data});  //將資料傳給更新頁面
        }else{
            res.render('notFound');  //導向找不到頁面
        }  
    })
});

module.exports = router;