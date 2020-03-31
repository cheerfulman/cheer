package com.zhu.mapper;

import com.zhu.pojo.Notification;
import com.zhu.pojo.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoticeMapper {

    @Insert("insert into notification(notifier,receiver,outerId,type,gmt_create,status,noticeName,title) values(" +
            "#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{noticeName},#{title})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver = #{id}")
    Integer queryAllNoticeByUserId(@Param("id")Long id);

    @Select("select * from notification where receiver = #{id} order by gmt_create desc limit #{offset},#{size}")
    List<Notification> queryAll(Integer offset, Integer size,Long id);


    @Update("update notification set status = 1 where id = #{id}")
    void read(@Param("id")Long noticeId);

    @Select("select count(*) from notification where receiver = #{id} and status = 0")
    Integer unRead(@Param("id")Long id);

    @Select("select outerId from notification where id = #{noticeId}")
    Long getNoticeById(@Param("noticeId")Long noticeId);
}
