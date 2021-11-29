var express = require('express');
var router = express.Router();

//增加引用函式
const {add, returnOrNo} = require('./utility/order_record');

//---------------------------
//接收POST請求
router.post('/', function(req, res, next) {
    var memNo = req.body.memNo;          
    var orTime = new Date();
    var orAddress = req.body.address;
    var orPayment = req.body.payment;
    var orTel = req.body.tel;
    
    var newData={
        memNo:memNo,
        orTime:orTime,
        orAddress:orAddress,
        orPayment:orPayment,
        orTel:orTel
    } 
    
    add(newData).then(c => {
        console.log(newData)
        if(c==0){
            returnOrNo(memNo).then(d => {
                if (d.code==0){
                    console.log('d!=null')
                    res.status(201).json({
                        status:'success',
                        orNo:d.data[0].orNo
                    })
                }else{   
                    console.log('d==null')
                    res.status(400).json({
                        status:'fail',
                        orNo:'Error!'
                    })
                }  
            })
        }else{
            res.status(400).json({
                status:'fail',
                orNo:'Error!'
            })
        }
    })
});

module.exports = router;