let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

let isCheckedLoginIdDuple = false;
let isCheckedLoginPwdDuple = false;
let isCheckedEmailDuple = false;
let isCheckedNickDuple = false;

function checkConfirmStatus() {

    let confirmBtn = $('#sign-in-btn');

    if ( isCheckedLoginIdDuple && isCheckedLoginPwdDuple && isCheckedEmailDuple && isCheckedNickDuple ) {

        if ( confirmBtn.hasClass('bg-gray-700') ) {
            confirmBtn.removeClass('bg-gray-700');
            confirmBtn.addClass('bg-black');
            confirmBtn.prop("disabled", false);
        }

    } else if ( !isCheckedLoginIdDuple || !isCheckedLoginPwdDuple || !isCheckedEmailDuple || !isCheckedNickDuple ) {

        if ( confirmBtn.hasClass('bg-black') ) {
            confirmBtn.removeClass('bg-black');
            confirmBtn.addClass('bg-gray-700');
            confirmBtn.prop("disabled", true);
        }

    }

}

function checkPwd() {

    let inputtedPwd = $('.pwd-form').val();
    let repeatedPwd = $('#pwd-check').val();
    let inputtedPwdBox = $('.pwd-form');
    let repeatedPwdBox = $('#pwd-check');

    if ( repeatedPwd == "" && (inputtedPwd != repeatedPwd || inputtedPwd == repeatedPwd)) {
        inputtedPwdBox.addClass('border-custom-red');
        repeatedPwdBox.addClass('border-custom-red');
        checkConfirmStatus();
    } else if ( inputtedPwd == repeatedPwd ) {
        if ( repeatedPwdBox.hasClass('border-custom-red') || inputtedPwdBox.hasClass('border-custom-red') ) {
            repeatedPwdBox.removeClass('border-custom-red');
            inputtedPwdBox.removeClass('border-custom-red');
            repeatedPwdBox.addClass('border-custom-blue');
            inputtedPwdBox.addClass('border-custom-blue');
            isCheckedLoginPwdDuple = true;
            checkConfirmStatus();
        }

    } else if ( repeatedPwd != inputtedPwd ) {
        if ( repeatedPwdBox.hasClass('border-custom-red') || inputtedPwdBox.hasClass('border-custom-red') ) {
            isCheckedLoginPwdDuple = false;
            checkConfirmStatus();
        } else if ( repeatedPwdBox.hasClass('border-custom-blue') && inputtedPwdBox.hasClass('border-custom-blue') ) {
            repeatedPwdBox.removeClass('border-custom-blue');
            inputtedPwdBox.removeClass('border-custom-blue');
            repeatedPwdBox.addClass('border-custom-red');
            inputtedPwdBox.addClass('border-custom-red');
            isCheckedLoginPwdDuple = false;
            checkConfirmStatus();
        }
    }

}

function checkNickname() {

    let nickname = $('#nickname').val();

    $.ajax({
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url: "/api/members/check/nick/" + nickname,
        type: "POST",
        contentType: "application/json",
        dataType : "text",
        data : JSON.stringify(nickname),
        success : (result) => {
            if ( result === 'true' ) {
                isCheckedNickDuple = false;
                checkConfirmStatus();
                $('.nickname_error-1').css("display","inline-block");
                $('.nickname_error-2').css("display","none");
                return;
            } else if ( result === 'false' ) {
                isCheckedNickDuple = true;
                checkConfirmStatus();
                $('.nickname_error-1').css("display","none");
                $('.nickname_error-2').css("display","inline-block");
                return;
            }

        },
        error : (data) => {
            console.log("????????? ?????? ????????? ?????????????????????.");
            location.reload();
        }
    });

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
                checkConfirmStatus();
                $('.id_error-1').css("display","inline-block");
                $('.id_error-2').css("display","none");
                return;
            } else if ( result === 'false' ) {
                isCheckedLoginIdDuple = true;
                checkConfirmStatus();
                $('.id_error-1').css("display","none");
                $('.id_error-2').css("display","inline-block");
                return;
            }

        },
        error : (data) => {
            console.log("????????? ?????? ????????? ?????????????????????.");
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
                checkConfirmStatus();
                $('.email_error-1').css("display","inline-block");
                $('.email_error-2').css("display","none");
                return;
            } else if ( result === 'false' ) {
                isCheckedEmailDuple = true;
                checkConfirmStatus();
                $('.email_error-1').css("display","none");
                $('.email_error-2').css("display","inline-block");
                return;
            }

        },
        error : (data) => {
            console.log("????????? ?????? ????????? ?????????????????????.");
            location.reload();
        }
    });

};