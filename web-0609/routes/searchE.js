var express = require('express');
var router = express.Router();

var moment = require('moment');
const {searchE} = require('./utility/exhibition');
/* GET home page. */
router.get('/', function(req, res, next) {
  var keyword=req.query.keyword;

  searchE(keyword).then(d => {
    console.log(d.data)
    if(d.data.length > 0){
        for(var i =0; i<d.data.length;i++){
          d.data[i].startTime=moment(d.data[i].startTime).format("YYYY-MM-DD")
          d.data[i].endTime=moment(d.data[i].endTime).format("YYYY-MM-DD");
        }  
        res.render('searchExhibition', {items:d.data});
    }else{
        res.render('notfound');  //導向找不到頁面
    } 
  })
});

//匯出
module.exports = router;