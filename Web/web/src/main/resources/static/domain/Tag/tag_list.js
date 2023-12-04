// --------------------------------------------- 태그관련 ---------------------------------------------

function redirectToUrlWithTag1(tag1) {
  let baseUrl = '/past/tags/tag1';
  let url = baseUrl + '?tag1=' + tag1;

  window.location.href = url;
}

function redirectToUrlWithTag2(tag2) {
  let baseUrl = '/past/tags/tag1&tag2';

  console.log(tag1);
  console.log(tag2);
  let url = baseUrl + '?tag1=' + tag1 + '&tag2=' + tag2; // 수정된 부분: '?' 대신 '&'

  window.location.href = url;
}

function redirectToUrlWithTag3(tag3) {
  let baseUrl = '/past/tags/allTagsSelected';
  console.log(tag1);
  console.log(tag2);
  console.log(tag3);
  let url = baseUrl + '?tag1=' + tag1 + '&tag2=' + tag2 + '&tag3=' + tag3; // 수정된 부분: '?' 대신 '&'

  window.location.href = url;
}


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