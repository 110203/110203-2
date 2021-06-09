var express = require('express');
var router = express.Router();

//增加引用函式
const {add} = require('./utility/shopping_cart');


//---------------------------
//接收POST請求
router.post('/', function(req, res, next) {
    var memNo = req.body.memNo;          
    var gNo = req.body.gNo;             
    var gAmount=req.body.gAmount;
    
    var newData={
        memNo:memNo,
        gNo:gNo,
        gAmount:gAmount
    } 
    
    add(newData).then(d => {
        console.log(newData)
            if (d.cade==0){
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