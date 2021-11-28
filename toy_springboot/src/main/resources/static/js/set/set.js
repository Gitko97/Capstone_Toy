$(document).ready(function() {
// Update the count down every 1 second
    $.each($(".timer"), function (idx, val) {
        var date = val.getAttribute("data-date")
        var countDownDate = new Date(date).getTime();
        countDownDate += 604800000

        var now = new Date().getTime();

        // Find the distance between now and the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Display the result in the element with id="demo"
        // $(val).text(days + "d " + hours + "h "+ minutes + "m " + seconds + "s ")
        $(val).text(days + "일 " + hours + "시간 "+ minutes + "분 " + seconds + "초")

        var x = setInterval(function() {

            // Get today's date and time
            var now = new Date().getTime();

            // Find the distance between now and the count down date
            var distance = countDownDate - now;

            // Time calculations for days, hours, minutes and seconds
            var days = Math.floor(distance / (1000 * 60 * 60 * 24));
            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            // Display the result in the element with id="demo"
            // document.getElementById("demo").innerHTML = days + "일 " + hours + "시간 "+ minutes + "분 " + seconds + "초";
            // $(val).text(days + "일 " + hours + "시간 "+ minutes + "분 ")
            $(val).text(days + "일 " + hours + "시간 "+ minutes + "분 " + seconds + "초")


            // If the count down is finished, write some text
            if (distance < 0) {
                clearInterval(x);
                document.getElementById("demo").innerHTML = "EXPIRED";
            }
        }, 1000);

    })
});

$(".auction-button").on('click', function() {
    var clicked_set_id = $(this).attr("id")
    var input_point = $('#'+clicked_set_id+'_inputPoint').val()
    console.log(input_point)
    if(input_point===""){
        alert("포인트를 입력해주세요")
        return;
    }

    $.ajax({
        url: '/api/setGoods/regist/' + clicked_set_id+"?point="+input_point,
        method: 'POST',
        "timeout": 0,
    }).done(function() {
        location.reload();
    }).fail(function (data) {
        alert("포인트가 충분하지 않습니다.")
    });
})
