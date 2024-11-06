window.addEventListener("scroll", (e) => {
    const scrollTop = window.scrollY;

    if(scrollTop > 0){
        document.querySelector(".banner").classList.add("on");
        document.querySelector(".menu").classList.add("on");
    } else {
        document.querySelector(".banner").classList.remove("on");
        document.querySelector(".menu").classList.remove("on");
    }
});

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