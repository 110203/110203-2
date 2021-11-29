'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

//------------------------------------------
//執行資料庫動作的函式-新增產品資料
//------------------------------------------
var queryCartExist = async function(newData){
    var result;

    await query('SELECT * FROM shopping_cart WHERE memNo=? AND gNo=?',[newData.memNo,newData.gNo])
        .then((data) => {
            console.log(data)
            if(data==""){
                result = {code:0};
            }else{
                result = {code:1, data:data}; 
            }
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
var add = async function(newData){
    var result;

    await query('INSERT INTO shopping_cart SET ?',newData)
        .then((data) => {
            result = 0;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}
var fetchAllShoppingcart = async function(memNo){
    var result={};
	
    await query('SELECT a.scNo, a.gNo, a.gAmount, b.gName, b.price, b.gImage2D, b.gAmount as stock FROM shopping_cart a, goods b where a.gNo=b.gNo and memNo=? and gDelete<>1',memNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
var featchOneShoppingcart = async function(newData){
    var results={};

    await query('SELECT gAmount FROM shopping_cart WHERE gNo=? AND memNo=?', [newData.gNo,newData.memNo])
        .then((data) => {
            results = {code:0, data:data};
        }, (error) => {
            console.log(error)
            results = -1;
        });
    return results;
}
var update = async function(newData){
    var results={};

    await query('UPDATE shopping_cart SET gAmount=gAmount+? WHERE gNo=? AND memNo=?', [newData.gAmount,newData.gNo,newData.memNo])
        .then((data) => {
            results = 0;
        }, (error) => {
            console.log(error)
            results = -1;
        });
    return results;
}
var deleteS = async function(newData){
    var result;

    await query('DELETE FROM shopping_cart WHERE memNo=? AND gNo=?', [newData.memNo, newData.gNo])
        .then((data) => {
            result = 0;
        }, (error) => {
            result = -1;
        });
    return result;
}
//匯出
module.exports = {queryCartExist,add,fetchAllShoppingcart,featchOneShoppingcart,update,deleteS};