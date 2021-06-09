'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

//---------------------------------------------
// 使用者登入
//---------------------------------------------
var login = async function(memeNo,memPassword,level){   
    var result=[];
    //取得會員資料
    await query('SELECT * FROM  `member` WHERE memNo=? and memPassword=? and level=?', [memeNo,memPassword,level])
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
    //回傳物件
    console.log(result)
    return result;
}
module.exports = {login};
