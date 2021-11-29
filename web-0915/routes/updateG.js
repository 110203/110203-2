var express = require('express');
var router = express.Router();

//增加引用函式
const {update} = require('./utility/goods');
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
router.post('/', upload.single( "gImage2D"),function(req, res, next){
    var gNo = req.body.gNo; 
    console.log(gNo)
    var newData={
        gName:req.body.gName, 
        introdution:req.body.introdution,
        gAmount:req.body.gAmount,
        price:req.body.price,
        gImage2D:req.file.filename,
        gDelete:req.body.gDelete    
    } 
    console.log(newData)
    update(newData,gNo).then(d => {
        if (d>=0){
            res.render('addForm');  //傳至成功頁面
        }else{
            res.render('error');     //導向錯誤頁面
        }  
    })
});

module.exports = router;