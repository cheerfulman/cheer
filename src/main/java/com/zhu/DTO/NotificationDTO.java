package com.zhu.DTO;


import com.zhu.pojo.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String OuterTile;
    private String type;
}
