// 주문정보에 있는 주소검색
document.querySelector("#post-btn").addEventListener("click", (e) => {
  e.preventDefault();

  new daum.Postcode({
    oncomplete: function (data) {
      document.querySelector("#postcode").value = data.zonecode;
      document.querySelector("#addr1").value = data.roadAddress;
    },
  }).open();
});

// 배송지에 있는 주소검색
document.querySelector("#post-btn2").addEventListener("click", (e) => {
  e.preventDefault();

  new daum.Postcode({
    oncomplete: function (data) {
      document.querySelector("#postcode2").value = data.zonecode;
      document.querySelector("#addr3").value = data.roadAddress;
    },
  }).open();
});

// 아코디언 ...?? 
document.querySelectorAll(".info-title").forEach((v, i) => {
  v.addEventListener("click", (e) => {
    const current = e.currentTarget;
    const parent = current.closest(".collapse");
    const infoBox = parent.querySelector(".info-box");
    
    infoBox.classList.toggle("selected");

    if (!infoBox.classList.contains("selected")) {
      infoBox.style.maxHeight = null;
    } else {
      infoBox.style.maxHeight = infoBox.scrollHeight + "px";
    }
  });
});

