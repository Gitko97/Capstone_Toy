
$(document).ready(function() {
    // Card Multi Select
    currentMode="me"
    $('input[type=checkbox]').click(function() {
        if ($(this).parent().parent().hasClass('active'))
        { $(this).parent().parent().removeClass('active'); }
        else
        { $(this).parent().parent().addClass('active'); }
    });
    getToyList()
});


const clicked_my_toy_list = new Set();
const clicked_opponent_toy_list = new Set();

const currentToyListArea = $("#toyList-card-area").html();
const currentToyListTemplate = Handlebars.compile(currentToyListArea);

$('#card-container').on('click', '.card-pf-view-checkbox>input', function(event) {
    var $currentCard = $(this).closest('.card-pf');
    if ($currentCard.hasClass('active')){
        $currentCard.removeClass('active')
        var clickedToyId = $currentCard.find(".img-thumbnail").attr('id');
        if(currentMode === "me"){
            clicked_my_toy_list.delete(clickedToyId)
        }else{
            clicked_opponent_toy_list.delete(clickedToyId)
        }

    }else{
        $currentCard.addClass('active')
        var clickedToyId = $currentCard.find(".img-thumbnail").attr('id');
        if(currentMode === "me"){
            clicked_my_toy_list.add(clickedToyId)
        }else{
            clicked_opponent_toy_list.add(clickedToyId)
        }
    }
    console.log(clicked_my_toy_list)
    console.log(clicked_opponent_toy_list)
});

$(".next-toyList").on('click', function() {
    currentMode = "next"
    getToyList()
    $(this).attr("hidden",true);
    $('.before-toyList').attr("hidden",false);
    $('.finish-toyList').attr("hidden",false);
    currentShopID = $('#nextShopID').data("shop")
    $('#current_shop').text(currentShopID +"의 장터")
});

$(".before-toyList").on('click', function() {
    currentMode = "me"
    getToyList()
    $(this).attr("hidden",true);
    $('.next-toyList').attr("hidden",false);
    $('.finish-toyList').attr("hidden",true);
    $('#current_shop').text("내 장난감 장터")
});

$(".finish-toyList").on('click', function() {
    var fromDataArray = new Array();
    var toDataArray = new Array();
    for ( let toy_id of clicked_my_toy_list) {
        var from_toy_id = new Object();
        from_toy_id.toy_id = toy_id
        fromDataArray.push(from_toy_id)
    }
    for ( let toy_id of clicked_opponent_toy_list) {
        var to_toy_id = new Object();
        to_toy_id.toy_id = toy_id
        toDataArray.push(to_toy_id)
    }

    var finishObject = new Object();

    finishObject.from_toy = fromDataArray
    finishObject.to_toy = toDataArray

    var Json = JSON.stringify(finishObject);

    console.log(Json)

    $.ajax({
        url: '/api/trade',
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: Json
    }).done(function() {
        alert("신청 성공!")
        location.reload();
    }).fail(function () {
        $('#errorMSG').text("한개 이상의 장난감을 선택해주세요!")
        $('#alarmModal').modal('show');
    });
});

function getToyList(){
    var currentShopID
    $(".removed-area").remove()
    if(currentMode === "me"){
        currentShopID = $('#myShopID').data("shop")
    }else{
        currentShopID = $('#nextShopID').data("shop")
    }

    $.ajax({
        url: '/api/toy/shop/' + currentShopID,
        method: 'GET',
        dataType: 'json'
    }).done(function (data) {
        const arrayData = [];
        $.each(data, function(idx, val) {
            arrayData.push(val)
        });
        const html = currentToyListTemplate(arrayData);
        $('#toy-card-list-area').append(html);
        $.each(data, function(idx, val) {
            if (val["photo"].length == 0){
                console.log("@")
            }else{
                var img_id = "#"+val["toy_id"]
                var imageByte = val["photo"]
                $(img_id).attr('src', 'data:image/png;base64,'+imageByte[0]["imageByte"]);
                if(currentMode === "me"){
                    if(clicked_my_toy_list.has(''+val["toy_id"])){
                        $('#'+val["toy_id"]).closest('.card-pf.card-pf-view.card-pf-view-select.card-pf-view-multi-select').addClass('active')
                        $('#'+val["toy_id"]).closest('.card-pf.card-pf-view.card-pf-view-select.card-pf-view-multi-select').find('.clickedCheckBox').prop('checked', true);
                    }
                }else{
                    if(clicked_opponent_toy_list.has(''+val["toy_id"])){
                        $('#'+val["toy_id"]).closest('.card-pf.card-pf-view.card-pf-view-select.card-pf-view-multi-select').addClass('active')
                        $('#'+val["toy_id"]).closest('.card-pf.card-pf-view.card-pf-view-select.card-pf-view-multi-select').find('.clickedCheckBox').prop('checked', true);
                    }
                }
            }


        });

    }).fail(function () {
        $('#errorMSG').text("교환 신청 실패.. 다시 시도해주세요.")
        $('#alarmModal').modal('show');
        location.reload();
    });
}



