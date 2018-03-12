var app = angular.module('app', []);
var page;
var basePath;
function successCallback(data){
		 page.error='';
		 page.tip = data.data.msg;
		 if(data.data.code==1){
			 window.location.href=basePath+"phone/plan2/index.jsp";
			 //window.location.href="http://localhost:8884/service-ymh/ymh/phone/plan2/index.jsp";
		 }
}
function errorCallback(data){
		page.tip = data;
}
function isNull(x){
	if (x !== null && x !== undefined && x!== '') {
		return false;
	}else{
		return true;
	}
}
app.controller('loginController', function($scope,$http) {
    $scope.user={};//初始化
    $scope.basePath = $("#path").val();
    basePath = $scope.basePath;
    page = $scope;
    $scope.submit=function(){
    	if(isNull($scope.user.username)){
    		$scope.error="用户名不能为空！";
    		return ;
    	}
    	if(isNull($('#password').val())){
    		$scope.error="密码不能为空！";
    		return ;
    	}
    	//加密方法
        //var  enResult = strEnc(str,key1,key2,key3);  
        $http.get($scope.basePath+"/common/getDesString").then(
           function(ret){
        	   var a=ret.data.data.a;
        	   var b=ret.data.data.b;
        	   var c=ret.data.data.c;
        	   var  enResult = strEnc($('#password').val(),a,b,c);  
        	   console.log("encodePassword is "+enResult);
        	   $scope.user.password=enResult;
        	   $http.post($scope.basePath+'/users2/login1', $scope.user).then(successCallback, errorCallback);
           }		
        );
    }
});