var page;
//重新画分页栏
function restPageList($scope,$http){
    $scope.pages = Math.ceil(  $scope.total / $scope.pageSize); //得到总页数;
    page  = $("#page").page({
        pages:$scope.pages, //页数
        curr:  $scope.cur , //当前页
        type: 'default', //主题
        groups: 5, //连续显示分页数
        prev: '<', //若不显示，设置false即可
        next: '>', //若不显示，设置false即可
        first: "首页",
        last: "尾页", //false则不显示
        render: function(context, $el, index) { //渲染[context：对this的引用，$el：当前元素，index：当前索引]
            //逻辑处理
            if (index == 'last') { //虽然上面设置了last的文字为尾页，但是经过render处理，结果变为最后一页
                $el.find('a').html('最后一页');
                return $el; //如果有返回值则使用返回值渲染
            }
            return false; //没有返回值则按默认处理
        },
        jump: function(context, first) {
            if (first)return;
            $scope.cur = context.option.curr;
            $scope.start =  $scope.cur *10-10;
            var limit = $scope.pageSzie;
            $http.get($scope.basePath+'common/roles?start='+ $scope.start+'&limit=10')
                .success(function (data) {
                    $scope.items = data.data;
                    $scope.total = data.data.total;
                });
            console.log('当前第：' + context.option.curr + "页");
        }
    });
}

var initController = function ( $scope,$http) {
    $scope.start=0;
    $scope.cur = 1;
    $scope.pageSize=10;
    $scope.basePath=$('#basePath').val();
    $http.get($scope.basePath+'plan/types?start='+0+'&limit=10')
        .success(function (data) {
            $scope.items = data;
           // $scope.total = data.total;
            //restPageList($scope,$http);
      });
    $scope.show=function (jhbh,jhlx,jhmc) {
    		$scope.jhmc=jhmc;
            //alert("正在查看"+jhbh+","+jhlx);
            $http.get($scope.basePath+'plan/getPlanContent?jhlx='+jhlx+'&jhbh='+jhbh)
            .success(function (data) {
                $scope.content = data.content;
                console.log("res="+data.content);
          }).error(function(data,status,headers,config){
          $scope.content="暂无内容！";
          });;
    };
    $scope.addRole=function () {
        $http.get($scope.basePath+"common/addRole?name=123&desc=123").success(function(data){
            $http.get($scope.basePath+'common/roles?start='+ $scope.start+'&limit=10')
                .success(function (data) {
                    $scope.items = data.data;
                    $scope.total = data.total;
                    console.log("cur="+$scope.cur+",curPageSize="+data.data.length);
                    restPageList($scope,$http);
              });
        }).error(function(data,status,headers,config){
        	alert("访问失败！");
        });
    };
};
var modController = function ($state, $scope) {
    console.log("传递参数："+$state.params.id);
    $scope.id=$state.params.id;
};
var delController = function ($state, $scope,$http) {
    console.log("传递参数："+$state.params.id);
    $scope.id=$state.params.id;
};
var myApp = angular.module('myApp', ['ui.router',"mytop",'ngSanitize'])
    .config(function ($locationProvider, $stateProvider, $urlRouterProvider) {
        $locationProvider.html5Mode(false);
        $urlRouterProvider.otherwise('/index/list');
        $stateProvider
            .state('role', {
                url: '/index',
                templateUrl: 'main.jsp',
                abstract: true,
                controller: initController,
                controllerAs: 'anyName'
            })
            .state('role.list', {
                url: '/list',
                templateUrl: 'list.jsp',
            })
            .state('role.mod', {
                url: '/{id}',
                templateUrl: 'mod.jsp',
                controller: modController,
                controllerAs: 'anyName'
            });
    })
    .component('app', {
        template: '<div class="app"><div ui-view></div></div>',
        restrict: 'E'
    })
    .controller('pageController', function ($scope,$http) {
        $scope.basePath=$('#basePath').val();
        $http.get($scope.basePath+'plan/getPlanFromFile?jhbh=01&jhlx=cq')
        .success(function (data) {
            console.log("data："+data);
      });
        
    });