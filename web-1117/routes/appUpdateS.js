var express = require('express');
var router = express.Router();

//增加引用函式
const {update} = require('./utility/shopping_cart');


//---------------------------
//接收POST請求
router.post('/', function(req, res, next) {
    var newData={
        gAmount:req.body.gAmount,  
        gNo:req.body.gNo,
        memNo:req.body.memNo
    }           
    update(newData).then(d => {
        console.log(newData)
        if (d.code==0){
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