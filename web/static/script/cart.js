window.onload=function(){
    var vue = new Vue({
        el:"#cart_div",
        data:{
            cart:{}
        },
        methods:{
            getCart:function(){
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'cartInfo'
                    }
                })
                    .then(function (value) {
                        var cart = value.data ;
                        vue.cart = cart ;
                    })
                    .catch(function (reason) {  });
            },
            editCart:function(cartItemId , buyCount){
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'editCart',
                        cartItemId:cartItemId,
                        buyCount:buyCount
                    }
                })
                    .then(function (value) { <!--在更改完之后，再进行一次查询-->
                        vue.getCart();
                    })
                    .catch(function (reason) {  });
            },
            deleteCart:function (cartItemId) {
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'deleteCart',
                        cartItemId:cartItemId
                    }
                })
                    .then(function (value) {
                        vue.getCart();
                    })
                    .catch(function (reason) {  });
            },
            clearCart:function () {
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'clearCart'
                    }
                })
                    .then(function (value) {
                        vue.getCart();
                    })
                    .catch(function (reason) {  });
            }
        },
        mounted:function(){
            this.getCart() ;
        }
    });
}