function $(id){
    return document.getElementById(id);
}

function preRegist(){

    var unameReg = /[0-9a-zA-Z]{6,16}/;
    var unameTxt = $("unameTxt");
    var uname = unameTxt.value ;
    var unameSpan = $("unameSpan");
    if(!unameReg.test(uname)){
        unameSpan.style.visibility="visible";
        return false ;
    }else{
        unameSpan.style.visibility="hidden";
    }


    var pwdTxt = $("pwdTxt");
    var pwd = pwdTxt.value ;
    var pwdReg = /[\w]{8,}/; // /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/;
    var pwdSpan = $("pwdSpan");
    if(!pwdReg.test(pwd)){
        pwdSpan.style.visibility="visible";
        return false ;
    }else{
        pwdSpan.style.visibility="hidden";
    }


    var pwd2 = $("pwdTxt2").value ;
    var pwdSpan2 = $("pwdSpan2") ;
    if(pwd2!=pwd){
        pwdSpan2.style.visibility="visible";
        return false ;
    }else{
        pwdSpan2.style.visibility="hidden";
    }


    var email = $("emailTxt").value ;
    var emailSpan = $("emailSpan");
    var emailReg = /^[a-zA-Z0-9_\.-]+@([a-zA-Z0-9-]+[\.]{1})+[a-zA-Z]+$/;
    if(!emailReg.test(email)){
        emailSpan.style.visibility="visible";
        return false ;
    }else{
        emailSpan.style.visibility="hidden";
    }

    return true ;
}


// An important object called XMLHttpRequest is needed to send asynchronous request
var xmlHttpRequest ;

function createXMLHttpRequest(){
    if(window.XMLHttpRequest){
        //for browser with DOM2 standard to create like Edge,firebox
        xmlHttpRequest = new XMLHttpRequest() ;
    }else if(window.ActiveXObject){
        //for browser not with DOM2 standard to create like IE
        try{
            xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        }catch (e) {
            xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP")
        }
    }
}

function ckUname(uname){
    if(uname.replace(/\s*/g,"") !== ''){
    createXMLHttpRequest();
    var url = "user.do?operate=ckUname&uname="+uname ;
    xmlHttpRequest.open("GET",url,true);
    //set call-back function
    xmlHttpRequest.onreadystatechange = ckUnameCB ;
    //send request
    xmlHttpRequest.send();
    }
    else{
        alert("please input username!");
    }
}

function ckUnameCB(){
    if(xmlHttpRequest.readyState===4 && xmlHttpRequest.status===200){
        var responseText = xmlHttpRequest.responseText ;
        if(responseText==="{'uname':'1'}"){
            alert("username already exists");
        }else{
            alert("username is available");
        }
    }
}




