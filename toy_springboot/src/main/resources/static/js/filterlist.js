var dataList1 = document.getElementById('json-datalist1');
var dataList2 = document.getElementById('json-datalist2');
var input1 = document.getElementById('ajax1');
var input2 = document.getElementById('ajax2');

var request1 = new XMLHttpRequest();
var request2 = new XMLHttpRequest();

$("json-datalist1").empty()
$("json-datalist2").empty()
$("ajax1").empty()
$("ajax1").empty()


request1.onreadystatechange = function (response) {
    if (request1.readyState === 4) {
        if (request1.status === 200) {

            var characterJSON = JSON.parse(request1.responseText);

            characterJSON.forEach(function (item) {
                var option = document.createElement('option');
                option.value = item.name;
                dataList1.appendChild(option);
            });

            input1.placeholder = "캐릭터를 입력하세요";
        } else {
            input1.placeholder = "에러";
        }
    }
};

input1.placeholder = "로딩중...";

request1.open('GET', 'http://localhost:8092/api/category/character', true);
request1.send();

request2.onreadystatechange = function (response) {
    if (request2.readyState === 4) {
        if (request2.status === 200) {

            var genreJSON = JSON.parse(request2.responseText);

            genreJSON.forEach(function (item) {
                var option = document.createElement('option');
                option.value = item.name;
                dataList2.appendChild(option);
            });

            input2.placeholder = "장르를 입력하세요";
        } else {
            input2.placeholder = "에러";
        }
    }
};

input2.placeholder = "로딩중...";

request2.open('GET', 'http://localhost:8092/api/category/genre', true);
request2.send();
