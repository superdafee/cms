/**
 * 以下四个函数作用是：两个多选的select框，从左框中将选中的条目移到右框中、反之、全部右移和全部左移
 * @param leftSelect:左边的多选select控件的jquery对象
 * @param rightSelect:右边的多选select控件的jquery对象
 */
function move(leftSelect,rightSelect){
    $(leftSelect).find("option:selected").each(function(){
        $(this).appendTo($(rightSelect));
    });
}
function remove(leftSelect,rightSelect){
    $(rightSelect).find("option:selected").each(function(){
        $(this).appendTo($(leftSelect));
    });
}
function moveAll(leftSelect,rightSelect){
    $(leftSelect).find("option").appendTo($(rightSelect));
}
function removeAll(leftSelect,rightSelect){
    $(rightSelect).find("option").appendTo($(leftSelect));
}

/**
 * 该方法实现了总控的checkbox控制多个checkbox的全选和全部取消选中
 * @param topCheckBox :总控的checkbox的jquery对象
 * @param childCheckBoxName :子checkbox的名字
 */
function toggleCheckBox(topCheckBox,childCheckBoxName){
    var status=$(topCheckBox).prop("checked");

    if(status){
        $("input[name='"+childCheckBoxName+"']").prop("checked",status);
    }else{
        $("input[name='"+childCheckBoxName+"']").removeAttr("checked");
    }
}
/**
 * 时间转字符串
 */
function date2str(d){
    var ret=d.getFullYear()+"-"
    ret+=("00"+(d.getMonth()+1)).slice(-2)+"-"
    ret+=("00"+d.getDate()).slice(-2)+" "
    ret+=("00"+d.getHours()).slice(-2)+":"
    ret+=("00"+d.getMinutes()).slice(-2)+":"
    ret+=("00"+d.getSeconds()).slice(-2)+"."
    return ret+d.getMilliseconds()
}

//$(document).ready(function(){var doc=document,inputs=doc.getElementsByTagName('input'),supportPlaceholder='placeholder'in  doc.createElement('input'),placeholder=function(input){var text=input.getAttribute('placeholder'),defaultValue=input.defaultValue;if(defaultValue==''){input.value=text}input.onfocus=function(){if(input.value===text){this.value=''}};input.onblur=function(){if(input.value===''){this.value=text}}};if(!supportPlaceholder){for(var i=0,len=inputs.length;i<len;i++){var input=inputs[i],text=input.getAttribute('placeholder');if((input.type==='text' ) && text){placeholder(input)}}}});
//$.fn.extend({
//    /**
//     * 该方法为了解决不支持placeholder属性的浏览器下达到相似效果作用
//     * @param _color 文本框输入字体颜色,默认黑色
//     * @param _plaColor 文本框placeholder字体颜色，默认灰色#a3a3a3
//     */
//    inputTip:function(_color, _plaColor) {
//        _color = _color || "#000000";
//        _plaColor = _plaColor || "#a3a3a3";
//        function supportsInputPlaceholder() { // 判断浏览器是否支持html5的placeholder属性
//            var input = document.createElement('input');
//            return "placeholder" in input;
//        }
//
//        function showPassword(_bool, _passEle, _textEle) { // 密码框和文本框的转换
//            if (_bool) {
//                _passEle.show();
//                _textEle.hide();
//            } else {
//                _passEle.hide();
//                _textEle.show();
//            }
//        }
//
//        if (!supportsInputPlaceholder()) {
//            this.each(function() {
//                var thisEle = $(this);
//                var inputType = thisEle.attr("type");
//                var isPasswordInput = inputType == "password";
//                var isInputBox = inputType == "password" || inputType == "text";
//                if (isInputBox) { //如果是密码或文本输入框
//                    var isUserEnter = false; // 是否为用户输入内容,允许用户的输入和默认内容一样
//                    var placeholder = thisEle.attr("placeholder");
//
//                    if (isPasswordInput) { // 如果是密码输入框
//                        //原理：由于input标签的type属性不可以动态更改，所以要构造一个文本input替换整个密码input
//                        var textStr = "<input type='text' class='" + thisEle.attr("class") + "' style='" + (thisEle.attr("style") || "") + "' />";
//                        var textEle = $(textStr);
//                        textEle.css("color", _plaColor).val(placeholder).focus(
//                            function() {
//                                thisEle.focus();
//                            }).insertAfter(this);
//                        thisEle.hide();
//                    }
//                    thisEle.css("color", _plaColor).val("");//解决ie下刷新无法清空已输入input数据问题
//                    if (thisEle.val() == "") {
//                        thisEle.val(placeholder);
//                    }
//                    thisEle.focus(function() {
//                        if (thisEle.val() == placeholder && !isUserEnter) {
//                            thisEle.css("color", _color).val("");
//                            if (isPasswordInput) {
//                                showPassword(true, thisEle, textEle);
//                            }
//                        }
//                    });
//                    thisEle.blur(function() {
//                        if (thisEle.val() == "") {
//                            thisEle.css("color", _plaColor).val(placeholder);
//                            isUserEnter = false;
//                            if (isPasswordInput) {
//                                showPassword(false, thisEle, textEle);
//                            }
//                        }
//                    });
//                    thisEle.keyup(function() {
//                        if (thisEle.val() != placeholder) {
//                            isUserEnter = true;
//                        }
//                    });
//                }
//            });
//        }
//    }
//});
//确定删除对话框
function isDel(msg){
	if(confirm(msg)){ return true;}else return false;
}

