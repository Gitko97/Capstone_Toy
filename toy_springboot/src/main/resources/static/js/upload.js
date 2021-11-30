var fileData;
var isRun = false;

// Select Upload-Area
const uploadArea = document.querySelector('#uploadArea')

// Select Drop-Zoon Area
const dropZoon = document.querySelector('#dropZoon');

// Loading Text
const loadingText = document.querySelector('#loadingText');

// Slect File Input
const fileInput = document.querySelector('#fileInput');

// Select Preview Image
const previewImage = document.querySelector('#previewImage');

// File-Details Area
const fileDetails = document.querySelector('#fileDetails');

// Uploaded File
const uploadedFile = document.querySelector('#uploadedFile');

// Uploaded File Info
const uploadedFileInfo = document.querySelector('#uploadedFileInfo');

// Uploaded File  Name
const uploadedFileName = document.querySelector('.uploaded-file__name');

// Uploaded File Icon
const uploadedFileIconText = document.querySelector('.uploaded-file__icon-text');

// Uploaded File Counter
const uploadedFileCounter = document.querySelector('.uploaded-file__counter');

// ToolTip Data
const toolTipData = document.querySelector('.upload-area__tooltip-data');

// Images Types
const imagesTypes = [
    "jpeg",
    "png",
    "svg",
    "gif"
];

// Append Images Types Array Inisde Tooltip Data
toolTipData.innerHTML = [...imagesTypes].join(', .');

// When (drop-zoon) has (dragover) Event
dropZoon.addEventListener('dragover', function (event) {
    // Prevent Default Behavior
    event.preventDefault();

    // Add Class (drop-zoon--over) On (drop-zoon)
    dropZoon.classList.add('drop-zoon--over');
});

// When (drop-zoon) has (dragleave) Event
dropZoon.addEventListener('dragleave', function (event) {
    // Remove Class (drop-zoon--over) from (drop-zoon)
    dropZoon.classList.remove('drop-zoon--over');
});

// When (drop-zoon) has (drop) Event
dropZoon.addEventListener('drop', function (event) {
    // Prevent Default Behavior
    event.preventDefault();

    // Remove Class (drop-zoon--over) from (drop-zoon)
    dropZoon.classList.remove('drop-zoon--over');

    // Select The Dropped File
    const file = event.dataTransfer.files[0];
    // Call Function uploadFile(), And Send To Her The Dropped File :)
    uploadFile(file);
});

// When (drop-zoon) has (click) Event
dropZoon.addEventListener('click', function (event) {
    // Click The (fileInput)
    fileInput.click();
});

// When (fileInput) has (change) Event
fileInput.addEventListener('change', function (event) {
    // Select The Chosen File
    const file = event.target.files[0];

    // Call Function uploadFile(), And Send To Her The Chosen File :)
    uploadFile(file);
});

// Upload File Function
function uploadFile(file) {
    fileData = file;
    //console.log(fileData);
    //console.log(typeof (fileData));

    // FileReader()
    const fileReader = new FileReader();
    // File Type
    const fileType = file.type;
    // File Size
    const fileSize = file.size;

    // If File Is Passed from the (File Validation) Function
    if (fileValidate(fileType, fileSize)) {
        // Add Class (drop-zoon--Uploaded) on (drop-zoon)
        dropZoon.classList.add('drop-zoon--Uploaded');

        // Show Loading-text
        loadingText.style.display = "block";
        // Hide Preview Image
        previewImage.style.display = 'none';

        // Remove Class (uploaded-file--open) From (uploadedFile)
        uploadedFile.classList.remove('uploaded-file--open');
        // Remove Class (uploaded-file__info--active) from (uploadedFileInfo)
        uploadedFileInfo.classList.remove('uploaded-file__info--active');

        // After File Reader Loaded
        fileReader.addEventListener('load', function () {
            // After Half Second
            setTimeout(function () {
                // Add Class (upload-area--open) On (uploadArea)
                uploadArea.classList.add('upload-area--open');

                // Hide Loading-text (please-wait) Element
                loadingText.style.display = "none";
                // Show Preview Image
                previewImage.style.display = 'block';

                // Add Class (file-details--open) On (fileDetails)
                fileDetails.classList.add('file-details--open');
                // Add Class (uploaded-file--open) On (uploadedFile)
                uploadedFile.classList.add('uploaded-file--open');
                // Add Class (uploaded-file__info--active) On (uploadedFileInfo)
                uploadedFileInfo.classList.add('uploaded-file__info--active');
            }, 500); // 0.5s

            // Add The (fileReader) Result Inside (previewImage) Source
            previewImage.setAttribute('src', fileReader.result);

            // Add File Name Inside Uploaded File Name
            uploadedFileName.innerHTML = file.name;

            // Call Function progressMove();
            progressMove();
        });

        // Read (file) As Data Url
        fileReader.readAsDataURL(file);
    } else { // Else

        this; // (this) Represent The fileValidate(fileType, fileSize) Function

    };
};

