'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

//------------------------------------------
//執行資料庫動作的函式-新增產品資料
//------------------------------------------
var fetchMemOrder = async function(memNo){
    var result={};
	
    await query('SELECT * FROM order_record where memNo = ?',memNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
    return result;
}
//------------------------------------------
var fetchOneOrder = async function(orNo){
    var result={};
	
    await query('select a.orNo,memNo,orTime,c.gName,c.gAmount,c.price,(c.gAmount*c.price)as total from order_record a,order_detail b,goods cwhere a.orNo=b.orNo and b.gNo=c.gNo and a.orNo=?',orNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
    return result;
}
var add = async function(newData){
    var result;

    await query('INSERT INTO order_record SET ?',newData)
        .then((data) => {
            result = 0;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}
var returnOrNo = async function(memNo){
    var result={};
	
    await query('SELECT *  FROM `110203`.order_record where memNo=? order by orNo  desc limit 1',memNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
    return result;
}
var AllOrderRecord = async function(){
    var result={};
	
    await query('SELECT * FROM Gcp110203.order_record where state!=4')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
    return result;
}
var OneOrderRecord = async function(orNo){
    var result={};
	
    await query('SELECT * FROM Gcp110203.order_record where orNo=?',orNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
    return result;
}
var verifyO = async function(state,orNo){
    var results={};

    await query('UPDATE order_record SET state=? WHERE orNo =?',[state,orNo])
        .then((data) => {
            results = data.affectedRows;  
        }, (error) => {
            results = -1;
        });  
    return results;
}
//匯出
module.exports = {fetchMemOrder,fetchOneOrder,add,returnOrNo,AllOrderRecord,OneOrderRecord,verifyO};