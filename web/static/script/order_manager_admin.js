window.onload=function(){
    //vue的v-for可能在document.getelementByID的后面，与th:each不一样，不会扩展表格行数
    var vue = new Vue({
        el:"#order_manager_admin_div",
        data:{
            orderList:{},
            orderItemList:{}
        },
        methods:{
            getOrder:function(){
                axios({
                    method:"POST",
                    url:"order.do",
                    params:{
                        operate:'jsOrderList'
                    }
                })
                    .then(function (value) {
                        var orderList = value.data ;
                        vue.orderList= orderList ;
                    })
                    .catch(function (reason) {  });
            },
            searchOrder:function(){
                axios({
                    method:"POST",
                    url:"order.do",
                    params:{
                        operate:'jsSearchOrder',
                        keyword:document.getElementById("input1").value
                    }
                })
                    .then(function (value) {
                        var orderList = value.data ;
                        vue.orderList= orderList ;
                    })
                    .catch(function (reason) {  });
            },
            deleteOrder:function (orderId) {
                axios({
                    method:"POST",
                    url:"order.do",
                    params:{
                        operate:'deleteOrder',
                        orderId:orderId
                    }
                })
                    .then(function (value) {
                        vue.getOrder();
                    })
                    .catch(function (reason) {  });
            }
        },
        // showDetail:function(event){
        //     //get parameter for method below
        //     var clickTd = event.currentTarget;//button
        //     var cell = clickTd.parentElement;//cell(td)
        //     var string = cell.parentElement.lastChild.firstChild.nodeValue;//row--lastTd--text--value
        //
        //     //set related show area to inline
        //     var index= cell.parentElement.rowIndex+1;
        //     var orderTr =  document.getElementById("show"+index);
        //
        //     //visibility=visible 会导致所有的循环体内相同位置的tr全部显示
        //     //而display="Inline"只会影响单独的位置 可能与二者和页面刷新顺序有关
        //     orderTr.style.display="Inline";
        //
        //
        //     //set previous area to none
        //     if(sessionStorage.getItem('preIndex') !== '200'){
        //         var preOrderTr = document.getElementById("show"+sessionStorage.getItem('preIndex'));
        //         preOrderTr.style.display="none";
        //     }
        //
        //     sessionStorage.setItem('preIndex',index.toString());
        //
        //     axios({
        //         method:"POST",
        //         url:"order.do",
        //         params:{
        //             operate:'jsOrderDetail',
        //             orderNo:string
        //         }
        //     })
        //         .then(function (value) {
        //             var orderItemList = value.data ;
        //             vue.orderItemList = orderItemList ;
        //         })
        //         .catch(function (reason) {  });
        // },
        mounted:function(){
            this.getOrder() ;
        }
    });
}
