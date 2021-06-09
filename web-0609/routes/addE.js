var express = require('express');
var router = express.Router();

//增加引用函式
const {add} = require('./utility/exhibition');
var crypto = require('crypto');
var buf = crypto.randomBytes(32);
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
    console.log(req.file);
    var memNo = req.body.memNo; 
    var eFile             
    var eName = req.body.eName;             
    var introdution = req.body.introdution;
    var startTime = req.body.startTime; 
    var endTime = req.body.endTime;  
    var eImage= req.file.filename; 
    var eType = req.body.eType;  
    var ePin = buf.toString('base64').substr(0, 10);;     

    var newData={
        memNo:memNo,
        eFile:eFile,
        eName:eName,
        introdution:introdution,
        startTime:startTime,
        endTime:endTime,
        eImage:eImage,
        eType:eType,
        ePin:ePin
    } 
    console.log(newData)
    add(newData).then(d => {
        if (d==0){
            res.render('addForm');  //傳至成功頁面
        }else{
            res.render('error');     //導向錯誤頁面
        }  
    })
});

module.exports = router;