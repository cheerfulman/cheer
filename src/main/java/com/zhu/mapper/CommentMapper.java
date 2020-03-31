package com.zhu.mapper;

import com.zhu.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified," +
            "like_count,content) values(#{parentId},#{type},#{commentator}," +
            "#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert1(Comment comment);


    @Select("select * from comment where id = #{id}")
    Comment queryById(Long id);

    @Select("select * from comment where parent_id = #{id} and type = #{type} order by gmt_create desc")
    List<Comment> queryAllQuestionById(@Param("id") Long id, @Param("type")Integer type);

    @Update("update comment set comment_count = comment_count + 1 where id = #{parentId}")
    void addCommentCount(@Param("parentId") Long parentId);
}
