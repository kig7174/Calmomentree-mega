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
