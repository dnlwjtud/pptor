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

   function movePage(page){

//           $.ajax({
//               data:JSON.stringify(page)
//               ,url : "/adm/manage/members?page=" + page
//               ,type : "GET"
//               ,contentType: 'application/json'
//               ,beforeSend : function(xhr){
//                   xhr.setRequestHeader(header, token);
//               }
//               ,success : function() {
//
//                   alert("페이지 이동 성공");
//                   location.reload();
//               }
//               ,error: function () {
//                   alert('실패');
//               }
//           })

      }