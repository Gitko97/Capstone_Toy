
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
});

$(".next-toyList").on('click', function() {
    currentMode = "next"
    getToyList()
    $(this).attr("hidden",true);
    $('.before-toyList').attr("hidden",false);
    $('.finish-toyList').attr("hidden",false);
    currentShopID = $('#nextShopUserName').data("shop")
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


    $.ajax({
        url: '/api/trade',
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: Json
    }).done(function() {
        swal({
            title: "교환 신청!",
            text: "총"+toDataArray.length+"개의 장난감과 교환 신청하셨습니다.",
            icon: "success",
            button: "확인",
        }).then((value) => {
            location.href="/exchange"
        });
    }).fail(function () {
        swal({
            title: "한 개 이상의 장난감을 선택해주세요!",
            icon: "warning",
            buttons: "확인",
        })
            .then((willDelete) => {
            });
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
            //createdDate data format 바꿈
            if (val["tradeStatus"] == 0 && val["set_time"] == null){
                val.createdDate = JSON.stringify(val.createdDate).split(',')
                val.createdDate = val.createdDate[0].replace('[','')+'-'+val.createdDate[1]+'-'+val.createdDate[2]
                arrayData.push(val)
            }
        });
        const html = currentToyListTemplate(arrayData);
        $('#toy-card-list-area').append(html);
        $.each(data, function(idx, val) {
            if (val["tradeStatus"] == 0 && val["set_time"] == null){
                if (val["photo"].length == 0){
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
            }


        });

    }).fail(function () {
        swal({
            title: "신청 오류!",
            icon: "warning",
            text: "다시 시도해 주세요!",
            buttons: "확인",
        })
            .then((willDelete) => {
                location.reload();
            });
    });
}



