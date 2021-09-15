var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

//web api
var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var homePage = require('./routes/homePage');
var loginForm = require('./routes/loginForm');
var addForm = require('./routes/addForm');
var login = require('./routes/login');
var logout = require('./routes/logout');
var updateExhibition = require('./routes/updateExhibition');
var updateGoods = require('./routes/updateGoods');
var addE = require('./routes/addE');
var addG = require('./routes/addG');
var updateE = require('./routes/updateE');
var updateG = require('./routes/updateG');
var searchE = require('./routes/searchE');
var searchG = require('./routes/searchG');
var searchExhibition= require('./routes/searchExhibition');
var searchGoods = require('./routes/searchGoods');
var verifyExhibition= require('./routes/verifyExhibition');
var verifyList= require('./routes/verifyList');
var verify= require('./routes/verify');
//app api
var appLogin = require('./routes/appLogin');
var appAddMember = require('./routes/appAddMember');
var appMemberProfile = require('./routes/appMemberProfile');
var appUpdateMember = require('./routes/appUpdateMember');
var appUpdateMemberPwd = require('./routes/appUpdateMemberPwd');
var appAllGoods = require('./routes/appAllGoods');
var appAllExhibition = require('./routes/appAllExhibition');
var appMemExhibition = require('./routes/appMemExhibition');
var appAllMemExhibition = require('./routes/appAllMemExhibition');
var appLoginExhibition = require('./routes/appLoginExhibition');
var appAddExhibition = require('./routes/appAddExhibition');
var appAddS = require('./routes/appAddS');
var appDeleteS = require('./routes/appDeleteS');
var appUpdateS = require('./routes/appUpdateS');
var appAllShoppingcart = require('./routes/appAllShoppingcart');
var appOrderRecord = require('./routes/appOrderRecord');
var appOneOrder = require('./routes/appOneOrder');
var appAddOrderRecord = require('./routes/appAddOrderRecord');
var appAddOrderDetail = require('./routes/appAddOrderDetail');
var app = express();
var cors = require('cors');
app.use(cors());


// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

/*var session = require('express-session');
app.use(session({secret: 'HOW健康', cookie: { maxAge: 6000000 },resave:true,saveUninitialized: true}));*/

//web api
app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/homePage', homePage);
app.use('/loginForm', loginForm);
app.use('/addE', addE);
app.use('/addG', addG);
app.use('/login', login);
app.use('/logout', logout);
app.use('/addForm', addForm);
app.use('/updateE', updateE);
app.use('/updateG', updateG);
app.use('/updateExhibition', updateExhibition);
app.use('/updateGoods', updateGoods);
app.use('/searchE', searchE);
app.use('/searchG', searchG);
app.use('/searchExhibition', searchExhibition);
app.use('/searchGoods', searchGoods);
app.use('/verifyExhibition', verifyExhibition);
app.use('/verifyList', verifyList);
app.use('/verify', verify);
//app api
app.use('/appLogin', appLogin);
app.use('/appAddMember', appAddMember);
app.use('/appMemberProfile', appMemberProfile);
app.use('/appUpdateMember', appUpdateMember);
app.use('/appUpdateMemberPwd', appUpdateMemberPwd);
app.use('/appAllGoods', appAllGoods);
app.use('/appAllExhibition', appAllExhibition);
app.use('/appMemExhibition', appMemExhibition);
app.use('/appAllMemExhibition', appAllMemExhibition);
app.use('/appLoginExhibition', appLoginExhibition);
app.use('/appAddExhibition', appAddExhibition);
app.use('/appAddS', appAddS);
app.use('/appDeleteS', appDeleteS);
app.use('/appUpdateS', appUpdateS);
app.use('/appAllShoppingcart', appAllShoppingcart);
app.use('/appOrderRecord', appOrderRecord);
app.use('/appOneOrder', appOneOrder);
app.use('/appAddOrderRecord', appAddOrderRecord);
app.use('/appAddOrderDetail', appAddOrderDetail);

app.use(express.static('public/file'));

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

process.on('unhandledRejection', error => {
  console.error('unhandledRejection', error);
  process.exit(1) // To exit with a 'failure' code
});
module.exports = app;
