var app = angular.module('app', []);
var page;
var basePath;
function successCallback(data){
		 page.msg = data.data.msg;
		 if(data.data.code==1){
			 window.location.href=basePath+"phone/plan2/index.jsp";
		 }
}
function errorCallback(data){
		page.msg = data;
}

app.controller('loginController', function($scope,$http) {
    $scope.user={};//初始化
    $scope.basePath = $("#path").val();
    basePath = $scope.basePath;
    page = $scope;
    $scope.submit=function(){
    	console.log($scope.user+","+page.basePath);
    	$http.post($scope.basePath+'/users2/login1', $scope.user).then(successCallback, errorCallback);
    }
});