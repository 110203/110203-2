var express = require('express');
var router = express.Router();

//增加引用函式
const {verify} = require('./utility/exhibition');
var moment = require('moment');
const {verifyExhibition} = require('./utility/exhibition');
//接收POST請求
router.post('/',function(req, res, next){
    var eNo = req.body.eNo; 
    var eCheck = req.body.eCheck; 
    console.log(eNo,eCheck)
    if(eCheck=='審核不通過'){
      eCheck=1
    }else if(eCheck=='審核通過'){
      eCheck=2
    }else if(eCheck=='開始建置'){
      eCheck=3
    }else if(eCheck=='建置失敗'){
      eCheck=4
    }else if(eCheck=='建置成功'){
      eCheck=5
    }else if(eCheck=='確定上架'){
      eCheck=6
    }else if(eCheck=='結束'){
      eCheck=7
    }else if(eCheck=='要求補件'){
      eCheck=100
    }else{
      eCheck=999
    }
    console.log(eNo,eCheck)
    verify(eCheck,eNo).then(d => {
        console.log(d)
        verifyExhibition().then(d => {
          console.log(d.data)
          if(d.data.length > 0){
              for(var i =0; i<d.data.length;i++){
                d.data[i].startTime=moment(d.data[i].startTime).format("YYYY.MM.DD")
                d.data[i].endTime=moment(d.data[i].endTime).format("YYYY.MM.DD");
              }  
              res.render('verifyList', {items:d.data});
          }else{
              res.render('notfound');  //導向找不到頁面
          } 
        })
    })
});

module.exports = router;