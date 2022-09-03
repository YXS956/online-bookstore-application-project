window.onload= function() {
    var url = location.search; //获取url中"?"符后的字串

    if (url.indexOf("?") !== -1) {
        var str = url.substr(1);
        var strs = str.split("&");

        var bookId = decodeURIComponent(strs[2].replace("bookId=",""));

        document.getElementById("idInput").value = bookId;

        if(bookId.valueOf() !== null) {
            document.getElementById("show1").innerText = "modify book";
        }else {
            document.getElementById("show1").innerText = "add new book";
        }
    }
}