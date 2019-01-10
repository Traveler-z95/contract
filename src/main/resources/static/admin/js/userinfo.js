/**
 * 修改密码
 */
$("#update-password").on('click',function () {
    var html = ' <div class="form-row">' +

        ' <input type="password" name="oldPassWord" ' + ' placeholder="旧密码" required />'
        + ' </div>' + ' <div class="form-row">'
        + ' <input type="password" name="newPassWord" placeholder="新密码" required/>'
        + ' </div>' + ' <div class="form-row">'
        + ' <input type="password" name="confirmPassWord" placeholder="确认密码" required/>'
        + ' </div>' + ' <div class="form-row">'
        + ' </div>';

    new $.flavr({content : '修改密码', dialog : 'form', form : { content: html, method:

            'post' },
        onSubmit : function( $container, $form ){
            var oldPassWord = $("input[name='oldPassWord']").val();
            var newPassWord = $("input[name='newPassWord']").val();
            var confirmPassWord = $("input[name='confirmPassWord']").val();
            if (isEmpty(oldPassWord) || isEmpty(newPassWord) || isEmpty(confirmPassWord) ){
                autoCloseAlert("字段不能为空！！！");
                return false;
            }
            if (newPassWord != confirmPassWord){
                autoCloseAlert("两次密码不一致",1000);
                return false;
            }
            $.ajax({
                url: "/admin/password/update",
                data: $form.serialize(),
                method: 'POST',
                success: function (data) {
                    if (data.resultCode == 'success'){
                        autoCloseAlert(data.errorInfo,1000);
                        window.location.href = '/admin/logout';
                    }
                    autoCloseAlert(data.errorInfo,1000);
                    return false;
                }
            })
            return false;
        }
    });
})


/**
 * 判断字符串是否为空
 * @param str 字符串
 * @returns {boolean}
 */
function isEmpty(str) {
    if (str == null || str == '' || str == undefined){
        return true;
    }
    return false;
}


/**
 * 当点击修改个人信息按钮时，获取个人信息
 */
$("#update-userinfo").on('click',function () {
    $.ajax({
        url: "/admin/userinfo/get",
        method: "GET",
        success: function (data) {
            $('#editUserInfoContent').html(data);
            $('#editUserInfoModal').modal('show');

        }
    })
});

/**
 * 获取修改后的个人信息，保存
 * @returns {boolean}
 */
function saveInfo() {
    var nickName = $("#nickName").val();
    if(isEmpty(nickName)){
        autoCloseAlert("请输入昵称",500);
        return false;
    }
    var phone = $("#phone").val();
    if(isEmpty(phone)){
        autoCloseAlert("请输入电话号码",500);
        return false;
    }
    var eMail = $("#eMail").val();
    if (isEmpty(eMail)){
        autoCloseAlert("请输入邮箱地址",eMail);
    }

    var address = $("#address").val();
    if (isEmpty(address)){
        autoCloseAlert("请输入地址",address);
    }

    var signature = $("#signature").val();
    if (isEmpty(signature)){
        autoCloseAlert("请输入个性签名!",signature);
    }

    var data = $("#infoForm").serialize();

    $.ajax({
        url: "/admin/userinfo/update",
        method: "POST",
        data: data,
        success: function (data) {
            if (data.resultCode == 'success'){
                autoCloseAlert(data.errorInfo,1000);
                cancelSaveInfo();
            }
            autoCloseAlert(data.errorInfo,1000);
            return false;
        }
    });
}


function cancelSaveInfo() {
    $('#editUserInfoModal').modal('hide');
}