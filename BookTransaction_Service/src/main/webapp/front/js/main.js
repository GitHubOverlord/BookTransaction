// 添加一个验证器
(function ($) {
	jQuery.validator.addMethod("equalToPsw", function(value, element, old) {   
		console.log(old);
	  if( hex_md5( value ) === $(old).val() )
	  	return this.optional(element);
	  else
	  	return false;
	}, "和原来的密码不一样");
})(jQuery);

jQuery(document).ready(function($) {
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
// 页面初始化
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
	
	// 查看是否有cookie 如果有cookie则直接登录
	if( $.cookie('user') )
	{
		var user = $.parseJSON( $.cookie('user') );
		$.ajax({
    	url:"http://192.168.1.108:8080/BookTransaction_Service/loginAction"
    	,type : 'post'
    	,data:{ userName: user.userName ,psw : user.psw }
    	,dataType:'json'
    	,success:function (json) {
    		if(json.status != 1)
    			loginError.text( json.response );
    		else if( json.status == 1){
    			var user = json.value;
    			// after login 
    			afterLogin(user);
    		}
    	}
    });
	}
	var departmentOptions = [];
	var departmentButtons = [];
	var departmentObject = {};
	// 初始化轮播图
	$('.carousel').carousel();
	// 获取所有的系和专业
	$.ajax({
		url: 'http://192.168.1.108:8080/BookTransaction_Service/getAllDepartmentAndMajor',
		async: false,
		dataType:'json'
	})
	.done(function  (depts) {
		depts = depts.value;
		departmentOptions.push('<option value=""></option>')
		$.each(depts.departmentBeans,function (i,item) {
			departmentOptions.push( '<option value="'+item.id+'"> '+item.departmentName+' </option>' );
			departmentButtons.push( '<button type="button" class="list-group-item" data-value="'+item.id+'" >'+item.departmentName+'</button>')
			departmentObject[item.id] = [];
		});
		$.each(depts.majorBeans,function (i,item) {
			departmentObject[item.belongDepartmentId].push(item);
		});
		$('select[name="departmentId"]').html( departmentOptions.join(' ') );
		$('#departmentFilter').append( departmentButtons.join(' ') );
	})
	.fail(function(a1,a2,a3){
		console.log(a1);
		console.log(a2);
		console.log(a2);
	});
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
// 筛选
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////

	// 筛选按钮
	$('#filterBtn').click(function(e) {
		$('#filterForm').slideToggle();
		$(this).find('span').toggleClass('glyphicon-triangle-bottom')
		.toggleClass('glyphicon-triangle-top');
	});
	// 四级筛选器
	var $seniorFilter = $('#seniorFilter'),
		$departmentFilter = $('#departmentFilter'),
		$majorFilter = $('#majorFilter'),
		$courseFilter = $('#courseFilter');

	// 激活当前按钮
	$('#departmentFilter,#majorFilter,#seniorFilter,#courseFilter')
	.on('click', 'button', function(e) {
		$(this).addClass('active').siblings('button').removeClass('active');
	});
	// 年级过滤
	$seniorFilter.on('click', 'button', function(e) {
		// 展开$courseFilter
		$departmentFilter.slideDown('fast');
		if($majorFilter.find('button.active').size() != 0){
			var majorId = $majorFilter.find('button.active').data('value');
			var grade = $(this).data('value');
			filterCourse(majorId,grade);
		}
	});
	// 系过滤
	$departmentFilter.on('click', 'button', function(e) {
		var key = $(this).data('value')
		var major = departmentObject[key];
		$courseFilter.html('')
		$majorFilter.html( '<div class="list-group-item list-group-item-info">专业</div>' );
		$.each(major , function (i , item) {
			$majorFilter.append('<button type="button" class="list-group-item" data-value="'+item.id+'">'+item.majorName+'</button>')
		})
	});
	// 专业过滤
	$majorFilter.on('click', 'button', function(e) {
		// 根据专业和年级更新课程信息
		var majorId = $(this).data('value');
		var grade = $seniorFilter.find('button.active').data('value');
		filterCourse(majorId,grade);
	});
	function filterCourse(majorId,grade){
		$courseFilter.html('<div class="list-group-item list-group-item-info">课程</div>');
		$.ajax({
			url:'http://192.168.1.108:8080/BookTransaction_Service/findProjectByMajorId',
			data:{"majorId":majorId,"belongJuniorCalss":grade},
			dataType:'json',
			async:false
		})
		.done(function (json) {
			course = json.value;
			$.each(course,function (i ,item) {
				$courseFilter.append('<button type="button" class="list-group-item" data-value="'+item.id+'">'+item.projectName+'</button>');
			})
		});
	}
	// 课程过滤
	$courseFilter.on('click', 'button', function(e) {
		// 请求订单数据更新订单列表
	});

	// 展开效果
	var $ajaxContent = $('#ajax-content');
	$ajaxContent.on('click', '.expand-btn', function(e) {

		$(this).prev('table').fadeToggle('fast');
		// 切换小箭头
		$(this).find('span').toggleClass('glyphicon-triangle-bottom')
		.toggleClass('glyphicon-triangle-top');
	});
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
// 注册
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
	// 弹出框
	$("#navLoginBtn").click(function(e) {
		$("#loginModal").modal('show');
	});
	$("#navRegistBtn").click(function(e) {
		$("#registModal").modal('show');
	});
	//注册表单验证
	$('#registForm').validate({
		rules: {
			userName: {
				required: true,
				digits:true,
				maxlength: 11,
				minlength:11
			},
			nickName: {
				required:true,
				maxlength:20
			},
			psw: {
				required: true,
				minlength: 6
			},
			grade: {
				required: true
			},
			major: {
				required: true
			}
		},
		errorPlacement: function(error, element) {  
		  error.addClass('text-danger').appendTo(element.parent());
		},
		submitHandler: function(form) {
			ajaxData = {};
			$(form).find('[name]').each(function(i,item) {
				var key = $(this).attr('name');
				var val = $(this).val();
				if( key === 'psw' ) 
					ajaxData[key] = hex_md5(val);
				else
					ajaxData[key] = val;
			});
			$.post('http://192.168.1.108:8080/BookTransaction_Service/registerAction',ajaxData,function (json) {
				json = $.parseJSON(json);
				var user = json.value;
				var registError = $(form).find('.registError');
				if(json.status === 1){
					// 注册成功
					afterLogin(user);
					$('#registModal').modal('hide');
				}else {
					registError.text(josn.response);
				}
			});
		 }  
	});
	$('#registBtn').click(function(e) {
		$('#registForm').submit();
	});
	// 选择了系之后更新专业列表
	$('select[name="departmentId"]').on('change', function(e) {
		var key = $(this).val()
		var major = departmentObject[key];
		var majorOptions = [];
		$.each(major,function  (i,item) {
			majorOptions.push( '<option value="'+item.id+'">'+item.majorName+'</option>' )
		})
		$('select[name="majorId"]').html(majorOptions.join(' '));
	});

// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
// 登录
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
	// 登录表单
	$('#loginForm').validate({
		rules:{
			userName:{
				required:true,
				digits:true,
				maxlength:11,
				minlength:11
			},
			passWord:{
				required:true,
				minlength:6
			}
		},
		errorPlacement: function(error, element) {  
		  error.addClass('text-danger').appendTo(element.parent().next('.error'));
		},
		submitHandler: function(form) {

			var userName = $(form).find('[name="userName"]').val();
			var psw = hex_md5 ( $(form).find('[name="passWord"]').val() );
			var loginError = $(form).find('.loginError');
		    $.ajax({
		    	url:"http://192.168.1.108:8080/BookTransaction_Service/loginAction"
		    	,type : 'post'
		    	,data:{ userName: userName ,psw : psw }
		    	,dataType:'json'
		    	,success:function (json) {
		    		if(json.status != 1)
		    			loginError.text( json.response );
		    		else if( json.status == 1){
		    			var user = json.value;
		    			// after login 
		    			afterLogin(user);
		    		}
		    	}
		    });     
		 }  
	});
	$('#loginBtn').click(function(e) {
		$('#loginForm').submit();
	});
	// 登录之后的一系列操作
	function afterLogin( user ){
		// 添加cookie
		// 存储用户信息 cookie保存7天
		$.cookie('user',JSON.stringify(user),{ expires: 7 });
		// 隐藏登录框
		$('#loginModal').modal('hide');
		// 显示用户名和默认图片
		$('.userName').find('a').html( '<img class="img-circle userPhoto" src="img/userphoto/default.png" height="30" width="30" alt=""> ' + user.nickName);
		$('.userName').show();
		// 显示发布图书和退出按钮
		$('.releaseBook,.loginOut').show();
		// 隐藏登录注册按钮
		$('.login,.regist').hide();
	}
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
// 退出登录
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////

	// 退出登录按钮
	var $loginOut = $('.loginOut').eq(0),
			$sureLogout = $('#sureLogout'),
			$logoutModal = $('#logoutModal');
	$loginOut.on('click', function() {
		$logoutModal.modal('show');
	});
	$sureLogout.on('click', function(e){
		$logoutModal.modal('hide');
		afterLogout();
  });

  function afterLogout () {
  	$('.login,.regist').show();
		$('.userName,.releaseBook,.loginOut').hide();
		$.cookie('user','',{expires:-1});
  }
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
// 修改个人资料
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
	
	// 弹出修改框
	$("#navModifyBtn").click(function(e) {
		$("#modifyModal").modal('show');
		var user = $.parseJSON( $.cookie('user') );
		$modifyForm.find('[name]').each(function (i,item) {
			var key = $(item).attr('name');
			$(item).val( user[key] );
		});
	});
	// 提交按钮
	$('#modifyBtn').click(function(e) {
		$('#modifyForm').submit();
	});

	// 表单验证
	$('#modifyForm').validate({
		rules:{
			nickName:{
				required:true,
				maxlength:20
			},
			oldPassWord:{
				required:true,
				minlength:6,
				equalToPsw:"#orginPassword"
			},
			psw:{
				required:true,
				minlength:6
			},
			grade:{
				required:true
			},
			major:{
				required:true
			}
		},
		errorPlacement: function(error, element) {  
		  error.addClass('text-danger').appendTo(element.parent().next('.error'));
		},
		submitHandler: function(form) {
			var ajaxData = {};
			$(form).find('[name]').each(function (i,item) {
				var key = $(item).attr('name');
				var val = $(item).val();
				if(key === 'psw') 
					ajaxData[key] = hex_md5(val);
				else
					ajaxData[key] = val;
			});
			$.ajax({
				url: 'http://192.168.1.108:8080/BookTransaction_Service/updateUserMessageAction',
				type: 'post',
				dataType: 'json',
				data: ajaxData,
			})
			.done(function(json) {
				$('#modifyModal').modal('hide');
				var msg = json.response;
				$('#messageModal').find('modal-body').text(msg);
				setTimeout(function(){ 
					$('#messageModal').modal('show'); 
				},1000)
			});
			
		}
	});
}); // end of ready