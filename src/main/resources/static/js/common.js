let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

function delContent(name) {

    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url : "/adm/boards/" + name,
        type : "DELETE",
        contentType : 'application/json',
        dataType : 'text',
        data : JSON.stringify(name),
        success : () => {
            alert('성공적으로 삭제되었습니다');
            location.reload();
        },
        error : () => {
            alert('삭제에 실패 하였습니다.');
        }

    });
}

 function modifyContent(tag) {

    const modifyModal = $(tag).next();

    if ( modifyModal.hasClass('hidden') ) {
       modifyModal.removeClass('hidden');
    } else {
        modifyModal.addClass('hidden');
    }

 }