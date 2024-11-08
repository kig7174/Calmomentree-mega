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