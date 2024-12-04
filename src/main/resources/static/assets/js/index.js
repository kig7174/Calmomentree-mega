// popup cookie
if (!utilHelper.getCookie("no-popup")) {
    document.querySelector(".popup").style.display = "block";
}

document.querySelector("#close-popup").addEventListener("click", (e) => {
    document.querySelector(".popup").style.display = "none";

    if (document.querySelector("#no-open").checked) {
        utilHelper.setCookie("no-popup", "Y", 60*60*24);
    }
});

// best seller swiper
let bestSeller = new Swiper(".best-seller", {
    slidesPerView: "auto",
    spaceBetween: 20,
    freemode: true,
    loop: true,
    speed: 1000,
    autoplay: {     
        delay: 3500,
        disableOnInteraction: true, 
    },
});

// partners list swiper/
let partnersList = new Swiper(".partners", {
    slidesPerView: "auto",
    spaceBetween: 20,
    freemode: true,
    loop: true,
    speed: 2000,
    autoplay: {     
        delay: 1000,
        disableOnInteraction: true, 
    },
});