$(function() {
    compareItems = document.getElementsByClassName('button7');
    for(i = 0; i < compareItems.length; i++) {
      compareValue = JSON.parse(compareItems[i].getAttribute('value'));
      if(compareValue == true) {
        compareItems[i].attr('class', 'button7-fill');
      } else {
        compareItems[i].attr('class', 'button7');
      }
    }
  });
  function onClickCompare(id) {
    compareValue = JSON.parse($('#'+id).attr('value'));
    compareValue = !compareValue;

    $('#'+id).attr('value', ''+compareValue);
    if(compareValue == true) {
      $('#'+id).attr('class', 'button7-fill');
    } else {
      $('#'+id).attr('class', 'button7');
    }
  }