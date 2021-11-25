let currentMode = null
let currentTrade;
$(".link-info.to-trade").on('click', function() {
    currentMode = "to-trade"
    $('#myModalLabel').text("상대방 장난감")
    $(".modal-complete-button").attr('hidden', false);
    var trade_id = $(this).data("id")
    getToyList(trade_id)
})

$(".link-info.from-trade").on('click', function() {
    currentMode = "from-trade"
    $('#myModalLabel').text("내 장난감")
    var trade_id = $(this).data("id")
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
        arrayData.push(val)
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
        console.log("success")
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