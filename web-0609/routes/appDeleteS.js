var express = require('express');
var router = express.Router();

//增加引用函式
const {deleteS} = require('./utility/shopping_cart');

//接收POST請求
router.post('/', function(req, res, next) {
    var scNo = req.body.scNo;   //取得產品編號
 
    deleteS(scNo).then(d => {
        if(d.code==0){
            console.log('d!=null')
            res.status(201).json({
                status:'success'
            })
        }else{
            console.log('d==null')
            res.status(400).json({
                status:'fail'
            })    //導向錯誤頁面
        }
    })    
});

module.exports = router;