// Progress Counter Increase Function
function progressMove() {
    // Counter Start
    let counter = 0;

    // After 600ms
    setTimeout(() => {
        // Every 100ms
        let counterIncrease = setInterval(() => {
            // If (counter) is equle 100
            if (counter === 100) {
                // Stop (Counter Increase)
                clearInterval(counterIncrease);
            } else { // Else
                // plus 10 on counter
                counter = counter + 10;
                // add (counter) vlaue inisde (uploadedFileCounter)
                uploadedFileCounter.innerHTML = `${counter}%`
            }
        }, 100);
    }, 600);
};


// Simple File Validate Function
function fileValidate(fileType, fileSize) {
    // File Type Validation
    let isImage = imagesTypes.filter((type) => fileType.indexOf(`image/${type}`) !== -1);

    // If The Uploaded File Type Is 'jpeg'
    if (isImage[0] === 'jpeg') {
        // Add Inisde (uploadedFileIconText) The (jpg) Value
        uploadedFileIconText.innerHTML = 'jpg';
    } else { // else
        // Add Inisde (uploadedFileIconText) The Uploaded File Type
        uploadedFileIconText.innerHTML = isImage[0];
    };

    // If The Uploaded File Is An Image
    if (isImage.length !== 0) {
        // Check, If File Size Is 10MB or Less
        if (fileSize <= 10000000) {
            return true;
        } else { // Else File Size
            return alert('Please Your File Should be 2 Megabytes or Less');
        };
    } else { // Else File Type
        return alert('Please make sure to upload An Image File Type');
    };
};

// :)

$("#btnUpload").unbind("click").bind("click", function () {

    if(isRun == true) {
        return;
    } //중복실행방지

    isRun = true;

    const form =  fileData;
    const data = new FormData();
    data.append('file', form);

    var shop_id = document.getElementById("currentShopID").value;

    var toy_url = "/api/toy?shop_id="+shop_id;
    var upload_url = "/api/photo/upload_image/";

    var toyId;

    //토이 객체 생성 및 토이 id 받아오기
    $.ajax({
        "url": toy_url,
        "method": "POST",
        async    : false,
        dataType : "JSON",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json",
            "Authorization": localStorage.getItem("token")
        },
        "data": JSON.stringify({}), //토이 빈 객체 생성
        success: function (response) {
            //console.log(response);
            toyId = response; // 생성 객체의 toy_id
            //alert("생성 성공");
        },
        error: function (e) {
            alert("fail");
        }
    });

    //생성한 객체의 toy id를 통해 이미지 업로드
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: upload_url+toyId.toy_id,
        data: data,
        async    : false,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 0,
        success: function (data) {
            //alert("업로드 성공");
        },
        error: function (e) {
            alert("fail");
        }
    });

    //이미지 분석 및 페이지 연결
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "http://13.125.48.240:8000/finder/api/image_file",
        data: data,
        async    : false,
        dataType : "JSON",
        processData: false,
        contentType: false,
        cache: false,
        timeout: 0,
        success: function (data) {
            localStorage.setItem("data", JSON.stringify(data)); //분석결과 로컬 스토리지에 저장
            // localStorage.setItem("toy_id", toyId.toy_id);
            //alert("분석 성공");
            location.href="/analysis?toy_id="+toyId.toy_id;
        },
        error: function (e) {
            alert("fail");
        }
    });
});