$(function() {
    heartItems = document.getElementsByClassName('btn-wishlist1');
    for(i = 0;i < heartItems.length;i++) {
      heartValue = JSON.parse(heartItems[i].getAttribute('value'));
      if(heartValue == true) {
        heartItems[i].attr('class', 'btn-wishlist1-fill');
      } else {
        heartItems[i].attr('class', 'btn-wishlist1');
      }
    }
  });
  function onClickHeart(id) {
    heartValue = JSON.parse($('#'+id).attr('value'));
    heartValue = !heartValue;

    $('#'+id).attr('value', ''+heartValue);
    if(heartValue == true) {
      $('#'+id).attr('class', 'btn-wishlist1-fill');
    } else {
      $('#'+id).attr('class', 'btn-wishlist1');
    }
  }