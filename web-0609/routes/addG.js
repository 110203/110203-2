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
var maxSize=2000*2000;  //設定最大可接受圖片大小

var upload = multer({storage:storage})
//---------------------------
//接收POST請求
router.post('/', upload.single('gImage2d'),function(req, res, next) {
     // 如果有選擇圖片
    if (typeof req.file != 'undefined'){
        // 傳入檔案不可超過maxSize
        if(req.file.size > maxSize){
            res.render('error');  //圖片過大
            return;
        }                      
    }
    var eNo = req.body.eNo;          
    var gName = req.body.gName;             
    var introdution = req.body.introdution;
    var gImage2d=req.file.filename; 
    var gImage3d; 
    var voice; 
    var gAmount=req.body.gAmount;
    var price=req.body.price;
     // 如果有選擇圖片
    /*if (typeof req.file != 'undefined'){
        eImage=req.file.filename;   //取得上傳照片名稱
    }*/
    // 建立一個新資料物件
    var newData={
        eNo:eNo,
        gName:gName,
        introdution:introdution,
        gAmount:gAmount,
        price:price,
        voice:voice,
        gImage2d:gImage2d,
        gImage3d:gImage3d
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