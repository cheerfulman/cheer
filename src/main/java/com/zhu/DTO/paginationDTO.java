package com.zhu.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class paginationDTO<T>{
    private List<T> t = new ArrayList<>(); // 如果分页的对象是 question 则泛型为 question 如果分页的为 notice 则泛型为 notice
    private boolean pre;//向前一页
    private boolean next; //向后按钮
    private boolean first;// 第一页
    private boolean end;//最后一页
    private Integer totalPage; //总页数
    private Integer currPage;//当前页
    private List<Integer> pages = new ArrayList<>();//显示的页码 （1，2，3，4）则页码显示1,2,3,4页

    // 将页码 参数 设置好
    public void setPage(Integer total, Integer currPage, Integer size){
        totalPage = total;

        this.currPage = currPage;

        pages.add(currPage);
        for(int i = 1; i <= 3; i++){
            if(currPage - i > 0)pages.add(0,currPage - i);
            if(currPage + i <= totalPage)pages.add(currPage + i);
        }

        // 如果当前页数 为1 则 不显示 向前，因为无法向前
        if(currPage == 1)pre = false;
        else pre = true;
        // 如果当前页数 为最后一页 则 不显示 向后，因为无法向后
        if(currPage == totalPage)next = false;
        else next = true;

        //如果 显示的 页码中 有 第一页则 不显示 主动跳回第一页的按钮 <<
        if(pages.contains(1))first = false;
        else first = true;
        //如果 显示的 页码中 有 末页 则 不显示 主动跳回末页的按钮 >>
        if(pages.contains(totalPage))end = false;
        else end = true;
    }
}
