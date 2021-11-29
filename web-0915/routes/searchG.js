var express = require('express');
var router = express.Router();

const {searchG} = require('./utility/goods');
/* GET home page. */
router.get('/', function(req, res, next) {
  var keyword=req.query.keyword;

  searchG(keyword).then(d => {
    console.log(d.data.length)
    if(d.data.length > 0){
        console.log(d.data)
        res.render('searchGoods', {items:d.data});
    }else{
        res.render('notfound');  //導向找不到頁面
    } 
  })
});

//匯出
module.exports = router;