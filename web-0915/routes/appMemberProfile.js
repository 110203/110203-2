var express = require('express');
var router = express.Router();

const {appMemProfile} = require('./utility/user');
/* GET home page. */
router.post('/', function(req, res, next) {
  var memeNo = req.body.memNo;                 //取得帳號
  
  appMemProfile(memeNo).then(d => {
    if(d.code==0&&d.data!=""){
      res.status(201).json({
        status:'success',
        data:d.data
      })
    }else{
      res.status(400).json({
        status:'fail',
        data:'Error!'
      })
    }  
  })
});

//匯出
module.exports = router;