function pageRequest(url, navi, page, sortType, searchParams) {
//    window.location.href="?navi=${param.navi}&page=1&sortType=${sortType}&${searchParams}";
    if (url != null) {
        window.location.href=url + "?page=" + page + "&sortType=" + sortType + "&" + searchParams + "&navi="+ navi;
    } else {
        window.location.href="?page=" + page + "&sortType=" + sortType + "&" + searchParams + "&navi="+ navi;
    }

}

function setPageAndRequest(navi, page, sortType, searchParams) {
    if ($("#pageNumber")) {
        $("#pageNumber").val(page)
    }
    pageRequest(null, navi, page, sortType, searchParams);
}
//验证textare最大数
function checkCharactorsCount(maxLength, areaField, numField){
	var existCharactors = $(areaField).val();
	var lineCharCount = 0;
	if (existCharactors.indexOf("\n") != -1) {
		lineCharCount = existCharactors.match(/[\n]/g).length;
	}
	//var existLength = existCharactors.length;
	var existLength = existCharactors.length + lineCharCount;
	if(existLength>maxLength){
		$(areaField).val(existCharactors.substring(0, maxLength-lineCharCount));
		existLength = maxLength;
	}
	$(numField).text(maxLength-existLength);
}
//js文件大小判断
function fileIsLegal(target){
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;     
    if (isIE && !target.files) {      
      var filePath = target.value;      
      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
      var file = fileSystem.GetFile (filePath);      
      fileSize = file.Size;     
    }else {     
      fileSize = target.files[0].size;      
     }    
     var size = fileSize / 1024;     
     if(size>1000){   
      return false;
     }   
	 return true;
}
//数字转字符串
function numberConversion(num){
    var result="";
    switch (num){
        case '0':
            result="零";
            break;
        case '1':
            result="一";
            break;
        case '2':
            result="二";
            break;
        case '3':
            result="三";
            break;
        case '4':
            result="四";
            break;
        case '5':
            result="五";
            break;
        case '6':
            result="六";
            break;
        case '7':
            result="七";
            break;
        case '8':
            result="八";
            break;
        case '9':
            result="九";
            break;

    }
    return result;
}
//自定义alert
//onHide，点击关闭的时候，调用相应的方法
function alertMsgTip(title, msg){
    $('#alertMsgTip').remove();
    var mtop = -240;
    if($(".ke-dialog").css("display") == "block"){
        mtop = -100;
    }
    var sdiv=$('<div class="modal hide fade" data-backdrop="static" id="alertMsgTip" style="width: 300px; margin-left: -160px;margin-top: '+mtop+'px">'+
        '<div id="main_title"><a class="close" data-dismiss="modal">×</a>'+title+'</div>'+
        '<div class="modal-body"> '+
        msg+
        '</div> <div class="modal-footer">'+
        '<input type="button" class="btn_com_b" data-dismiss="modal" value="关闭"/> '+
        '</div></div>');
    $("body").append($(sdiv)) ;
    $('#alertMsgTip').on("show", function(){ $('#alertMsgTip').css("z-index","811215");});

    $(".ke-dialog-icon-close").click(function(){$('#alertMsgTip').modal("hide");});

    $('#alertMsgTip').modal({backdrop:"static", show:true});
//    $('#uploadModal').on("hide",function(){ $('#alertMsgTip').modal("hide");});
}
//提交按钮禁止10秒
 function buttonDisabled(obj){
     obj.disabled = true;
     //暂时不用导致表单不能提交
     //setTimeout(function() { obj.disabled = false; }, 10000);
 }
function checkDateFormat(obj){
    var a = /^(\d{4})-(\d{2})-(\d{2})$/;
    if (!a.test(obj.value)) {
       obj.value="";
    }
}
//图片缩小设置
function picShrink(){
    $('.expr').each(function() {
        var ratio = 0.4;  // 缩放比例
        var width = $(this).width();    // 图片实际宽度
        var height = $(this).height();  // 图片实际高度
        $(this).css("width", width * ratio); // 设定等比例缩放后的宽度
        $(this).css("height", height * ratio);  // 设定等比例缩放后的高度
    });
}
//只适应于试题预览
function priviewOrSubmit(flag,ctx,from,submitBut){
    if(flag=="1"){
        $("#"+from).attr("action",ctx+"/kind/preView");
        $("#"+from).attr("onsubmit","window.open('','newWin','width=1000,height=600,toolbar=no,menubar=no,scrollbars=yes,resize=yes')");
        $("#"+from).attr("target","newWin");
        $("#"+from).submit();
    }else if(flag=="2"){
        $("#"+submitBut).attr("disabled","disabled");
        $("#"+from).attr("action",ctx+"/quiz/save");
        $("#"+from).removeAttr("onsubmit");
        $("#"+from).removeAttr("target")
        $("#"+from).submit();
    }
}


/**
 * 计算textarea剩余字数
 * 在页面初始化完成后的脚本中为textarea元素的keydown,keypress,keyup事件增加此方法
 * @param maxLength 最大字数
 * @param areaField textarea的jquery对象
 * @param numField 显示字数数字部分的jquery对象--eg.”还可以录入<span id='numfield'>150</span>个字“
 * @returns
 *
 * */
function checkCharactersCount(maxLength, areaField, numField){
    var existCharactors = $(areaField).val();
    var lineCharCount = 0;
    if (existCharactors.indexOf("\n") != -1) {
        lineCharCount = existCharactors.match(/[\n]/g).length;
    }
    //var existLength = existCharactors.length;
    var existLength = existCharactors.length + lineCharCount;
    if(existLength>maxLength){
        $(areaField).val(existCharactors.substring(0, maxLength-lineCharCount));
        existLength = maxLength;
    }
    $(numField).text(maxLength-existLength);
}