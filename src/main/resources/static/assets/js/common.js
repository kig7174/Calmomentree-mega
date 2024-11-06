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