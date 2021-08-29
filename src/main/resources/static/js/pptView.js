function renderS1TemplateEle() {

    let s1 = document.querySelector("#S1");
    let s1h1Tags = s1.getElementsByTagName("h1");
    let s1PTags;

    if (s1.getElementsByTagName("h1").length != 0) {
        s1h1Tags = s1.getElementsByTagName("h1");
    }

    if (s1.getElementsByTagName("p").length != 0) {

        s1PTags = s1.getElementsByTagName("p");
    }

    for (var i = 0; i < s1h1Tags.length; i++) {
        var item = s1h1Tags.item(i);
        item.classList.add("text-landing");
    }

    for (var i = 0; i < s1PTags.length; i++) {
        var item = s1PTags.item(i);
        item.classList.add("text-subtitle");
    }

}

function renderS2TemplateEle() {

    let s2 = document.querySelectorAll('#S2');
    let s2DivTags;

    if (s2.getElementsByTagName("div").length != 0) {
        s2DivTags = s2.getElementsByTagName("div");
    }

    for (var i = 0; i < s2DivTags.length; i++) {
        var item = s2DivTags.item(i);
        item.classList.add('content-center');
    }

}

function renderS3TemplateEle() {

    let s3 = document.querySelectorAll('#S3');
    let s3DivTags;

    if (s3.getElementsByTagName("div").length != 0) {
        s3DivTags = s3.getElementsByTagName("div");
    }

    for (var i = 0; i < s3DivTags.length; i++) {
        var item = s3DivTags.item(i);
        item.classList.add('wrap');
    }

}

function renderS4TemplateEle() {

    let s4 = document.querySelectorAll('#S4');
    let s4DivTags;

    if (s4.getElementsByTagName("div").length != 0) {
        s4DivTags = s4.getElementsByTagName("div");
    }

    for (var i = 0; i < s4DivTags.length; i++) {
        var item = s4DivTags.item(i);
        item.classList.add('wrap');
    }

}

function renderS5TemplateEle() {

    let s5 = document.querySelectorAll('#S5');
    let s5DivTags;
    let s5ImgTags;

    if (s5.getElementsByTagName("div").length != 0) {
        s5DivTags = s5.getElementsByTagName("div");
    }

    for (var i = 0; i < s5DivTags.length; i++) {
        var item = s5DivTags.item(i);
        item.classList.add('wrap');
    }

    if (s5.getElementsByTagName("img").length != 0) {
        s5ImgTags = s5.getElementsByTagName("img");
    }

    for (var i = 0; i < s5ImgTags.length; i++) {
        var item = s5ImgTags.item(i);
        item.classList.add('alignleft', 'size-50');
    }

}

function renderS6TemplateEle() {

    let s6 = document.querySelectorAll('#S6');
    let s6DivTags;
    let s6ImgTags;

    if (s6.getElementsByTagName("div").length != 0) {
        s6DivTags = s6.getElementsByTagName("div");
    }

    for (var i = 0; i < s3DivTags.length; i++) {
        var item = s3DivTags.item(i);
        item.classList.add('wrap');
    }

    if (s6.getElementsByTagName("img").length != 0) {
        s6ImgTags = s6.getElementsByTagName("img");
    }

    for (var i = 0; i < s6ImgTags.length; i++) {
        var item = s6ImgTags.item(i);
        item.classList.add('alignright', 'size-50');
    }

}