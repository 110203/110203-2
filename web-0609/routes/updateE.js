var express = require('express');
var router = express.Router();

//增加引用函式
const {update} = require('./utility/exhibition');
const multer  = require('multer');

// 宣告上傳存放空間及檔名更改
var storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'public/file');
    },

    filename: function (req, file, cb) {
        cb(null, Date.now()+"--"+file.originalname);
    }
})
// 產生multer的上傳物件

var upload = multer({storage:storage})
//---------------------------
//接收POST請求
router.post('/', upload.single( "eImage"),function(req, res, next){
    var eNo = req.body.eNo; 
    console.log(eNo)
    var newData={
        eName:req.body.eName, 
        introdution:req.body.introdution,
        startTime:req.body.startTime,
        endTime:req.body.endTime,
        eImage:req.file.filename,
        eType:req.body.eType,
        eDelete:req.body.eDelete  
    } 
    console.log(newData)
    update(newData,eNo).then(d => {
        if (d>=0){
            res.render('addForm');  //傳至成功頁面
        }else{
            res.render('error');     //導向錯誤頁面
        }  
    })
});

module.exports = router;