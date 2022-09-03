sessionStorage.setItem('preIndex','50');
window.onload=function(){
    var orderNo = 0;
    var orderTbl =  document.getElementById("tbl_order");
    var rows = orderTbl.rows ;
    for(var i = 1 ; i<rows.length-1 ; i=i+2){
        var tr = rows[i];
        //获取tr这一行的所有单元格
        var cells = tr.cells;
        var clickDetail = cells[6];
        //2.绑定text
        clickDetail.innerText= orderNo;
        orderNo = orderNo+1;
    }
    var vue = new Vue({
        el:"#order_manager_div",
        data:{
            orderItemList:{}
        },
        methods:{
            showDetail:function(event){
                //get parameter for method below
                var clickTd = event.currentTarget;//button
                var cell = clickTd.parentElement;//cell(td)
                var string = cell.parentElement.lastChild.firstChild.nodeValue;//row--lastTd--text--value

                //set related show area to inline
                var index= cell.parentElement.rowIndex+1;
                var orderTr =  document.getElementById("show"+index);

                //visibility=visible 会导致所有的循环体内相同位置的tr全部显示
                //而display="Inline"只会影响单独的位置 可能与二者和页面刷新顺序有关
                orderTr.style.display="Inline";


                //set previous area to none
                if(sessionStorage.getItem('preIndex') !== '50'){
                    var preOrderTr = document.getElementById("show"+sessionStorage.getItem('preIndex'));
                    preOrderTr.style.display="none";
                }

                sessionStorage.setItem('preIndex',index.toString());

                axios({
                    method:"POST",
                    url:"order.do",
                    params:{
                        operate:'jsOrderDetail',
                        orderNo:string
                    }
                })
                    .then(function (value) {
                        var orderItemList = value.data ;
                        vue.orderItemList = orderItemList ;
                    })
                    .catch(function (reason) {  });
            }
        },
        mounted:function(){
        }
    });
}
