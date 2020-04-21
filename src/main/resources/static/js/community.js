function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    var commentator = $("#commentator").val();
    if(!content){
        alert("小可爱发表的评论是什么呢?");
        return ;
    }
    $.ajax({
        type : "post",
        url : "/comment",
        data : {
            "parentId" : questionId,
            "content" : content,
            "type" : 1,
            "commentator" : commentator
        },
        success : function (response) {
            if(response.code == 200){
                // 局部刷新
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("/register");
                        window.localStorage.setItem("closable","true");
                    }else {
                        alert(response.message);
                    }
                }
            }
        },
        dataType : "json"
    });
}

function like(obj,type) {
    // let user = window.sessionStorage.getItem("user");
    // let user = session.getAttribute("user");
    // var user = ${session.user};
    // var user = '<%=session.getAttribute("user")%>';
    // var user = $.session.get('user');
    // let user = localStorage.getItem("user");

    // var id = e.getAttribute("data-id");

    // var user1 = obj.getAttribute("data-user");
    var subjectId = obj.getAttribute("data-id");
    // obj.classList.add("active");
    // obj.removeClass('active');
    $.ajax({
        type : "post",
        url : "/like",
        // contentType : "application/json",
        data: {
            "type" : type,
            "subjectId" : subjectId
            // "user" : user1
        },
        success : function (response) {

            if(response.code == 2003){
                var isAccepted = confirm(response.message);
                if(isAccepted){
                    window.open("/register");
                    window.localStorage.setItem("closable","true");
                }else {
                    alert(response.message);
                }
            }else{
                // alert(response.count);
                // response = $.parseJSON(response);
                if(response.likeStatus == true){
                    $(obj).addClass("active");
                    //obj.classList.add("active");
                }else{
                    $(obj).removeClass('active');
                }
                // $(obj).children("i").text(response.count);
                    $(obj).text(response.count);
            }

        },

    });
}


function comment(e) {
    // 评论人 ID
    var commentator = $("#commentator").val();

    var questionId = e.getAttribute("data-id");
    var content = $("#input-"+questionId).val();

    if(!content){
        alert("小可爱发表的评论是什么呢?");
        return ;
    }
    $.ajax({
        type : "post",
        url : "/comment",
        data : {
            "parentId" : questionId,
            "content" : content,
            "type" : 2,
            "commentator" : commentator
        },
        success : function (response) {
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("/register");
                        window.localStorage.setItem("closable","true");
                    }else {
                        alert(response.message);
                    }
                }
            }
        },
        dataType : "json"
    });
}


// 展开二级评论
function collapseComments(e) {

    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var v = $("#tag").val();
    if(v.indexOf(value) == -1){
        if(v){
            $("#tag").val(v + ',' + value);
        }else $("#tag").val(value);
    }
}

function showSelectTag() {
    $("#select-tag").show();
}