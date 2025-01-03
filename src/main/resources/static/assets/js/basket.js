const formData = new FormData();

// ==================장바구니 max-height 조절 ========================
document.querySelector(".prodBox-title").addEventListener("click", (e) => {
  const current = e.currentTarget;
  const parent = current.closest(".prodBox");
  const content = parent.querySelector(".prodBox-info");
  const select = document.querySelector("#agree-content-btn");

  select.classList.toggle("select");

  content.classList.toggle("active");

  if (!content.classList.contains("active")) {
    content.style.maxHeight = null;
  } else {
    content.style.maxHeight = content.scrollHeight + "px";
  }
});

// ============ '+' 버튼 클릭시 수량 증가 ====================
document.querySelectorAll(".plus").forEach((v, i) => {
  v.addEventListener("click", (e) => {
    e.preventDefault();

    const current = e.currentTarget;
    const parent = current.closest(".qtyBox");
    const qty = parent.querySelector(".qty");

    qty.value++;

    // 이벤트 강제 전송????
    const eve = new Event("change");
    qty.dispatchEvent(eve);
  });
});

// =============== '-' 버튼 클릭시 수량 감소 =======================
document.querySelectorAll(".minus").forEach((v, i) => {
  v.addEventListener("click", (e) => {
    e.preventDefault();

    const current = e.currentTarget;
    const parent = current.closest(".qtyBox");
    const qty = parent.querySelector(".qty");

    if (qty.value > 1) {
      qty.value--;

      // 이벤트 강제 전송????
      const eve = new Event("change");
      qty.dispatchEvent(eve);
    }
  });
});

// 전체선택 버튼 클릭
document.querySelector("#checkAll").addEventListener("click", (e) => {
  document.querySelectorAll(".prodCheck").forEach((v, i) => {
    v.checked = true;
  });
});

// =================== 선택삭제 버튼 클릭 ========================= 
document.querySelector("#checkDelete").addEventListener("click", async (e) => {
  const checkedChks = document.querySelectorAll(".prodCheck:checked");

  if (checkedChks.length == 0) {
    alert("선택된 상품이 없습니다.");
    return;
  } else {
    
    if (confirm("선택하신 상품을 삭제하시겠습니까?")) {
      let data = null;
      
      checkedChks.forEach((v, i) => {
        const parent = v.closest(".listTable");
        // console.log("삭제할상품박스: " + parent);
        const basketId = parent.querySelector("#basket_id").value;
        // console.log("삭제할 장바구니 번호: " + basketId);
        
        let basketIdNum = parseInt(basketId);
        formData.append("basketIdTmp", basketIdNum);
        
      });
      data = await axiosHelper.delete(basketListDeleteLink, formData);
    
      if (data) {
        alert("삭제되었습니다.");
        window.location.reload();
      }
    }
  }
});

// ================ 개별 상품 삭제 버튼 ====================
document.querySelectorAll(".cancel").forEach((v, i) => {
  v.addEventListener("click", async (e) => {
    e.preventDefault();

   const current = e.currentTarget.closest(".listTable");
    const basketId = current.querySelector("#basket_id").value;
    
    formData.append('basketIdTmp',basketId);

    if (confirm("선택하신 상품을 삭제하시겠습니까?")) {
      const data = await axiosHelper.delete(basketDeleteLink, formData);

      if (data) {
        alert("삭제 되었습니다.");
        window.location = basketList;
      }
    }
  });
});
