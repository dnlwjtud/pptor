/*
function renderS1Code() {

    let s1 = document.getElementsByClassName('s1');

    for ( var i = 0; i < s1.length; i++ ) {

      var section = s1.item(i);

      //h1
      var h1Tags = section.getElementsByTagName('h1');

      for ( var j = 0; j < h1Tags.length; j++ ) {

        var h1Tag = h1Tags.item(j);

        h1Tag.classList.add('text-landing');

      }

      //p
      var pTags = section.getElementsByTagName('p');

      for ( var j = 0; j < pTags.length; j++ ) {

        var pTag = pTags.item(j);

        pTag.classList.add('text-subtitle');

      }


    }

}

function renderS2Code() {

    let s2 = document.getElementsByClassName('s2');

    // div
    for ( var i = 0; i < s1.length; i++ ) {

      var section = s1.item(i);

      var divTags = section.getElementsByTagName('div');

      for ( var j = 0; j < divTags.length; j++ ) {

        var divTag = divTags.item(j);

        divTag.classList.add('content-center');

      }

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

function init() {

    renderS1Code();
    renderS2Code();

}
*/