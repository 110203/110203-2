var express = require('express');
var router = express.Router();

//增加引用函式
const {queryCartExist,add,featchOneShoppingcart,update} = require('./utility/shopping_cart');
const {fetchOneGoods} = require('./utility/goods');

//---------------------------
//接收POST請求
router.post('/', function(req, res, next) {
    var memNo = req.body.memNo;          
    var gNo = req.body.gNo;             
    var gAmount=req.body.gAmount;
    
    var newData={
        memNo:memNo,
        gNo:gNo,
        gAmount:gAmount
    } 
    var dataGAmount = 0;
    fetchOneGoods(gNo).then(d => {
        dataGAmount = d.data[0].gAmount;
    })

    queryCartExist(newData).then(x => {
        if(x.code==0 && gAmount<=dataGAmount){
            console.log('x==0')
            add(newData).then(d => {
                if (d==0){
                    res.status(201).json({
                        status:'add success'
                    })
                }else{   
                    res.status(400).json({
                        status:'add fail'
                    })
                }  
            })
        }else if(x.code==1 && (x.data[0].gAmount+gAmount)<=dataGAmount){
            console.log('x==1')
            update(newData).then(d => {
                if (d==0){
                    featchOneShoppingcart(newData).then(i => {
                        console.log("47",i)
                        res.status(201).json({
                            status:'update success',
                            gAmount:i.data[0].gAmount
                        })
                    })
                }else{   
                    res.status(400).json({
                        status:'update fail'
                    })
                }  
            })
        }else{
            console.log('query error')
            res.status(400).json({
                status:'fail'
            })
        }
    })
    
});

module.exports = router;