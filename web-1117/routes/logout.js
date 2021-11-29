var express = require('express');
var router = express.Router();

//增加引用函式
const user = require('./utility/user');

//接收POST請求
router.get('/', function(req, res, next) {
    req.body.memno = null;
    req.body.memPassword = null;           
    res.render('index');  //傳至登出    
});

module.exports = router;