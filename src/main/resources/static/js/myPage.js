var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function delContent(articleId) {
      $('#trashBin').on('click', function() {
      $.ajax({
     beforeSend: function(xhr){
        xhr.setRequestHeader(header,token);
     },
    url : "/api/articles/" + articleId,
    type : "delete",
    data : articleId,
    success : () => {
      alert("성공적으로 삭제되었습니다.");
    },
    error : () => {
      alert("삭제를 실패하였습니다.");
    }
  });
    });
    }