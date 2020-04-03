package com.zhu.mapper;

import com.zhu.pojo.Question;
import com.zhu.DTO.QuestionDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into question (id,title,description,gmt_create,gmt_modified,creator,tag)" +
            "values(#{id},#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insertQue(Question question);

    @Select("select * from question where creator = #{id} order by gmt_create desc limit #{offset},#{size}")
    List<Question> queryAll(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size, @Param("id") Long id);

    @Select({"<script>"
            + "select count(1) from question" +
            "<where>" +
            "<if test = 'search != null'>" +
            "and title regexp #{search}" +
            "</if>" +
            "</where>" +
            "</script>"
    })
    Integer countPage(String search);

    @Select("select * from question where creator = #{id}")
    List<Question> queryQuestionByUserId(@Param(value = "id") Long id);

    @Select("select * from question where id = #{id}")
    Question queryByQueId(@Param(value = "id") Long id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id = #{id}")
    int update(Question question);

    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void addView(Question question);

    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    int addComment(@Param("id") Long id);

    @Select("select * from question where tag regexp #{tag} and id != #{id}")
    List<QuestionDTO>
    selectRelative(QuestionDTO questionDTO);

    @Select({"<script>" +
            "select * from question " +
            "<where>" +
            "<if test='search != null'>" +
            "and title regexp #{search}" +
            "</if>" +
            "</where>" +
            "order by gmt_create desc limit #{offset},#{size}" +
            "</script>"
    })
    List<Question> queryAll1(String search, Integer offset, Integer size);
}
