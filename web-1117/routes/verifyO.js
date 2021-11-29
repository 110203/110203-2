var express = require('express');
var router = express.Router();

//增加引用函式
const {verifyO} = require('./utility/order_record');
var moment = require('moment');
const {AllOrderRecord} = require('./utility/order_record');
//接收POST請求
router.post('/',function(req, res, next){
    var orNo = req.body.orNo; 
    var state = req.body.state; 
    console.log(orNo,state)
    if(state=='準備出貨中'){
      state=1
    }else if(state=='出貨'){
      state=2
    }else if(state=='商品已送達'){
      state=3
    } else{
      state=4
    }
    console.log(orNo,state)
    verifyO(state,orNo).then(d => {
        console.log(d)
        AllOrderRecord().then(d => {
          console.log(d.data)
          if(d.data.length > 0){
              for(var i =0; i<d.data.length;i++){
                d.data[i].orTime=moment(d.data[i].orTime).format("YYYY-MM-DD.HH:mm:ss")
              }  
              res.render('verifyOrderRecordList', {items:d.data});
          }else{
              res.render('notfound');  //導向找不到頁面
          } 
        })
    })
});

module.exports = router;