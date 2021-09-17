function addFavorite(articleId){

    $.ajax({
        data:JSON.stringify(articleId)
        ,url : "/api/favorite/" + articleId
        ,type : "POST"
        ,contentType: 'application/json'
        ,beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        }
        ,success : function() {

            alert("즐찾완");
            location.reload();
        }
        ,error: function () {
            alert('실패');
        }
    })

}

function delFavorite(articleId){

    $.ajax({
        data:JSON.stringify(articleId)
        ,url : "/api/favorite/" + articleId
        ,type : "DELETE"
        ,contentType: 'application/json'
        ,beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        }
        ,success : function() {

            alert("즐찾삭");
            location.reload();
        }
        ,error: function () {

            alert('실패');
        }
    })

}