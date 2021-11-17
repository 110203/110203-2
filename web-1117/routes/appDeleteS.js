var express = require('express');
var router = express.Router();

//增加引用函式
const {deleteS} = require('./utility/shopping_cart');

//接收POST請求
router.post('/', function(req, res, next) {
    var memNo = req.body.memNo;
    var gNo = req.body.gNo;

    var newData={
        memNo:memNo,
        gNo:gNo
    } 
 
    deleteS(newData).then(d => {
        if(d==0){
            console.log('deleteS success')
            res.status(201).json({
                status:'success'
            })
        }else{
            console.log('deleteS fail')
            res.status(400).json({
                status:'fail'
            })
        }
    })    
});

module.exports = router;