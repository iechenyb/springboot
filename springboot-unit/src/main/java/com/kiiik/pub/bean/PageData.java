package com.kiiik.pub.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分页数据对象")
public class PageData<T>
{

  @ApiModelProperty("当前分页数据")
  public List<T> list;

  @ApiModelProperty("记录总数")
  public long total;

  @ApiModelProperty("第n页")
  public int curPage;

  @ApiModelProperty("每页展示记录数")
  public int pageSize;

  @ApiModelProperty("总页数")
  public int pages;

  public PageData(List<T> data)
  {
    if (CollectionUtils.isEmpty(data)) {
      this.list = new ArrayList<T>();
      this.total = 0;
      this.curPage = 0;
      this.pageSize = 0;
    } else {
      this.list = data;
      this.total = data.size();
      this.curPage = 1;
      this.pages = 1;
    }
  }

  public PageData(Page<T> page, KiiikPage p) {
    this.list = page.getResult();
    this.total = page.getTotal();
    if (p.getCurPage().intValue() < 1)
      this.curPage = 1;
    else if (p.getCurPage().intValue() > page.getPages())
      this.curPage = page.getPages();
    else {
      this.curPage = p.getCurPage().intValue();
    }
    this.pageSize = p.getPageSize().intValue();
    this.pages = page.getPages();
  }

  public List<T> getList() {
    return this.list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public long getTotal() {
    return this.total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getCurPage() {
    return this.curPage;
  }

  public void setCurPage(int curPage) {
    this.curPage = curPage;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPages() {
    return this.pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }
}