$(document).ready(function() {
    $.ajax({
        url: '/api/trade/toTrade',
        method: 'GET',
        headers: {Authorization: localStorage.getItem('token') },
        dataType: 'json'
    }).done(function (data) {
        var totalTrade = (data.length)
        $('.trade-num').text(totalTrade)
        for(let i=1; i<=3; i++){
            if(data[i-1] === undefined){
                $('.trade-message-' + i).remove()
            }else{
                var userName = data[i-1].from_user.name
                var date = data[i-1].createdD
                $('.message-content-'+i).text(userName+"님의 교환 신청!")
                $('.message-date-'+i).text(date)
            }
        }
    }).fail(function () {
    });
})