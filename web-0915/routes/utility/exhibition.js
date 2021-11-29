'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

//------------------------------------------
//執行資料庫動作的函式-新增產品資料
//------------------------------------------
var add = async function(newData){
    var result;

    await query('INSERT INTO exhibition SET ?',newData)
        .then((data) => {
            result = 0;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}
var searchE = async function(keyword){
    var result={};
	
    await query('SELECT * FROM exhibition where eName like ?   and eCheck=6','%'+keyword+'%')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
var fetchOneExhibition = async function(eNo){
    var result={};
	
    await query('SELECT * FROM exhibition where eNo =  ?',eNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
var update = async function(newData, eNo){
    var results={};

    await query('UPDATE exhibition SET eName=?, eFile=?, introdution=?,startTime=?,endTime=?,eImage=?,eType=?,eDelete=? WHERE eNo = ?', [newData.eName, newData.eFile, newData.introdution,newData.startTime, newData.endTime, newData.eImage, newData.eType,newData.eDelete, eNo])
        .then((data) => {
            results = data.affectedRows;  
        }, (error) => {
            results = -1;
        });
    return results;
}
var fetchAllExhibition = async function(){
    var result={};
	
    await query('SELECT * FROM exhibition where eDelete=0 and eCheck=6')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
var memExhibition = async function(memNo){
    var result={};
	
    await query('SELECT * FROM exhibition where memNo = ? and eDelete=0 and login=1 and eCheck=6',memNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}

var memExhibition2 = async function(memNo){
    var result={};
	
    await query('SELECT * FROM exhibition where memNo = ? and eDelete=0',memNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}

var loginExhibition = async function(memNo,ePin){
    var result={};
	
    await query('UPDATE exhibition SET login=1 where eNo=(select t2.eNo from(SELECT eNo FROM exhibition where memNo=? and ePin=? and login=0) t2)',[memNo,ePin])
        .then((data) => {
            result = {code:0, data:data.affectedRows};  
        }, (error) => {
            result = {code:-1};
            console.log(error)
        });
		
    return result;
}
var verifyExhibition = async function(){
    var result={};
	
    await query('SELECT * FROM exhibition where eCheck=0 or eCheck=2 or eCheck=3 or eCheck=5 or eCheck=6 or eCheck=100 ')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}
var verify = async function(eCheck,eNo){
    var results={};

    await query('UPDATE exhibition SET eCheck=? WHERE eNo =?',[eCheck,eNo])
        .then((data) => {
            results = data.affectedRows;  
        }, (error) => {
            results = -1;
        });  
    return results;
}
//匯出
module.exports = {add,searchE,fetchOneExhibition,update,fetchAllExhibition,memExhibition,memExhibition2,loginExhibition,verifyExhibition,verify};