//댓글 삭제

$(document).ready(function(){
    $('.trashbtn').click(function(){
        $(this).closest('.reviewlist').remove();
    });
  });
  
  
  //리뷰 더 보기
  //댓글 더 보기 누르면 댓글 전체 나옴
  
  $(function () {
    isHidden = false;
    $('.reviewlist').slice(0, 6).show(); // 첨에 3개 보여줌
    if ($('.reviewlist:hidden').length == 0)  // 댓글이 없을때
    {
        $('#seeMore').attr('disabled',true);
        document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;">댓글 없음</span>';
    }
    $('#seeMore').click(function (e) {  //댓글더보기 클릭시 
        if (isHidden == true)
        {
            document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;">댓글 더 보기</span>';
            $('.reviewlist').slice(6, $(".reviewlist").length).hide();
        } else
        {
            document.getElementById('seeMore').innerHTML = '<i class="ci-reload me-2" style="color: #fc6c93;"></i><span style="color: #fc6c93;"> 댓글 감추기 </span>';
            $('.reviewlist').show();
        }
        isHidden = !isHidden;
    });
  });