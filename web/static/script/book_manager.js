window.onload=function(){
    var vue = new Vue({
        el:"#book_manager_div",
        data:{
            bookList:{}
        },
        methods:{
            getBook:function(){
                axios({
                    method:"POST",
                    url:"book.do",
                    params:{
                        operate:'getBook'
                    }
                })
                    .then(function (value) {
                        var bookList = value.data ;
                        vue.bookList = bookList ;
                    })
                    .catch(function (reason) {  });
            },
            searchBook:function(){
                axios({
                    method:"POST",
                    url:"book.do",
                    params:{
                        operate:'jsSearchBook',
                        keyword:document.getElementById("input1").value
                    }
                })
                    .then(function (value) {
                        var bookList = value.data ;
                        vue.bookList = bookList ;
                    })
                    .catch(function (reason) {  });
            },
            bookModify:function (bookId) {
                // this.$router.push({name: 'page.do', params : {operate : 'page', page: 'manager/book_edit', bookId : bookId}})
                window.location.href='page.do?operate=page&page=manager/book_edit&bookId='+bookId;
            },
            deleteBook:function (bookId) {
                axios({
                    method:"POST",
                    url:"book.do",
                    params:{
                        operate:'deleteBook',
                        bookId:bookId
                    }
                })
                    .then(function (value) {
                        vue.getBook();
                    })
                    .catch(function (reason) {  });
            }
        },
        mounted:function(){
            this.getBook() ;
        }
    });
}