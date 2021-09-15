var express = require('express');
var router = express.Router();

//增加引用函式
const {add} = require('./utility/order_record');
const {test} = require('./utility/order_record');

//---------------------------
//接收POST請求
router.post('/', function(req, res, next) {
    var memNo = req.body.memNo;          
    var orTime =Date.now()
    
    var newData={
        memNo:memNo,
        orTime:orTime
    } 
    
    add(newData).then(d => {
        console.log(newData)
        test(memNo).then(d => {
            if (d==0){
                console.log('d!=null')
                res.status(201).json({
                    status:'success',
                    data:d.data
                })
            }else{   
                console.log('d==null')
                res.status(400).json({
                    status:'fail',
                    data:'Error!'
                })
            }  
        })
    })
});

module.exports = router;