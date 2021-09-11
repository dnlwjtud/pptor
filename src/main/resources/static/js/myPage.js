let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

function delContent(articleId) {

    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url : "/api/articles/" + articleId,
        type : "DELETE",
        contentType : 'application/json',
        dataType : 'text',
        data : JSON.stringify(articleId),
        success : () => {
            alert('성공적으로 삭제되었습니다');
            location.reload();
        },
        error : () => {
            alert('삭제 실패 하였습니다.');
        }

    });

}

function addFollow(fromMember) {

    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url : "/api/follow/" + fromMember,
        type : "POST",
        contentType : 'application/json',
        dataType : 'text',
        data : JSON.stringify(fromMember),
        success : () => {
            alert('팔로우 하였습니다.');
            location.reload();
        },
        error : () => {
            alert('팔로우에 실패하였습니다.');
            location.reload();
        }

    });

}