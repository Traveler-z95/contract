/**
 * 设置pager初始信息
 * @type {{page: number, start: number, limit: number}}
 */
var pager = {page:1,start:0,limit:10};

/**
 * 初始化数据
 */
$(function() {
    $("#contract-manage-li").addClass("active");
    $("#contract-list-li").addClass("active");
    var page = $("#current-page").val();
    if (page == null || page == 0) {
        page = 1;
    }
    pager.page = page;
    $.ajax({
        url: '/admin/contract/initPage',
        data: pager,
        success: function (data) {
            pager = data;
            $("#total-num").text(data.totalCount);
            $("#total-page").text(data.totalPageNum);
            $("#current-page").text(data.page);
            //初始化分页    update by  由于插件在没有数据的时候会报错，所以添加一层判断
            if (pager.totalCount > 0 ) {
                $.jqPaginator('#pagination', {
                    totalPages: data.totalPageNum,
                    visiblePages: 5,
                    currentPage: data.page,
                    prev: '<li class="prev"><a href="javascript:;">Previous</a></li>',
                    next: '<li class="next"><a href="javascript:;">Next</a></li>',
                    page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
                    onPageChange: function (num, type) {
                        // 加载管理员列表
                        $("#current-page").text(num);
                        pager.page = num;
                        loadContractList();
                    }
                });
            }else {
                loadContractList();
            }
            $(".chosen-select").chosen({
                max_selected_options: 5,
                no_results_text: "没有找到",
                allow_single_deselect: true
            });
            $(".chosen-select").trigger("liszt:updated");
        }
    });

});

/**
 * 跳转分页
 * @param page
 */
function toPage(page){
    $("#page").val(page);
    loadContractList();
}

/**
 * 加载文章列表
 */
function loadContractList(){

    var contractSignYear = $("#contractSignYear").val();
    var itemCoding = $("#itemCoding").val();
    var salePerson = $("#salePerson").val();

    // 查询列表
    $.ajax({
        url : '/admin/contract/loadContract',
        data : 'totalCount='+pager.totalCount+'&page='+pager.page+"&contractSignYear="+contractSignYear+"&itemCoding="+itemCoding+"&salePerson="+salePerson,
        success  : function(data) {
            $("#dataList").html(data);
        }
    });

}

/**
 * 搜索
 */
$("#contract-search").on('click',function () {
    loadContractList();
});

/**
 * 更新
 */
