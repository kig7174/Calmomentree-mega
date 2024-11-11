// index page margin remove
(function() {
    document.querySelector(".container").style.margin = "0";
})();

// 브라우저 스크롤 이벤트
window.addEventListener("scroll", (e) => {
    const scrollY = window.scrollY;  
    
    // 메뉴 및 배너 가리기 / 보이기
    if(scrollY > 0){
        document.querySelector(".banner").classList.add("on");
        document.querySelector(".menu").classList.add("on");
    } else {
        document.querySelector(".banner").classList.remove("on");
        document.querySelector(".menu").classList.remove("on");
    }
});

let lastScroll = 0;
const topBtn = document.querySelector(".top-btn-box");

// top 버튼 가리기 / 보이기
window.addEventListener("scroll", (e) => {
    const scrollY = window.scrollY;
    const scrollDown = scrollY > lastScroll;

    if (scrollDown) {
        topBtn.classList.add("scroll");
    } else {
        topBtn.classList.remove("scroll");
    }

    lastScroll = scrollY;
})

topBtn.addEventListener("click", (e) => {
    e.preventDefault();

    window.scrollTo({ top: 0, behavior: 'smooth'});
});

// 브라우저 창 중간에서 새로고침 시 메뉴가 브라우저 상단 끝으로 이동하지 않음
// 아래 코드 추가
const scrollY = window.scrollY;

if(scrollY > 0){
    document.querySelector(".banner").classList.add("on");
    document.querySelector(".menu").classList.add("on");
} else {
    document.querySelector(".banner").classList.remove("on");
    document.querySelector(".menu").classList.remove("on");
}

// 카테고리 마우스 오버 이벤트
document.querySelector(".category-inner").addEventListener("mouseover", (e) => {
    e.preventDefault();
    const subCategory = document.querySelectorAll(".sub-category");
    subCategory.forEach((v, i) => {
        v.classList.add("on");
    });
    document.querySelector(".sub-category-box").classList.add("on");
});

document.querySelector(".category-inner").addEventListener("mouseout", (e) => {
    e.preventDefault();
    const subCategory = document.querySelectorAll(".sub-category");
    subCategory.forEach((v, i) => {
        v.classList.remove("on");
    });
    document.querySelector(".sub-category-box").classList.remove("on");
});


/** 검색 창 이벤트 */
const searchBox = document.querySelector("#search-popup");
const searchBg = document.querySelector(".search-background");
const searchHeight = document.body.offsetHeight;

// 검색 창 동적 높이 (페이지 각각의 body높이로 설정)
(function() {
    searchBg.style.height = searchHeight + "px";
})();

// 검색 창 열기
document.querySelector("#search-on").addEventListener("click", (e) => {
    e.preventDefault();
    
    searchBox.classList.add("on");
    searchBg.classList.add("on");
});

// 검색 창 닫기 (검색 박스 외에 외부 영역 클릭 시 닫힘)
window.addEventListener("click", (e) => {
    if (e.target.contains(searchBox)) {
        searchBox.classList.remove("on");
        searchBg.classList.remove("on");
    }
});