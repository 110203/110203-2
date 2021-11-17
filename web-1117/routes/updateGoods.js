var express = require('express');
var router = express.Router();

//增加引用函式

const {fetchOneGoods} = require('./utility/goods');

//接收GET請求
router.get('/:gNo', function(req, res, next) {
    var gNo = req.params.gNo;

    fetchOneGoods(gNo).then(d => {      
        if (d.data.length > 0){          
            var data = {
                gNo: d.data[0].gNo,
                eNo: d.data[0].eNo,
                gName:d.data[0].gName,
                introdution:d.data[0].introdution,
                gAmount:d.data[0].gAmount,
                price:d.data[0].price,
                voice:d.data[0].voice,
                gImage2D:d.data[0].gImage2D,
                gImage3D:d.data[0].gImage3D,
                gDelete:d.data[0].gDelete
            }
            res.render('updateGoods', {items:data});  //將資料傳給更新頁面
        }else{
            res.render('notFound');  //導向找不到頁面
        }  
    })
});

module.exports = router;