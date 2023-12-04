// --------------------------------------------- 결과창 전달 ---------------------------------------------

function recommendSimilar(){
    console.log(musicalId);
    console.log(document.getElementById("selectedTitle").textContent);
  
    // Id로 해당 엘리먼트를 찾아서 존재하면 값 설정
    const musicalIdInput = document.getElementById("musicalIdInput");
    const musicalTitleInput = document.getElementById("musicalTitleInput");
  
    musicalIdInput.value = musicalId;
    musicalTitleInput.value = document.getElementById("selectedTitle").textContent;
  
    // 폼 안에 해당 value 값들 포함해서 action url로 전달
    document.getElementById("recommendForm").submit();
  }