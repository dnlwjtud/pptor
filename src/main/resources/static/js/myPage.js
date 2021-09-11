
function delContent(articleId) {

    $('#trashBin').on('click', function () {
        $.ajax({
            beforeSend : function(xhr) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                xhr.setRequestHeader(header, token);
            },
            url : "/api/articles/" + articleId,
            type : "DELETE",
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(articleId),
            success : () => {
                alert('성공적으로 삭제되었습니다');
            },
            error : () => {
                alert('삭제 실패 하였습니다.');
            }

        })
    })


}

/*
function delContent(articleId) {
      $('#trashBin').on('click', function(articleId) {
      $.ajax({
     beforeSend: function(xhr){
        xhr.setRequestHeader(header,token);
     },
    url : "/api/articles/" + articleId,
    type : "delete",
    data : formdata,
    success : () => {
      alert("성공적으로 삭제되었습니다.");
    },
    error : () => {
      alert("삭제를 실패하였습니다.");
    }
  });
    });
    }
    */
/*
$.ajax({
    type       : 'POST',
    async      : false,
    contentType: 'application/json',
    data        : JSON.stringify($('form[name="codeForm"]').serializeComponent()),
    dataType   : 'json',
    url        : 'saveCode.do',
    error      : function(json){
        alert('저장중 오류가 발생하였습니다');
    },
    success    : function(data){
        if(data.resultFg == "SS" ) {
            alert('저장 하였습니다.');
            window.parent.opener.searchMain();
            self.close();
        } else if (data.resultFg == "DU" ){
            alert('메인코드명이 중복 되었습니다.');
            return;
        } else {
            alert('저장중 오류가 발생 하였습니다.');
            return;
        }
    }
});

*/