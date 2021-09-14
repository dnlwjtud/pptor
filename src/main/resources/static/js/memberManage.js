var token = $("meta[name='_csrf']").attr("content");
   var header = $("meta[name='_csrf_header']").attr("content");

//   function blockMember(loginId){
//
//        $.ajax({
//            data:JSON.stringify(loginId)
//            ,url : "/adm/manage/members/" + loginId
//            ,type : "PUT"
//            ,contentType: 'application/json'
//            ,beforeSend : function(xhr){
//                xhr.setRequestHeader(header, token);
//            }
//            ,success : function() {
//
//                alert("차단/해제 처리 완료");
//                location.reload();
//            }
//            ,error: function () {
//                alert('실패');
//            }
//        })
//
//   }

   function modifyAuth(loginId, newLevel){

           let allData = {"loginId" : loginId, "newLevel" : newLevel};

           $.ajax({
               data:JSON.stringify(allData)
               ,url : "/adm/manage/members/" + allData
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
                   alert('실패 - loginId : ' + loginId + ", newLevel : " + newLevel);
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




