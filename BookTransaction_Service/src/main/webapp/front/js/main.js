jQuery(document).ready(function($) {
	$('.carousel').carousel();
	$('[data-toggle="popover"]').popover({
		html: true,
		content: [
			'<a href="#" class="list-group-item">土木</a>',
			'<a href="#" class="list-group-item">道路</a>',
			'<a href="#" class="list-group-item">计算机</a>',
			'<a href="#" class="list-group-item">英语</a>',
			'<a href="#" class="list-group-item">会计</a>',
			'<a href="#" class="list-group-item">金融</a>'
		].join(''),
		template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content list-group"></div></div>'
	});
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////
// 筛选
// //////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////

	// 筛选按钮
	$('#filterBtn').click(function(e) {
		$('#filterForm').slideToggle();
		$(this).find('span').toggleClass('glyphicon-triangle-bottom').toggleClass('glyphicon-triangle-top');
	});
	// 三级筛选器
	var $seniorFilter = $('#seniorFilter'),
		$majorFilter = $('#majorFilter'),
		$courseFilter = $('#courseFilter');

	// 激活当前按钮
	$('#majorFilter,#seniorFilter,#courseFilter').on('click', 'button', function(e) {
		$(this).addClass('active').siblings('button').removeClass('active');
	});
	// 专业过滤
	$majorFilter.on('click', 'button', function(e) {
		// 展开seniorFilter
		$seniorFilter.slideDown('fast');
		$seniorFilter.find('button').each(function(i, item) {
			if ($(this).hasClass('active')) {
				// 去服务器请求 相应的课程数据
				// 更新courseFilter
			}
		});
	});
	// 年级过滤
	$seniorFilter.on('click', 'button', function(e) {
		// 展开$courseFilter
		$courseFilter.slideDown('fast');
	});
	// 课程过滤
	$courseFilter.on('click', 'button', function(e) {
		// 请求订单数据更新订单列表
	});

	// 展开效果
	var $ajaxContent = $('#ajax-content');
	$ajaxContent.on('click', '.expand-btn', function(e) {

		$(this).prev('table').fadeToggle('fast');
		// 切换小箭头
		$(this).find('span').toggleClass('glyphicon-triangle-bottom').toggleClass('glyphicon-triangle-top');
	});
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
// 登录注册
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
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
			passWord: {
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
		}
	});
	$('#registBtn').click(function(e) {
		$('#registForm').submit();
	});
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
		    $(form).ajaxSubmit();     
		 }  
	});
	$('#loginBtn').click(function(e) {
		$('#loginForm').submit();
	});
});