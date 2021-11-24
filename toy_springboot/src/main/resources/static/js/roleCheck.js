

document.addEventListener("DOMContentLoaded", () => {
    $.ajax({
        type: "GET",
        url: "/role",
        contentType: "application/json",
        headers: {Authorization: localStorage.getItem('token') },
        success: function () {

        },
        error: function () {
            alert('회원전용 페이지입니다. 로그인 후 이용해주세요.');
            window.location.href = "/signIn"
        }
    });
});