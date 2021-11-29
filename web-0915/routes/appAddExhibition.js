var express = require('express');
var router = express.Router();

//增加引用函式
const {add} = require('./utility/exhibition');
var crypto = require('crypto');
var buf = crypto.randomBytes(32);

//---------------------------
//接收POST請求
router.post('/', function(req, res, next) {
    var memNo = req.body.memNo;  
    var eName = req.body.eName;             
    var introdution = req.body.introdution;
    var startTime = req.body.startTime; 
    var endTime = req.body.endTime;  
    var eType = req.body.eType;         
    var ePin = buf.toString('base64').substr(0, 10);; 
    var style= req.body.style 
    var newData={
        memNo:memNo,
        eName:eName,
        introdution:introdution,
        startTime:startTime,
        endTime:endTime,
        eType:eType,
        ePin:ePin,
        style:style
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