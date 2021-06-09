'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

//------------------------------------------
//執行資料庫動作的函式-新增產品資料
//------------------------------------------
var add = async function(newData){
    var result;

    await query('INSERT INTO goods SET ?',newData)
        .then((data) => {
            result = 0;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}
var searchG = async function(keyword){
    var result={};
	
    await query('SELECT * FROM goods where gName like ? and gDelete=0','%'+keyword+'%')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
var fetchOneGoods = async function(gNo){
    var result={};
	
    await query('SELECT * FROM goods where gNo =  ?',gNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
var update = async function(newData, gNo){
    var results={};

    await query('UPDATE goods SET gName=?, introdution=?, gAmount=?,price=?,voice=?,gImage2D=?,gImage3D=?,gDelete=? WHERE gNo = ?', [newData.gName, newData.introdution,newData.gAmount,newData.price,newData.voice, newData.gImage2D, newData.gImage3D,newData.gDelete, gNo])
        .then((data) => {
            results = data.affectedRows;  
        }, (error) => {
            results = -1;
        });
    return results;
}
var fetchAllGoods = async function(eNo){
    var result={};
	
    await query('SELECT * FROM goods where eNo = ? and gDelete=0',eNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
//匯出
module.exports = {add,searchG,fetchOneGoods,update,fetchAllGoods};