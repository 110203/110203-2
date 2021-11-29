var express = require('express');
var router = express.Router();

//增加引用函式
const {fetchOneExhibition} = require('./utility/exhibition');
var moment = require('moment');
//接收GET請求
router.get('/:eNo', function(req, res, next) {
    var eNo = req.params.eNo;

    fetchOneExhibition(eNo).then(d => {      
        if (d.data.length > 0){          
            var data = {
                eNo: d.data[0].eNo,
                memNo: d.data[0].memNo,
                eName:d.data[0].eName,
                introdution:d.data[0].introdution,
                startTime: moment(d.data[0].startTime).format("YYYY-MM-DD"),
                endTime: moment(d.data[0].endTime).format("YYYY-MM-DD"),
                eCheck:d.data[0].eCheck
            }
            console.log(data)
            res.render('verifyExhibition', {items:data});  //將資料傳給更新頁面
        }else{
            res.render('notFound');  //導向找不到頁面
        }  
    })
});

module.exports = router;