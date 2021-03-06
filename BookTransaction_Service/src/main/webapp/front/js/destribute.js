// var URL = 'http://192.168.1.108:8080';
var URL = '';
$(document).ready(function() {
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	// 初始化页面
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////	

	var user = $.parseJSON( $.cookie('user') );

	$.getJSON('/BookTransaction_Service/getMyOrder.action')
	.done(function(json){
		if(json.status === 1){
			order_data = json.value;

			console.log(order_data);
			//var bookNames = order_data.includeBookName.split(',');
			//bookNames.pop();
			//var projectIds = order_data.includeJuniorClass.split(',');
			//projectIds.pop();
			var set = order_data.set;
			var bookNames = [];
			var projectIds = [];
			$.each(set,function (i,item) {
				bookNames.push( item.bookName );
				projectIds.push( item.projectId );
			});

			// console.log( order_data.includeBookName );
			showBooks (bookNames,user,projectIds);

			$trs = $( '#myTable tbody tr' )
			$trs.each(function(i,tr) {
				$(tr).find('[name="oldDegree"]').val( set[i].oldDegree );
				$(tr).find('[name="haveExerciseBook"]').val( set[i].haveExerciseBook );
				$(tr).find('[name="price"]').val( parseInt(set[i].price) );
				$(tr).find('[name="describle"]').val( set[i].describle );
			});
			$('#myTable').find('[name="orderContactPhone"]').val( order_data.contactPhone );
			$('#myTable').find('[name="orderContactQQ"]').val( order_data.contactQQ );
			$('#myTable').find('[name="orderDescribe"]').val( order_data.orderDescribe );

		}else if( json.status === 2 ){
			// 未登录
			alert( '您还未登陆 , 请先进行登陆操作' );

			location.href = 'index.html';
		}
	});

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	// 选中书籍
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	$('#grade').on('click', 'a', function() {
		$(this).toggleClass('active').siblings('a').removeClass('active');
		var grade = $(this).data('value');
		console.log(user.majorId)
		$.getJSON(URL+'/BookTransaction_Service/findProjectByMajorId.action', 
			{majorId:user.majorId,belongJuniorCalss:grade}, 
			function(json, textStatus) {
				console.log(json)
				course = json.value;

				$.each(course,function (i ,item) {
					// $('#bookName').append('<button type="button" class="list-group-item" data-value="'+item.id+'">'+item.projectName+'</button>');
					$('#bookName').html('<a href="#" class="list-group-item list-group-item-warning" data-value="'+item.id+'">'+item.projectName+'</a>');
				});
				if(course.length === 0) $('#bookName').html('');
			});
		// end get json
	});

	$("#bookName").on('click', 'a', function(e) {
		$(this).toggleClass('active');
	});

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	//添加书籍
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	$("#addSure").click(function() {
		var bookNames = [];
		var projectIds = [];
		$("#bookName").find('a.active').each(function(i, element) {
			bookNames.push(element.innerHTML);
			projectIds.push($(this).data('value'));
		});
		$('#addModal').modal("hide");
		//显示书籍
		showBooks (bookNames,user,projectIds);
		$("#bookName,#grade").find('a.active').removeClass('active');
		$('#addModal').modal('hide');
	});

	function showBooks (bookNames,user,projectIds){
		$.each(bookNames, function(i, item) {
			var line = [
				'<tr>',
				'	<td class="text-left">',
				'<input type="checkbox" id="blankCheckbox" value="option1" aria-label="...">  ' + bookNames[i] + '',
				'<input type="hidden" name="belongUserName" value="'+user.userName+'" />',
				'<input type="hidden" name="belongMajorId" value="'+user.major+'" />',
				'<input type="hidden" name="belongJuniorClass" value="'+user.grade+'" />',
				'<input type="hidden" name="projectId" value="'+projectIds[i]+'" />',
				'<input type="hidden" name="bookName" value="'+bookNames[i]+'" />',

				'</td>',
				'	<td>',
				'		<select name="oldDegree" class="form-control">',
				'			<option value="20">20%</option>',
				'			<option value="40">40%</option>',
				'			<option value="60">60%</option>',
				'			<option value="80">80%</option>',
				'			<option value="100">全新</option>',
				'		</select>',
				'	</td>',
				'	<td>',
				'		<select name="haveExerciseBook" class="form-control">',
				'			<option value="1">有</option>',
				'			<option value="0">无</option>',
				'		</select>',
				'	</td>',
				'	<td>',
				'		<div class="form-group">',
				'			<div class="input-group">',
				'				<div class="input-group-addon"><span class="glyphicon glyphicon-yen"></span></div>',
				'				<input type="text" class="form-control" id="price" name="price" placeholder="">',
				'				<div class="input-group-addon">.00</div>',
				'			</div>',
				'		</div>',
				'	</td>',
				'	<td>',
				'		<div class="form-group" style="width:100%">',
				'			<input class="form-control" style="width:100%" name="describle" type="text" id="formGroupInputSmall" placeholder="" >',
				'		</div>',
				'	</td>',
				'</tr>'
			].join('');
			$('#myTable').append(line);
		});
	}
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	//选中要删除的书籍
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	$('#myTable tbody').on('click', 'tr input[type="checkbox"]', function() {
		$(this).parents('tr').toggleClass("active");
	});

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	//删除书籍
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	$("#deleteBtn").on('click', function() {
		var activeRows = $("#myTable").find('tbody tr.active');
		if (activeRows.size()) {
			$('#deleteModal').modal('show');
		};
	});
	$('#deleteSure').on('click', function() {
		var activeRows = $("#myTable").find('tbody tr.active');
		activeRows.remove();
		$('#deleteModal').modal('hide');
	});
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	// 表单验证
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	$("#tableForm").validate({
		rules: {
			price: {
				required: true,
				digits: true
			},

			orderContactPhone: {
				required: true,
				maxlength: 11,
				minlength: 11,
				digits: true
			},

			orderContactQQ: {
				required: true,
				digits: true,
				minlength: 4
			},
		},
		// errorPlacement: function(error, element) {
		// 	$('.errorMessage').html(error);
		// },
		errorLabelContainer:'.errorMessage',
		submitHandler: function (form) {
			var obj = {};
			
			$(form).find('tfoot').find('[name]').each(function(index, value) {
				var name = $(this).attr('name');
				var value = $(this).val();
				obj[name] = value;
			});
			if( $(form).find('tbody').find('tr').size() === 0 ) {
				// 订单中一本书都没有
				$('#infoModal').find('.modal-body').html( '请选择至少一本书籍' );
				$('#infoModal').modal('show');
				return;
			}
			var arr = [];
			$(form).find('tbody').find('tr').each(function(index, trElem) {
				var bookRecord = {};
				$(trElem).find('[name]').each(function(i, tdElem) {
					var name = $(tdElem).attr('name');
					var value = $(tdElem).val();
					bookRecord[name] = value;
				});
				arr.push(bookRecord);
			});

			obj.orderItemBeans = JSON.stringify ( arr );
			console.log ( JSON.stringify( obj ) );
			$.ajax({
				url:URL + '/BookTransaction_Service/publishOrder.action',
				type:'post',
				data:obj,
				dataType:'json'
			})
			.done(function(json){
				console.log( JSON.stringify(json) );
				$('#infoModal').find('.modal-body').html( '保存并发布成功' );
				$('#infoModal').modal('show');
			});
		}
	});
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	// 发布
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	$("#sent").on('click', function() {
		
		$("#tableForm").submit();

	});
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	// 撤销发布
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	$('#cheXiaoBtn').on('click',function (e) {
		var url = '/BookTransaction_Service/setPublishOrderStatus.action?publishStatus=true';
		$.get(url).done(function (data) {
			console.log( data );
			if( data['status'] == 1 ){
				$('#infoModal').find('.modal-body').html( '撤销发布成功' );
				$('#infoModal').modal('show');
			}
		});
	});
});