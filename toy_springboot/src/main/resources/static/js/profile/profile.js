let currentMode = null
let currentTrade;

$(document).ready(function() {
    // Card Multi Select
    $(".trade-status").each(function (index){
        switch ($(this).data("status")){
            case 0:
                break
            case 1:
                $(this).closest(".trade-status-area").css("background-color", "lightgreen")
                break
            case 2:
                $(this).closest(".trade-status-area").css("background-color", "lightcoral")
                break
        }
    });

});

$(".modal-complete-button").on('click', function() {
    trade_id = $(this).data("trade_id")
    $.ajax({
        url: '/api/trade/' + trade_id+"/complete",
        method: 'POST',
        headers: {Authorization: localStorage.getItem('token') },
        dataType: 'json'
    }).done(function (data) {
        swal({
            title: "수락 성공!",
            text: "교환 신청을 수락하셨습니다!",
            icon: "success",
            button: "확인",
        }).then((value) => {
            location.reload();
        });
    }).fail(function () {
        alert("수락 불가능")
        location.reload();
    });
})

$(".modal-delete-button").on('click', function() {
    trade_id = $(this).data("trade_id")
    swal({
        title: "Are you sure?",
        text: "정말로 해당 신청을 거부하시겠습니다?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    url: '/api/trade/' + trade_id+"/delete",
                    method: 'POST',
                    headers: {Authorization: localStorage.getItem('token') },
                    dataType: 'json'
                }).done(function (data) {
                    location.reload();
                }).fail(function () {
                    alert("수락 불가능")
                    location.reload();
                });
            } else {
                swal("Your imaginary file is safe!");
            }
        });

})

$(".link-info.to-trade").on('click', function() {
    currentMode = "to-trade"
    $('#myModalLabel').text("상대방 장난감")
    var trade_id = $(this).data("id")
    $(".modal-complete-button").attr('hidden', false).data("trade_id", trade_id)
    $(".modal-delete-button").data("trade_id", trade_id)
    getToyList(trade_id)
})

$(".link-info.from-trade").on('click', function() {
    currentMode = "from-trade"
    $('#myModalLabel').text("내 장난감")
    var trade_id = $(this).data("id")
    $(".modal-delete-button").data("trade_id", trade_id)
    getToyList(trade_id)
})

$(".modal-next-button").on('click', function() {
    if(currentMode === "to-trade"){
        $('#myModalLabel').text("상대가 고른 내 장난감")
    }else{
        $('#myModalLabel').text("내가 고른 상대방 장난감")
    }
    showToyCard(currentTrade["to_toy"])
    $(".modal-before-button").attr('hidden', false);
    $(".modal-next-button").attr('hidden', true);
})

$(".modal-before-button").on('click', function() {
    if(currentMode === "to-trade"){
        $('#myModalLabel').text("상대방 장난감")
    }else{
        $('#myModalLabel').text("내 장난감")
    }
    showToyCard(currentTrade["from_toy"])
    $(".modal-before-button").attr('hidden', true);
    $(".modal-next-button").attr('hidden', false);
})

const currentToyListArea = $("#toyList-card-area").html();
const currentToyListTemplate = Handlebars.compile(currentToyListArea);


function showToyCard(toyList){
    $(".removed-area").remove()

    const arrayData = [];
    $.each(toyList, function(idx, val) {
        //createdDate data format 바꿈
        if(JSON.stringify(val.createdDate).indexOf(",")!=-1){
            val.createdDate = JSON.stringify(val.createdDate).split(',');
            val.createdDate = val.createdDate[0].replace('[','')+'-'+val.createdDate[1]+'-'+val.createdDate[2]
            arrayData.push(val)
            console.log(arrayData)
        }else{
            arrayData.push(val)
        }
    });
    const html = currentToyListTemplate(arrayData);
    $('#toy-card-list-area').append(html);
    $.each(toyList, function(idx, val) {
        if (val["photo"].length == 0){
            console.log("@")
        }else{
            var img_id = "#"+val["toy_id"]
            var imageByte = val["photo"]
            $(img_id).attr('src', 'data:image/png;base64,'+imageByte[0]["imageByte"]);
        }


    });
}

function getToyList(trade_id){
    $.ajax({
        url: '/api/trade/' + trade_id,
        method: 'GET',
        headers: {Authorization: localStorage.getItem('token') },
        dataType: 'json'
    }).done(function (data) {
        currentTrade = data
        showToyCard(currentTrade["from_toy"])
        $('#modalCart').modal('show');
    }).fail(function () {
        location.reload();
    });
}


$('#modalCart').on('hidden.bs.modal', function () {
    $(".modal-complete-button").attr('hidden', true);
    $(".modal-before-button").attr('hidden', true);
    $(".modal-next-button").attr('hidden', false);
})