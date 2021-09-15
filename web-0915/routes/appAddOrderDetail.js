var express = require('express');
var router = express.Router();

//增加引用函式
const {add} = require('./utility/order_detail');

//---------------------------
//接收POST請求
router.post('/', function(req, res, next) {
    var orNo = req.body.orNo;          
    var gNo = req.body.gNo;
    var gAmount = req.body.gAmount
    
    var newData={
        orNo:orNo,
        gNo:gNo,
        gAmount:gAmount
    } 
    
    add(newData).then(d => {
        console.log(newData)
        if (d==0){
            console.log('d!=null')
            res.status(201).json({
                status:'success'
            })
        }else{   
            console.log('d==null')
            res.status(400).json({
                status:'fail'
            })
        }  
    })
});

module.exports = router;