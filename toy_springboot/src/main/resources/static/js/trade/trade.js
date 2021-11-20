
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
    console.log()
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
});

$(".before-toyList").on('click', function() {
    currentMode = "me"
    getToyList()
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
            var img_id = "#"+val["toy_id"]
            var imageByte = val["photo"]
            $(img_id).attr('src', 'data:image/png;base64,'+imageByte[0]["imageByte"]);
        });

    }).fail(function () {
        alert("등록 실패");
        location.reload();
    });
}