$("#dataList").on('click','.contract-update',function () {
    var id = $(this).parent().data("id");
    $.ajax({
        url : '/admin/contract/searchContract?contractId='+id,
        success : function (data) {
            $("#updateContractContent").html(data);
            $('#contract-updates').modal('show');
            $('#contract-updates').addClass('animated');
            $('#contract-updates').addClass('flipInY');
            $(".chosen-select").chosen({
                max_selected_options: 5,
                no_results_text: "没有找到",
                allow_single_deselect: true
            });
            $(".chosen-select").trigger("liszt:updated");
        }
    });

})
function updates() {
    var id = $("#contractId").val();
    var contractSignYear = $("#contractSignYear").val();
    var itemCoding = $("#itemCoding").val();
    var salePerson = $("#salePerson").val();
    var projectName = $("#projectName").val();
    var contractAmount = $("#contractAmount").val();
    var invoiceAmount2015 = $("#invoiceAmount2015").val();
    var recoveredAmount2015 = $("#recoveredAmount2015").val();
    var invoiceAmount2016 = $("#invoiceAmount2016").val();
    var recoveredAmountDate2016 = $("#recoveredAmountDate2016").val();
    var recoveredAmount2016 = $("#recoveredAmount2016").val();
    var invoiceAmount2017 = $("#invoiceAmount2017").val();
    var recoveredAmountDate2017 = $("#recoveredAmountDate2017").val();
    var recoveredAmount2017 = $("#recoveredAmount2017").val();
    var invoiceAmountDate2018 = $("#invoiceAmountDate2018").val();
    var invoiceAmount2018 = $("#invoiceAmount2018").val();
    var recoveredAmountDate2018 = $("#recoveredAmountDate2018").val();
    var recoveredAmount2018 = $("#recoveredAmount2018").val();
    var due = $("#due").val();
    var invoiceAmount = $("#invoiceAmount").val();
    var unbilledAmount = $("#unbilledAmount").val();
    var receivableTotal = $("#receivableTotal").val();


    // 保存文章
    $.ajax({
        type : "POST",
        url :  '/admin/contract/update',
        data : "id=" + id +"&contractSignYear=" + contractSignYear
            + "&itemCoding=" + itemCoding + "&salePerson=" + salePerson

            + "&projectName=" + projectName
            + "&contractAmount=" + contractAmount + "&contractAmount" + contractAmount + "&invoiceAmount2015=" + invoiceAmount2015
            + "&recoveredAmount2015=" + recoveredAmount2015 + "&invoiceAmount2016=" + invoiceAmount2016 + "&recoveredAmountDate2016=" + recoveredAmountDate2016
            + "&recoveredAmount2016=" + recoveredAmount2016 + "&invoiceAmount2017=" + invoiceAmount2017 + "&recoveredAmountDate2017=" + recoveredAmountDate2017
            + "&recoveredAmount2017=" + recoveredAmount2017 + "&invoiceAmountDate2018=" + invoiceAmountDate2018
            + "&invoiceAmount2018=" + invoiceAmount2018 + "&recoveredAmountDate2018=" + recoveredAmountDate2018 + "&recoveredAmount2018=" + recoveredAmount2018
            + "&due=" + due + "&invoiceAmount=" + invoiceAmount + "&unbilledAmount=" + unbilledAmount + "&receivableTotal=" + receivableTotal,
        success  : function(data) {
            if(data.resultCode != 'success'){
                autoCloseconsole.log(data.errorInfo,1000);
                closeEditWindow();
                return false;
            }else{
                new $.flavr({
                    content: '修改文章成功!',

                    buttons: {
                        success: {
                            text: '返回', style: 'danger', action: function () {
                                window.location.href = "/admin/contract/list";
                            }
                        }
                    }
                });
                // 调到列表页
            }
        }
    });
}

//关闭编辑窗口
function closeEditWindow(){
    $('#updateContractContent').modal('hide');
}

/**
 * 删除
 */
$("#dataList").on('click','.contract-delete',function () {
    var id = $(this).parent().data("id");
    new $.flavr({
        content: '您确定要删除吗?',

        buttons: {
            primary: {
                text: '确定', style: 'primary', action: function () {
                    $.ajax({
                        url : '/admin/contract/delete/'+id,
                        method: "GET",
                        success  : function(data) {
                            if(data.resultCode == 'success'){
                                autoCloseAlert(data.errorInfo,1000);
                                loadContractList();
                            }else{
                                autoCloseAlert(data.errorInfo,1000);
                            }
                        }
                    });
                }
            },
            success: {
                text: '取消', style: 'danger', action: function () {

                }
            }
        }
    });
    // 调到列表页
});


function exportAll(){
    var contractSignYear = $("#contractSignYear").val();
    var itemCoding = $("#itemCoding").val();
    var salePerson = $("#salePerson").val();
    window.location.href = "/admin/contract/exportAllContract?"+"contractSignYear="+contractSignYear
        +"&itemCoding="+itemCoding+"&salePerson"+salePerson;
};

function exportCurrent(){
    var contractSignYear = $("#contractSignYear").val();
    var itemCoding = $("#itemCoding").val();
    var salePerson = $("#salePerson").val();
    window.location.href = "/admin/contract/exportCurrentContract?"
        +'totalCount='+pager.totalCount+'&page='+pager.page+"&contractSignYear="+contractSignYear
        +"&itemCoding="+itemCoding+"&salePerson"+salePerson;
};