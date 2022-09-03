window.onbeforeunload = function (){
    //This fucntion is to keep focus at same place after every refresh operation
    var scrollPos;
    if (typeof window.pageYOffset != 'undefined') {
        scrollPos = window.pageYOffset;
    }
    else if (typeof document.compatMode != 'undefined' &&
        document.compatMode != 'BackCompat') {
        scrollPos = document.documentElement.scrollTop;
    }
    else if (typeof document.body != 'undefined') {
        scrollPos = document.body.scrollTop;
    }
    document.cookie="scrollTop="+scrollPos;
}

window.onload = function ()
{
    if(document.cookie.match(/scrollTop=([^;]+)(;|$)/)!=null){
        var arr=document.cookie.match(/scrollTop=([^;]+)(;|$)/);
        document.documentElement.scrollTop=parseInt(arr[1]);
        document.body.scrollTop=parseInt(arr[1]);
    }

    var vue = new Vue({
        el:"#random_book_div",
        data:{
            randomBookList:{}
        },
        methods:{
            getRandomBookList:function(){
                axios({
                    method:"POST",
                    url:"book.do",
                    params:{
                        operate:'getRandomBookList'
                    }
                })
                    .then(function (value) {
                        var randomBookList = value.data ;
                        vue.randomBookList = randomBookList ;
                    })
                    .catch(function (reason) {  });
            },
            updateHistory:function(bookId){
                axios({
                    method:"POST",
                    url:"book.do",
                    params:{
                        operate:'updateHistory',
                        bookId: bookId
                    }
                })
                    .then(function (value) {
                        vue2.getHistoryBookList() ;
                    })
                    .catch(function (reason) {  });
            }
        },
        mounted:function(){
            this.getRandomBookList() ;
        }
    });

    var vue2 = new Vue({
        el:"#history_book_div",
        data:{
            historyBookList:{}
        },
        methods:{
            getHistoryBookList:function(){
                axios({
                    method:"POST",
                    url:"book.do",
                    params:{
                        operate:'getHistoryBookList'
                    }
                })
                    .then(function (value) {
                        var historyBookList = value.data ;
                        vue2.historyBookList = historyBookList ;
                    })
                    .catch(function (reason) {  });
            },
            updateHistory:function(bookId){
                axios({
                    method:"POST",
                    url:"book.do",
                    params:{
                        operate:'updateHistory',
                        bookId: bookId
                    }
                })
                    .then(function (value) {
                        vue2.getHistoryBookList() ;
                    })
                    .catch(function (reason) {  });
            }
        },
        mounted:function(){
            this.getHistoryBookList() ;

            // how to bind function of js to function in method of vue
            // window.updatehistory = this.updateHistory();
        }
    });
}

function addCart(bookId){
    window.location.href='cart.do?operate=addCart&bookId='+bookId;
}

function backLogin(){
    window.location.href='page.do?operate=page&page=user/login';
}

// function refresh(){
//     // sessionStorage.setItem("refresh","Y");
//     window.location.href='book.do?ope=refre&keyWord=';
// }

function pageChange(pageNo){
    window.location.href='book.do?pageNo='+pageNo;
}

function searchKey(){
    window.location.href='book.do?ope=keySearch&keyWord='+document.getElementById('input1').value;
}

function pageJump(maxJumpNo){
    var JumpNo = document.getElementById('input2').value;
    if(Number(JumpNo) < 1){
        alert('page number is too small!');
    }else if(Number(JumpNo) > maxJumpNo) {
        alert('page number is too large!');
    }else{
        window.location.href='book.do?pageNo='+JumpNo;
    }
}

function searchPrice(){
    var middle = 0.0;
    var max = parseFloat(document.getElementById('max').value);
    var min = parseFloat(document.getElementById('min').value);
    if(max == null || max === '') max = 500;
    if(min == null || min === '') min = 1;
    if(Number.isFinite(min) === false || Number.isFinite(max) === false){
        alert("Please input correct prices!");
    }
    else{
        if(max < min){
            middle = max;
            max = min;
            min = middle;
        }
        window.location.href='book.do?ope=priceSearch&maxPrice='+max+'&minPrice='+min;
    }
}





