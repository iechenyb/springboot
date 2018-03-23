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
    page.nameKey="";
    page.mySelected="http://jqaaa.com/jx.php?url=";
    page.type="tengxun";
    page.curName="";
    document.getElementById("center").height="200";//默认高度
    page.smaller=function smaller(){
    	var curH=document.getElementById("center").height;
    	if(curH<200){
    		return ;
    	}
    	document.getElementById("center").height=Number(curH)-10;
    }
    page.max=function(){
    	var width=document.body.clientWidth ;
    	var height=document.body.clientHeight ;
    	console.log(width+"#"+height);
    	document.getElementById("center").height=height;
    	//document.getElementById("center").width=width;
    }
    page.min=function(){
    	document.getElementById("center").height=200;
    }
    page.bigger=function bigger(){
    	/*var iframe=window.frameElement;  
        var bHeight=iframe.center.document.body.scrollHeight;  
        var dHeight=iframe.center.document.documentElement.scrollHeight;  
        var height=Math.max(bHeight,dHeight);  
        iframe.height=800;  */
    	var curH=document.getElementById("center").height;
    	document.getElementById("center").height=Number(curH)+10;
    }
    
    page.check=function(){
    	if(window.orientation==180||window.orientation==0){  
            alert("竖屏状态！")         
       }  
       if(window.orientation==90||window.orientation==-90){  
            alert("横屏状态！")          
        }  
    }
    page.search=function search(){
    	console.log(page.mySelected+",name="+page.nameKey+"type="+page.type);
	    $http.get($scope.basePath + "vip/"+page.type+"/dianshiju/details?name="+page.nameKey).then(
				function(data){
					page.list = data.data;
					page.curName=page.list[0].name;
				}, function(){
					
				}
		);
    }
    page.searchYouku=function searchYouku(){
	    $http.get($scope.basePath + "vip/youku/dianshiju/details?url="+page.youkuKey).then(
				function(data){
					page.list = data.data;
					page.curName=page.list[0].name;
				}, function(){
					
				}
		);
    }
    //剧集播放
    page.play=function(vedio){
    	  var url =page.mySelected+vedio.url;
    	  document.getElementById('page9090').href=url;
    	  document.getElementById('page9090').click();
    	  page.curIndex=" 正在播出 第"+vedio.idx+"集";
    }
    //任意视频地址播放
    page.playAll=function(){
       var url =page.mySelected+page.anyKey;
  	  document.getElementById('page9090').href=url;
  	  document.getElementById('page9090').click();
  	  page.curIndex=" 地址 "+page.anyKey+"";
    }
    /*
    $http.get($scope.basePath+'plan2/cq').then(successCallback1, errorCallback1);
    $http.get($scope.basePath+'plan2/pk10').then(successCallback2, errorCallback2);*/
});