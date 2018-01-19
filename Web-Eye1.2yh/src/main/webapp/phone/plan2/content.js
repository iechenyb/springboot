var app = angular.module('app', [])
.filter('to_trusted', ['$sce',
function($sce){ 
	return function(text) { 
			return $sce.trustAsHtml(text); 
	}; 
}]);
var page;
var basePath;
function successCallback1(data){
		 page.cqList = data.data.data;
}
function errorCallback1(data){
		page.msg = data;
}
function successCallback2(data){
	 page.pkList = data.data.data;
}
function errorCallback2(data){
	page.msg = data;
}
function successCallback3(data){
	 page.planList = data.data.data;
	 page.$apply();
	   //父元素向子元素广播
	 page.$broadcast("successChange",111);
	
}
function errorCallback3(data){
	page.msg = data;
}
var curid=0;
app.controller('contentController', function($scope,$http) {
    $scope.basePath = $("#path").val();
    basePath = $scope.basePath;
    page = $scope;
    $scope.count=0;
    window.setInterval(function(){
    	console.log("count++");
    	$scope.count=$scope.count+1;
    	var param = '?jhlx='+$('#jhlx').val()+"&jhbh="+$('#jhbh').val();
        $http.get($scope.basePath+'/plan2/getPlan'+param).then(successCallback3, errorCallback3);
    	$scope.$apply();
    	$('#do-not-say-'+curid).collapse('open');//打开已经打开的面板
    },1000*3);//毫秒 
    page.open=function(id){
    	console.log("open ..."+'#'+id);
    	/*$('#do-not-say-'+id).collapse({
    		  toggle: true,
    		  parent:'#accordion'
    	});*/
    	$('#do-not-say-'+id).collapse('toggle');
    	curid=id;
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