//console.log("mobileSideBar.js 로딩");

function MobileTopBar__init() {
  $('.btn-show-mobile-side-bar').click(function() {
    MobileSideBar__show();
  });
}

function MobileSideBar__init() {
  $('.btn-close-mobile-side-bar').click(function() {
    MobileSideBar__hide();
  })
}

function MobileSideBar__show() {
  $('.mobile-side-bar').addClass('active');
  $('html').addClass('mobile-side-bar-actived');
}

function MobileSideBar__hide() {
  $('.mobile-side-bar').removeClass('active');
  $('html').removeClass('mobile-side-bar-actived');
}

MobileTopBar__init();
MobileSideBar__init();