function delContent(name) {

    $('#modifyBin').on('click', function () {
        $.ajax({
            beforeSend : function(xhr) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                xhr.setRequestHeader(header, token);
            },
            url : "/adm/boards/" + name,
            type : "PUT",
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(name),
            success : () => {
                alert('성공적으로 삭제되었습니다');
            },
            error : () => {
                alert('삭제 실패 하였습니다.');
            }

        })
    })

    $('#trashBin').on('click', function () {
        $.ajax({
            beforeSend : function(xhr) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                xhr.setRequestHeader(header, token);
            },
            url : "/adm/boards/" + name,
            type : "DELETE",
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(name),
            success : () => {
                alert('성공적으로 삭제되었습니다');
            },
            error : () => {
                alert('삭제 실패 하였습니다.');
            }

        })
    })


}