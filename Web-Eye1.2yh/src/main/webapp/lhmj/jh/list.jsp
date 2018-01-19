<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>

<style type="text/css">
.divcss5{ border:0px solid #F00;height:750px;word-wrap:break-word;overflow:scroll;} 
</style>
<div class="am-g">
  <div class="am-u-sm-1">&nbsp;</div>
    <div class="am-u-sm-7 divcss5" ng-bind-html="content" ><!-- <p style="font-size:xx-small;height:100px;" ></p> --></div>
    <div class="am-u-sm-4">
        <div class="am-g">
            <div class="am-u-sm-4" ng-repeat="x in items" style="margin-top:5px;margin-left:10px;">
                    <button type="button" class="am-btn am-btn-success" ng-click="show(x.jhbh,x.jhlx,x.jhmc)">{{ x.jhmc }}</button>
            </div>
            <hr>
        </div>
    </div>
</div>

