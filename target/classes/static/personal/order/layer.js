var layer = {
    error:function (msg) {
        _alert('error', msg, 2000);
    },
    success:function (msg) {
        _alert('success', msg, 1500);
    },
    dynamic:function (id) {
        dynamicNumBtn(id)
    }
}

function _alert(type, msg, time) {
    swal({
        position: 'top-end',
        type: type,
        title: msg,
        showConfirmButton: false,
        timer: time
    })
}

function dynamicNumBtn(id) {
    $(id)[0].disabled = true;
    var time = 60;
    var ref = setInterval(function(){
        if (time > 0){
            time = time -1;
            $(id)[0].innerText = time + 's后重试';
        }else {
            clearInterval(ref);
            $(id)[0].innerText = ' 获取验证码 ';
            $(id)[0].disabled = false;
        }
    },1000);
}
