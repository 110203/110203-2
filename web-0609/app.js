var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

//web api
var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var addForm = require('./routes/addForm');
var login = require('./routes/login');
var updateExhibition = require('./routes/updateExhibition');
var updateGoods = require('./routes/updateGoods');
var addE = require('./routes/addE');
var updateE = require('./routes/updateE');
var updateG = require('./routes/updateG');
var addG = require('./routes/addG');
var searchE = require('./routes/searchE');
var searchG = require('./routes/searchG');
var searchExhibition= require('./routes/searchExhibition');
var searchGoods = require('./routes/searchGoods');
//app api
var appAllGoods = require('./routes/appAllGoods');
var appAllExhibition = require('./routes/appAllExhibition');
var appMemExhibition = require('./routes/appMemExhibition');
var appLoginExhibition = require('./routes/appLoginExhibition');
var appAddS = require('./routes/appAddS');
var appDeleteS = require('./routes/appDeleteS');
var appUpdateS = require('./routes/appUpdateS');
var appAllShoppingcart = require('./routes/appAllShoppingcart');

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
app.use('/addE', addE);
app.use('/login', login);
app.use('/addForm', addForm);
app.use('/updateE', updateE);
app.use('/updateG', updateG);
app.use('/addG', addG);
app.use('/updateExhibition', updateExhibition);
app.use('/updateGoods', updateGoods);
app.use('/searchE', searchE);
app.use('/searchG', searchG);
app.use('/searchExhibition', searchExhibition);
app.use('/searchGoods', searchGoods);
//app api
app.use('/appAllGoods', appAllGoods);
app.use('/appAllExhibition', appAllExhibition);
app.use('/appMemExhibition', appMemExhibition);
app.use('/appLoginExhibition', appLoginExhibition);
app.use('/appAddS', appAddS);
app.use('/appDeleteS', appDeleteS);
app.use('/appUpdateS', appUpdateS);
app.use('/appAllShoppingcart', appAllShoppingcart);

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
