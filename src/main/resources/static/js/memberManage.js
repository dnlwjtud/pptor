var token = $("meta[name='_csrf']").attr("content");
   var header = $("meta[name='_csrf_header']").attr("content");

   function blockMember(loginId){

        $.ajax({
            data:JSON.stringify(loginId)
            ,url : "/adm/manage/members/" + loginId
            ,type : "PUT"
            ,contentType: 'application/json'
            ,beforeSend : function(xhr){
                xhr.setRequestHeader(header, token);
            }
            ,success : function() {

                alert("차단/해제 처리 완료");
                location.reload();
            }
            ,error: function () {
                alert('실패');
            }
        })

   }

   function delMember(loginId){

        $.ajax({
            data:JSON.stringify(loginId)
            ,url : "/adm/manage/members/" + loginId
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

function addSelected(authLevel){
        switch(authLevel){
            case "1" :
                $("#authLevel").val("1").prop("selected", true);
                break;
            case "3" :
                $("#authLevel").val("3").prop("selected", true);
                break;
            case "4" :
                $("#authLevel").val("4").prop("selected", true);
                break;
            case "7" :
                $("#authLevel").val("7").prop("selected", true);
                break;
        }

    }

   $(document).ready(function(){

   });

