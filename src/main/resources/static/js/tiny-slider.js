var slider = tns({
    container: '.my-slider',
    controls: false,
    items: 1,
    speed: 1500,
    autoplay: true,
    autoplayTimeout: 2000,
    autoplayText: [
        "",
        ""
    ],
    mouseDrag: true,
    swipeAngle: false,
    responsive: {
        640: {
            gutter: 20,
            items: 2
        },
        700: {
            gutter: 30,
            items: 3
        },
        900: {
            items: 4
        }
    }
});