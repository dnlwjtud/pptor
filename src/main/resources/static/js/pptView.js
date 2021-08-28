function renderS1TemplateEle() {

    let s1 = document.querySelector('#S1');
    let s1h1Tags;
    let s1PTags;

    if ( s1.getElementsByTagName("h1").length != 0 ) {
        s1h1Tags = s1.getElementsByTagName("h1");
    }

    if ( s1.getElementsByTagName("p").length != 0 ) {
        s1PTags = s1.getElementsByTagName("p");
    }

    for ( var i = 0; i < s1h1Tags.length; i++ ) {
        var item = s1h1Tags.item(i);
        item.classList.add('text-landing');
    }

    for ( var i = 0; i < s1PTags.length; i++ ) {
        var item = s1PTags.item(i);
        item.classList.add('text-subtitle');
    }

}

function renderS2TemplateEle() {

    let s2 = document.querySelector('#S2');
    let s2DivTags;

    if ( s2.getElementsByTagName("div").length != 0 ) {
        s2DivTags = s1.getElementsByTagName("div");
    }

    for ( var i = 0; i < s2DivTags.length; i++ ) {
        var item = s2DivTags.item(i);
        item.classList.add('content-center');
    }

}

