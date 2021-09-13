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

function doFollow(toMember) {

    if ( $('#follow').hasClass('active') ) {
        $('#follow').removeClass('active');
        removeFollow(toMember);
    } else if ( !$('#follow').hasClass('active') ) {
        $('#follow').addClass('active');
        addFollow(toMember);
    }


}

function addFollow(toMember) {

    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url : "/api/follow/" + toMember,
        type : "POST",
        contentType : 'application/json',
        dataType : 'text',
        data : JSON.stringify(toMember),
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

function removeFollow(toMember) {

    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url : "/api/follow/" + toMember,
        type : "DELETE",
        contentType : 'application/json',
        dataType : 'text',
        data : JSON.stringify(toMember),
        success : () => {
            alert('언팔로우 하였습니다.');
            location.reload();
        },
        error : () => {
            alert('언팔로우에 실패하였습니다.');
            location.reload();
        }

    });

}