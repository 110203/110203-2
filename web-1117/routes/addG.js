var express = require('express');
var router = express.Router();

//增加引用函式
const {add} = require('./utility/goods');
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
router.post('/', upload.single('gImage2d'),function(req, res, next) {
    console.log(req.file);
    var eNo = req.body.eNo;          
    var gName = req.body.gName;             
    var introdution = req.body.introdution;
    var gImage2d=req.file.filename; 
    var gAmount=req.body.gAmount;
    var price=req.body.price;

    var newData={
        eNo:eNo,
        gName:gName,
        introdution:introdution,
        gImage2d:gImage2d,
        gAmount:gAmount,
        price:price
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