let musicalId = 0;

document.addEventListener("DOMContentLoaded", function () {
  var images = document.querySelectorAll("#slider-image");
  images.forEach(function (image, index) {
    image.addEventListener("click", function () {
      imageClickHandler(index);
    });
  });

  var selectedIndex = 0; // 초기값 설정

  function updateSelectedImage() {
    const selectedImage = document.getElementById("selectedImage");
    const selectedTitle = document.getElementById("selectedTitle"); // 선택된 제목을 나타내는 요소
    const selectedSys = document.getElementById("selectedSys");
    const selectedGenre = document.getElementById("selectedGenre");
    const selectedDate = document.getElementById("selectedDate");
    const selectedLocation = document.getElementById("selectedLocation");
    const selectedRunning = document.getElementById("selectedRunning");
    const index = selectedIndex % musicals.length; // index를 musicals 배열의 길이로 나눈 나머지를 사용하여 배열을 순환하도록 함
    selectedImage.src = musicals[index].posterUrl;
    selectedTitle.textContent = musicals[index].title; // 선택된 이미지의 제목 업데이트
    selectedSys.textContent = musicals[index].synopsis;
    selectedGenre.textContent = musicals[index].genre;
    selectedDate.textContent = musicals[index].date;
    selectedLocation.textContent = musicals[index].location;
    selectedRunning.textContent = musicals[index].runningTime;


    musicalId = musicals[index].musicalId;

    // sconsole.log(musicalId)
    // window.location.href = "/list/"+musicals[index].musicalId;
  }

  function imageClickHandler(index) {
    console.log("Selected Index:", index);
    selectedIndex = index;
    updateSelectedImage();
  }

  updateSelectedImage(); // 초기 이미지 업데이트
})