var app = angular.module('app', []);
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
app.controller('indexController', function($scope,$http) {
    $scope.basePath = $("#path").val();
    basePath = $scope.basePath;
    page = $scope;
    $http.get($scope.basePath+'/plan2/cq').then(successCallback1, errorCallback1);
    $http.get($scope.basePath+'/plan2/pk10').then(successCallback2, errorCallback2);
});