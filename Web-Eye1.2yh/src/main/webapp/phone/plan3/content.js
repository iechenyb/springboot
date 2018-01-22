var app = angular.module('app', []).filter('to_trusted',
		[ '$sce', function($sce) {
			return function(text) {
				return $sce.trustAsHtml(text);
			};
		} ]);
var page;
var basePath;
<<<<<<< HEAD
var curid = 0;
function successCallback1(data) {
	page.cqList = data.data.data;
=======
var curid=0;
function successCallback1(data){
		 page.cqList = data.data.data;
>>>>>>> 2af0b915207c21d06314351034f00b5f976ed8e1
}
function errorCallback1(data) {
	page.msg = data;
}
function successCallback2(data) {
	page.pkList = data.data.data;
}
function errorCallback2(data) {
	page.msg = data;
}
<<<<<<< HEAD
function successCallback3(data) {
	page.planList = data.data.data;
	console.log("重新加载list数据！");
	// page.$apply();
	// showPanel();
	// $('#do-not-say-'+curid).collapse('open');//打开已经打开的面板
	// 父元素向子元素广播
	// page.$broadcast("successChange",111);

=======
function successCallback3(data){
	 page.planList = data.data.data;
	 page.$apply(function(){
		 $('#do-not-say-'+curid).collapse('open');//打开已经打开的面板
	 });
	   //父元素向子元素广播
	 page.$broadcast("successChange",111);
	
>>>>>>> 2af0b915207c21d06314351034f00b5f976ed8e1
}
function errorCallback3(data) {
	page.msg = data;
}
<<<<<<< HEAD
function showPanel() {
	console.log("open ..." + '#' + curid);
	$('#do-not-say-' + curid).collapse('open');
}
app.controller('contentController', function($scope, $http, $interval) {
	$scope.basePath = $("#path").val();
	basePath = $scope.basePath;
	page = $scope;
	$scope.curid = 0;
	$scope.count = 0;// $scope.opidx
	/*
	 * $scope.show=function(){ $('#do-not-say-'+$scope.opidx).collapse('open'); }
	 */
	/*
	 * window.setInterval(function(){ console.log("count++");
	 * $scope.count=$scope.count+1; var param =
	 * '?jhlx='+$('#jhlx').val()+"&jhbh="+$('#jhbh').val();
	 * $http.get($scope.basePath+'/plan2/getPlan'+param).then(successCallback3,
	 * errorCallback3); //$scope.$apply(); },1000*3);//毫秒
	 */
	var timer = $interval(function() {
		console.log("count++");
		$scope.count = $scope.count + 1;
		var param = '?jhlx=' + $('#jhlx').val() + "&jhbh=" + $('#jhbh').val();
		$http.get($scope.basePath + '/plan2/getPlan' + param).then(
				successCallback3, errorCallback3);
	}, 3000);
	page.open = function(id) {
		console.log("open ..." + '#' + id);
		/*
		 * $('#do-not-say-'+id).collapse({ toggle: true, parent:'#accordion' });
		 */
		$('#do-not-say-' + id).collapse('toggle');
		curid = id;
		$scope.curid = id;
	}
	page.exit= function(){
		$http.get($scope.basePath + '/users/exit' + param).then(
				function(data){
					window.location.replace($scope.basePath + 'phone/plan2/login.jsp');
				}, function(){
					
				});
		
	}

	/*
	 * $('#collapse-nav').on('open.collapse.amui', function() {
	 * console.log('折叠菜单打开了！'); }).on('close.collapse.amui', function() {
	 * console.log('折叠菜单关闭鸟！'); });
	 */
	$http.get($scope.basePath + '/plan2/cq').then(successCallback1,
			errorCallback1);
	$http.get($scope.basePath + '/plan2/pk10').then(successCallback2,
			errorCallback2);
	var param = '?jhlx=' + $('#jhlx').val() + "&jhbh=" + $('#jhbh').val();
	$http.get($scope.basePath + '/plan2/getPlan' + param).then(
			successCallback3, errorCallback3);
});
app.directive('repeatFinish', function() {
	return {
		link : function(scope, element, attr) {
			console.log(scope.$index + "," + element);
			if (scope.$last == true) {
				console.log('ng-repeat执行完毕' + curid);
				// $('#do-not-say-1').collapse('toggle');
				showPanel();
			}
		}
	}
})
=======
app.controller('contentController', function($scope,$http) {
    $scope.basePath = $("#path").val();
    basePath = $scope.basePath;
    page = $scope;
    $scope.count=0;
    $scope.indexP=0;
    $scope.time = CurentTime();
    window.setInterval(function(){
    	console.log("count++");
    	$scope.$apply(function(){
    		$scope.count=$scope.count+1;
        	var param = '?jhlx='+$('#jhlx').val()+"&jhbh="+$('#jhbh').val();
            $http.get($scope.basePath+'/plan2/getPlan'+param).then(successCallback3, errorCallback3);
            $('#do-not-say-'+curid).collapse('open');//打开已经打开的面板
            $scope.time = CurentTime();
    	});
    },1000*30);//毫秒 
    page.open=function(id){
    	console.log("open ..."+'#'+id);
    	$('#do-not-say-'+id).collapse({
    		  toggle: true,
    		  parent:'#accordion'
    	});
    	//$('#do-not-say-'+id).collapse('open');
    	curid=id;
    	$scope.indexP=curid;
    }
    
   /* $('#collapse-nav').on('open.collapse.amui', function() {
    	  console.log('折叠菜单打开了！');
    	}).on('close.collapse.amui', function() {
    	  console.log('折叠菜单关闭鸟！');
    	});*/
    $http.get($scope.basePath+'/plan2/cq').then(successCallback1, errorCallback1);
    $http.get($scope.basePath+'/plan2/pk10').then(successCallback2, errorCallback2);
    var param = '?jhlx='+$('#jhlx').val()+"&jhbh="+$('#jhbh').val();
    $http.get($scope.basePath+'/plan2/getPlan'+param).then(successCallback3, errorCallback3);
});

function CurentTime()
{ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds()      //分
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
   
    if(hh < 10)
        clock += "0";
       
    clock += hh + ":";
    
    if (mm < 10) clock += '0'; 
    clock += mm+ ":";
    
    if (ss < 10) clock += '0'; 
    clock += ss; 
    
    return(clock); 
} 
>>>>>>> 2af0b915207c21d06314351034f00b5f976ed8e1
