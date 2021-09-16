console.log("연결 성공");

let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

function checkId(){
    let loginId = $('#loginId').val();
    let sendData = {"loginId":loginId}
    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url:"/api/idCheck/" + loginId,
        type: "POST",
        dataType : "text",
        data : sendData,
        contentType: "application/json; charset=UTF-8",
        success : (data) => {
            if (data == true) {
//                console.log("존재하는 아이디");
                $('.id_error-1').css("display","inline-block");
                $('.id_error-2').css("display","none")
            } else {
//                console.log("사용가능한 아이디");
                $('.id_error-1').css("display","none");
                $('.id_error-2').css("display","inline-block");
            }
        },
        error : (data) => {
            console.log("에러..");
        }
    });
};

function checkEmail(){
    let email = $('#email').val();
    let sendData = {"email":email}
    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url:"/api/idCheck/" + email,
        type: "POST",
        dataType : "text",
        data : sendData,
        contentType: "application/json; charset=UTF-8",
        success : (data) => {
            if (data == true) {
//                console.log("존재하는 이메일");
                $('.email_error-1').css("display","inline-block");
                $('.email_error-2').css("display","none")
            } else {
//                console.log("사용가능한 이메일");
                $('.email_error-1').css("display","none");
                $('.email_error-2').css("display","inline-block");
            }
        },
        error : (data) => {
            console.log("에러..");
        }
    });
};