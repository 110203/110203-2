var express = require('express');
var router = express.Router();

//增加引用函式
const {add} = require('./utility/user');

//---------------------------
//接收POST請求
router.post('/', function(req, res, next){

    var memNo = req.body.memNo;                         
    var memPassword = req.body.memPassword;
    var memName = req.body.memName; 
    var address = req.body.address; 
    var phone = req.body.phone;  

    var newData={
        memNo:memNo,
        memPassword:memPassword,
        memName:memName,
        address:address,
        phone:phone
    } 
    add(newData).then(d => {
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