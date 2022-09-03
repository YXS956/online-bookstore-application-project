window.onload=function(){
    var vue = new Vue({
        el:"#div_comment_list",
        data:{
            commentList:{},
            userRole: "1"
        },
        methods:{
            jsGetComment:function (){
                axios({
                    method:"POST",
                    url:"comment.do",
                    params:{
                        operate:'jsGetComment'
                    }
                })
                    .then(function (value) {
                        var commentList = value.data ;
                        vue.commentList = commentList ;
                    })
                    .catch(function (reason) {  });
            },
            sortByTime:function(bookId){
                axios({
                    method:"POST",
                    url:"comment.do",
                    params:{
                        operate:'sortByTime',
                        bookId:bookId
                    }
                })
                    .then(function (value) {
                        var commentList = value.data ;
                        vue.commentList = commentList ;
                    })
                    .catch(function (reason) {  });
            },
            sortByRank:function(bookId){
                axios({
                    method:"POST",
                    url:"comment.do",
                    params:{
                        operate:'sortByRank',
                        bookId:bookId
                    }
                })
                    .then(function (value) {
                        var commentList = value.data ;
                        vue.commentList = commentList;
                    })
                    .catch(function (reason) {  });
            },
            delReply:function(commentId){
                if(window.confirm("delete this comment？")) {
                axios({
                    method: "POST",
                    url: "comment.do",
                    params: {
                        operate: 'deleteComment',
                        commentId: commentId
                    }
                })
                    .then(function (value) {
                        vue.jsGetComment();
                    })
                    .catch(function (reason) {
                    });
            }},
            updateLikeNo:function(commentId,currNo){
                axios({
                    method: "POST",
                    url: "comment.do",
                    params: {
                        operate: 'updateCommentLikeNo',
                        commentId: commentId,
                        newLikeNum: currNo
                    }
                    })
                    .then(function (value) {
                        vue.jsGetComment();
                    })
                    .catch(function (reason) {

                    });
            },
        mounted:function(){
            this.jsGetComment();
        }
        }});
}

function addCart(bookId){
    window.location.href='cart.do?operate=addCart&bookId='+bookId;
}

function addECart(bookId){
    window.location.href='cart.do?operate=addECart&bookId='+bookId;
}

function showDelImg(imgId){
    var obj = document.getElementById(imgId) ;
    if(obj){
        obj.style.display='inline';
    }

}
function hiddenDelImg(imgId){
    var obj = document.getElementById(imgId) ;
    if(obj){
        obj.style.display='none';
    }
}
function delReply(replyId , topicId){
    if(window.confirm("Confirm to delete?")){
        window.location.href='reply.do?operate=delReply&replyId='+replyId+'&topicId='+topicId;
    }
}
function refresh(){
    // sessionStorage.setItem("refresh","Y");
    window.location.href='book.do?ope=refre&keyWord=';
    window.location.href='book.do';
}