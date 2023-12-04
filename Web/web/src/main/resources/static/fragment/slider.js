// --------------------------------------------------------------- 슬라이더 ---------------------------------------------
// musical_list_slider.html 에 정의됨.
// JQuery 문법 $(선택자).동작함수();
// 이건 태그결과 js
$('.show-slides').slick(
{
    infinite: false, // 무한반복
    slidesToShow: 4, // 화면에 보여질 갯수
    slidesToScroll: 4, // 슬라이드 갯수
    dots: true, // 네비버튼
    variableWidth: true,
    speed: 400
});

/*<![CDATA[*/
// ml 결과 js
// console.log(slideNum);
$('.show-one-slide').slick(
{
    infinite: true, // 무한반복
    slidesToShow: 1, // 화면에 보여질 갯수
    slidesToScroll: 1, // 슬라이드 갯수
    centerMode: true,
    dots: true, // 네비버튼
    draggable: false, // 드래그기능 
    speed: 400
});

if (slideNum == '1') {
$('.slick-prev').on('click', function () {
    var currentSlideIndex = $('.show-one-slide').slick('slickCurrentSlide');
    imageClickHandler(currentSlideIndex);
});

$('.slick-next').on('click', function () {
    var currentSlideIndex = $('.show-one-slide').slick('slickCurrentSlide');
    imageClickHandler(currentSlideIndex);
});
}