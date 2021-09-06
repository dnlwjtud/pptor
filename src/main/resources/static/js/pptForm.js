/* PPT 작성폼 관련 */
function getUriParams(uri) {
  uri = uri.trim();
  uri = uri.replaceAll('&amp;', '&');
  if ( uri.indexOf('#') !== -1 ) {
    let pos = uri.indexOf('#');
    uri = uri.substr(0, pos);
  }

  let params = {};

  uri.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
  return params;
}

function youtubePlugin() {
  const toHTMLRenderers = {
    youtube(node) {
      const html = renderYoutube(node.literal);

      return [
        { type: 'openTag', tagName: 'div', outerNewLine: true },
        { type: 'html', content: html },
        { type: 'closeTag', tagName: 'div', outerNewLine: true }
      ];
    }
  }

  function renderYoutube(uri) {
    uri = uri.replace('https://www.youtube.com/watch?v=', '');
    uri = uri.replace('http://www.youtube.com/watch?v=', '');
    uri = uri.replace('www.youtube.com/watch?v=', '');
    uri = uri.replace('youtube.com/watch?v=', '');
    uri = uri.replace('https://youtu.be/', '');
    uri = uri.replace('http://youtu.be/', '');
    uri = uri.replace('youtu.be/', '');

    let uriParams = getUriParams(uri);

    let width = '100%';
    let height = '100%';

    let maxWidth = 500;

    if ( !uriParams['max-width'] && uriParams['ratio'] == '9/16' ) {
      uriParams['max-width'] = 300;
    }

    if ( uriParams['max-width'] ) {
      maxWidth = uriParams['max-width'];
    }

    let ratio = '16/9';

    if ( uriParams['ratio'] ) {
      ratio = uriParams['ratio'];
    }

    let marginLeft = 'auto';

    if ( uriParams['margin-left'] ) {
      marginLeft = uriParams['margin-left'];
    }

    let marginRight = 'auto';

    if ( uriParams['margin-right'] ) {
      marginRight = uriParams['margin-right'];
    }

    let youtubeId = uri;

    if ( youtubeId.indexOf('?') !== -1 ) {
      let pos = uri.indexOf('?');
      youtubeId = youtubeId.substr(0, pos);
    }

    return '<div style="max-width:' + maxWidth + 'px; margin-left:' + marginLeft + '; margin-right:' + marginRight + ';" class="ratio-' + ratio + ' relative"><iframe class="absolute top-0 left-0 w-full" width="' + width + '" height="' + height + '" src="https://www.youtube.com/embed/' + youtubeId + '" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>';
  }
  // 유튜브 플러그인 끝

  return { toHTMLRenderers }
}

const ToastEditor__chartOptions = {
  minWidth: 100,
  maxWidth: 600,
  minHeight: 100,
  maxHeight: 300
};

function ToastEditor__init() {
  $('.toast-ui-editor').each(function(index, node) {
    const $node = $(node);
    const $initialValueEl = $node.find(' > script');
    const initialValue = $initialValueEl.length == 0 ? '' : $initialValueEl.html().trim();

    const editor = new toastui.Editor({
      el: node,
      previewStyle: 'none',
      initialValue: initialValue,
      height:'600px',
      plugins: [
        [toastui.Editor.plugin.chart, ToastEditor__chartOptions],
        [toastui.Editor.plugin.codeSyntaxHighlight, {highlighter:Prism}],
        toastui.Editor.plugin.colorSyntax,
        toastui.Editor.plugin.tableMergedCell,
        youtubePlugin
      ],
      customHTMLSanitizer: html => {
        return DOMPurify.sanitize(html, { ADD_TAGS: ["iframe"], ADD_ATTR: ['width', 'height', 'allow', 'allowfullscreen', 'frameborder', 'scrolling', 'style', 'title', 'loading', 'allowtransparency'] }) || ''
      }
    });

    $node.data('data-toast-editor', editor);
  });
}

$(function() {
  ToastEditor__init();
});