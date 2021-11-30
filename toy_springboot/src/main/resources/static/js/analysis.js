<script th:inline="javascript">
    if (localStorage.getItem('data')) {
    var lastData = localStorage.getItem('data')
}

    Data = JSON.parse(lastData); //local storage에 저장된 분석결과 json 불러옴.

    var j = 0;
    for (i = 0; i < Data.length; i++) {
    // document.write("<input type='radio' name='category' value=" + i + "> " + Data[i].product_name + '</input>');
    $('#change' + [j]).text(Data[i].product_name);
    j += 1;
    $('#change' + [j]).text(Data[i].product_label.character);
    j += 1;
    $('#change' + [j]).text(Data[i].product_label.category);
    j += 1;
} //분석결과 json 화면에 표시

    var select_genre;
    var select_character;
    var select_name;
    var id_check;

    $("button[value='btnSelect']").click(function() {

    var genreList = [[${genreList}]];
    var characterList = [[${characterList}]];
    var current_toyId = [[${currentToy.toy_id}]];
    var current_shopId = [[${currentShop.shop_id}]];
    var currentToy_data;
    id_check = $(this).attr("id"); //선택한 버튼의 id 가져옴.

    if (id_check == "1") {
    select_name = $('#change' + [0]).text();
    select_character = $('#change' + [1]).text();
    select_genre = $('#change' + [2]).text();
    select(select_name, select_character, select_genre);
} else if (id_check == "2") {
    select_name = $('#change' + [3]).text();
    select_character = $('#change' + [4]).text();
    select_genre = $('#change' + [5]).text();
    select(select_name, select_character, select_genre);
} else {
    select_name = $('#change' + [6]).text();
    select_character = $('#change' + [7]).text();
    select_genre = $('#change' + [8]).text();
    select(select_name, select_character, select_genre);
}

    function select(select_name, select_character, select_genre) {

    function findList(List, select_kind, kind){
    var find_List = List.filter(function(g){
    if(g.name == select_kind){
    return true;
}
    else{
    return false;
}
})

    if(find_List.length === 0){
    alert("db에 정보 없음");
    var info = new Object();
    info.name = select_kind

    $.ajax({
    "url": "/api/category/"+kind,
    "method": "POST",
    async    : false,
    dataType : "JSON",
    "timeout": 0,
    "headers": {
    "Content-Type": "application/json",
    "Authorization": localStorage.getItem("token")
},
    "data": JSON.stringify(info),
    success: function (response) {
    console.log(JSON.stringify(response));
    alert("갱신 성공");
},
    error: function (e) {
    alert("fail");
}
});
    return info;
}
    else{
    return find_List[0];
}
} //db에 장르랑 캐릭터 검색해서 찾아보고 없으면 추가.

    var final_genre = findList(genreList, select_genre, "genre");
    var final_character = findList(characterList, select_character, "character");

    $.ajax({
    type: "GET",
    url:  "/api/toy/id?toy_id="+current_toyId,
    async    : false,
    dataType : "JSON",
    processData: false,
    contentType: "application/json",
    cache: false,
    timeout: 0,
    success: function (data) {
    console.log(typeof(data));
    currentToy_data = data;
},
    error: function (e) {
    alert("실패");
}
}); //현재 토이 객체의 정보 받아옴

    var shopInfo = new Object();
    shopInfo.shop_id = current_shopId;

    currentToy_data.productName = select_name;
    currentToy_data.genre = final_genre;
    currentToy_data.character = final_character;
    currentToy_data.shop = shopInfo;
    currentToy_data = JSON.stringify(currentToy_data);

    console.log(currentToy_data);

    $.ajax({
    type: "PUT",
    url:  "/api/toy",
    data: currentToy_data,
    async    : false,
    contentType: "application/json",
    cache: false,
    timeout: 0,
    success: function (data) {
    alert("업데이트 성공");
    location.href="/set?toy_id=" + current_toyId + "&select_num=" + id_check;
},
    error: function (e) {
    alert("fail");
}
}); //토이 객체 업데이트.

    //var new_toy = $("#currentToyId").val();
    //location.href = "/regist/set?toy_id=" + new_toy;
}
}); //선택한 항목에 대한 장르, 캐릭터, 상품명 가져와서 토이 객체에 저장

    $("#btnTag").unbind("click").bind("click", function () {
    var current_toyId = [[${currentToy.toy_id}]];
    location.href="/tag?toy_id="+current_toyId;
});
</script>