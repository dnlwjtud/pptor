var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

   function blindArticle(articleId){

        $.ajax({
            data:JSON.stringify(articleId)
            ,url : "/adm/manage/articles/" + articleId
            ,type : "PUT"
            ,contentType: 'application/json'
            ,beforeSend : function(xhr){
                xhr.setRequestHeader(header, token);
            }
            ,success : function() {

                alert("블라인드/해제 처리 완료");
                location.reload();
            }
            ,error: function () {
                alert('실패');
            }
        })

   }



   function delArticle(id){

        $.ajax({
            data:JSON.stringify(id)
            ,url : "/adm/manage/articles/" + id
            ,type : "DELETE"
            ,contentType: 'application/json'
            ,beforeSend : function(xhr){
                xhr.setRequestHeader(header, token);
            }
            ,success : function() {

                alert("삭제되었습니다");
                location.reload();
            }
            ,error: function () {

                alert('실패');
            }
        })

   }

