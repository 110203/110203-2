var express = require('express');
var router = express.Router();

var moment = require('moment');
const {AllOrderRecord} = require('./utility/order_record');

/* GET home page. */
router.get('/', function(req, res, next) {

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

});

module.exports = router;
