var express = require('express');
var router = express.Router();

const {fetchAllGoods} = require('./utility/goods');
/* GET home page. */
router.post('/', function(req, res, next) {
  var eNo=req.body.eNo;

  fetchAllGoods(eNo).then(d => {
    console.log(eNo)
    console.log(d.data)
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