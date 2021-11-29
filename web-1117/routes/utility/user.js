'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');


var add = async function(newData){
    var result;

    await query('INSERT INTO member SET ?',newData)
        .then((data) => {
            result = 0;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}
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
var appLogin = async function(memeNo,memPassword){   
    var result=[];
    //取得會員資料
    await query('SELECT * FROM member WHERE memNo=? AND memPassword=? AND memDelete=0', [memeNo,memPassword])
        .then((data) => {
            result = {code:0, data:data};
        }, (error) => {
            result = {code:-1};
        });
    //回傳物件
    console.log(result)
    return result;
}
var appMemProfile = async function(memeNo,memPassword){   
    var result=[];
    //取得會員資料
    await query('SELECT * FROM member WHERE memNo=?', [memeNo])
        .then((data) => {
            result = {code:0, data:data};
        }, (error) => {
            result = {code:-1};
        });
    return result;
}
var appUpdateMem = async function(newData){
    var results;

    await query('UPDATE member SET memName=?,address=?,phone=? WHERE memNo=?', [newData.memName,newData.address,newData.phone,newData.memNo])
        .then((data) => {
            results = {code:0, data:data.affectedRows};  
        }, (error) => {
            results = {code:-1};
        });
    return results;
}
var appUpdateMemPwd = async function(newData){
    var results;

    await query('UPDATE member SET memPassword=? WHERE memNo=? AND memPassword=?', [newData.memNewPassword,newData.memNo,newData.memOldPassword])
        .then((data) => {
            results = {code:0, data:data.affectedRows};  
        }, (error) => {
            results = {code:-1};
        });
    return results;
}
module.exports = {add,login,appLogin,appMemProfile,appUpdateMem,appUpdateMemPwd};
