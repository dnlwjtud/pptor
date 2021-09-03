class RenderFactory {

  rendering() {

    for ( let i = 0; i < 7; i++) {
      let code = 'S' + i;
      this.renderingByCode(code);
    }

    this.wrapping();
  }

  wrapping() {
    $('section').wrapInner('<div class="wrap"></div>');
  }

  renderOption1(code, tagName, className) {

    let sections = document.getElementsByClassName(code);

    for ( let i = 0; i < sections.length; i ++ ) {

      let section = sections.item(i);

      let tags = section.getElementsByTagName(tagName);

      for ( let j = 0; j < tags.length; j++ ) {

        let tag = tags.item(j);
        tag.classList.add(className);

      }

    }

  }

  renderOption2(code, findClassName, modifyClassName) {

    let sections = document.getElementsByClassName(code);

    for ( let i = 0; i < sections.length; i ++ ) {

      let section = sections.item(i);

      let classes = section.getElementsByClassName(findClassName);

      for ( let j = 0; j < classes.length; j++ ) {

        let findClass = classes.item(j);
        findClass.classList.add(modifyClassName);

      }

    }

  }

  renderingByCode( code ) {

    switch ( code ) {
      case 'S1' :
        this.renderOption1(code, 'h1', 'text-landing');
        this.renderOption1(code, 'p', 'text-subtitle');
        $('.S1').wrapInner('<div class="content-center"></div>');
        break;
      case 'S2' :
        $('.S2').wrapInner('<div class="content-center"></div>');
        break;
      case 'S3' :

        this.renderOption1(code,'div','column');

        $('.S3').each(function(index, node) {
          const $node = $(node);
          $node.children('.column').wrapAll('<div class="grid sm"></div>');


        });

        break;
      case 'S4' :

        this.renderOption1(code,'div','column');

        $('.S4').each(function(index, node) {
          const $node = $(node);
          $node.children('.column').wrapAll('<div class="grid ms"></div>');
        });

        break;
      case 'S5' :
        this.renderOption1(code, 'img', 'alignleft');
        this.renderOption1(code, 'img', 'size-50');
        break;
      case 'S6' :
        this.renderOption1(code, 'img', 'alignright');
        this.renderOption1(code, 'img', 'size-50');
        break;
    }

  }

}




