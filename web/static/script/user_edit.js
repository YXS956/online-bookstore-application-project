window.onload= function() {
    var url = location.search; //获取url中"?"符后的字串

    if (url.indexOf("?") !== -1) {
        var str = url.substr(1);
        var strs = str.split("&");

        var id = decodeURIComponent(strs[2].replace("id=",""));
        document.getElementById("idInput").value = id;
    }
}