var express = require('express');
var router = express.Router();

const {fetchMemOrder} = require('./utility/order_record');
/* GET home page. */
router.post('/', function(req, res, next) {
  var memNo=req.body.memNo;
  
  fetchMemOrder(memNo).then(d => {
    if (d.code==0){
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
});

//匯出
module.exports = router;