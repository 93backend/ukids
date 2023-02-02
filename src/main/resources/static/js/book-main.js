$(function() {
    $(".book-search-filter").on("click", function() {
        var searchType = $(this).attr("value");
        $("#searchType").attr("value", searchType);
    });
});
function submitCheck() {
    var code = $("#searchType").val();
    if(code == "none") {
        alert("검색 필터를 선택하세요");
        return false;
    }
    return true
};