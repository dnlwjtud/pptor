let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

let isCheckedLoginIdDuple = false;
let isCheckedLoginPwdDuple = false;
let isCheckedEmailDuple = false;

function checkConfirmStatus() {

    let confirmBtn = $('#sign-in-btn')

    if ( isCheckedLoginIdDuple && isCheckedLoginPwdDuple && isCheckedEmailDuple ) {

        if ( confirmBtn.hasClass('bg-gray-200') ) {
            confirmBtn.removeClass('bg-gray-200');
            confirmBtn.addClass('bg-black');
            confirmBtn.prop("disabled", false);
        }

    } else if ( !isCheckedLoginIdDuple || !isCheckedLoginPwdDuple || !isCheckedEmailDuple ) {

        if ( confirmBtn.hasClass('bg-black') ) {
            confirmBtn.removeClass('bg-black');
            confirmBtn.addClass('bg-gray-200');
            confirmBtn.prop("disabled", true);
        }

    }

}

function checkId(){

    let loginId = $('#loginId').val();

    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url: "/api/members/check/id/" + loginId,
        type: "POST",
        contentType: "application/json",
        dataType : "text",
        data : JSON.stringify(loginId),
        success : (result) => {
            if ( result === 'true' ) {
                isCheckedLoginIdDuple = false;
                $('.id_error-1').css("display","inline-block");
                $('.id_error-2').css("display","none");

                console.log("현재 체크 상태 : " + isCheckedLoginIdDuple);
                return;
            } else if ( result === 'false' ) {
                isCheckedLoginIdDuple = true;
                $('.id_error-1').css("display","none");
                $('.id_error-2').css("display","inline-block");

                console.log("현재 체크 상태 : " + isCheckedLoginIdDuple);
                return;
            }

        },
        error : (data) => {
            console.log("아이디 중복 확인에 실패하였습니다.");
            location.reload();
        }
    });

};

function checkEmail(){

    let email = $('#email').val();

     $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url: "/api/members/check/email/" + email,
        type: "POST",
        contentType: "application/json",
        dataType : "text",
        data : JSON.stringify(email),
        success : (result) => {
            if ( result === 'true' ) {
                isCheckedEmailDuple = false;
                $('.email_error-1').css("display","inline-block");
                $('.email_error-2').css("display","none");

                console.log("현재 체크 상태 : " + isCheckedEmailDuple);
                return;
            } else if ( result === 'false' ) {
                isCheckedEmailDuple = true;
                $('.email_error-1').css("display","none");
                $('.email_error-2').css("display","inline-block");

                console.log("현재 체크 상태 : " + isCheckedEmailDuple);
                return;
            }

        },
        error : (data) => {
            console.log("이메일 중복 확인에 실패하였습니다.");
            location.reload();
        }
    });

};