<%@include file="/common/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:url var="APIurl" value="/admin-new"/>
<c:url var="APIurl" value="/api-admin-new"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa bài viết</title>
</head>
<body>

<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs ace-save-state" id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trangchủ</a>
                </li>
                <li class="active">Chỉnh sửa bài viết</li>
            </ul>
            <!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${not empty messageResponse}">
                        <div class="alert alert-${alert}">
                                ${messageResponse}
                        </div>
                    </c:if>
                    <form id="formSubmit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Thể Loại</label>
                            <div class="col-sm-9">
                                <select class="form-control" id="categoryCode" name="categoryCode">
                                    <c:if test="${empty model.categoryCode}">
                                        <option value="">Chọn loại bài viết</option>
                                        <c:forEach var="item" items="${categories}">
                                            <option value="${item.code}">${item.name}</option>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${not empty model.categoryCode}">
                                        <option value="">Chọn loại bài viết</option>
                                        <c:forEach var="item" items="${categories}">
                                            <option value="${item.code}"
                                                    <c:if test="${item.code == model.categoryCode}">selected="selected"</c:if>>
                                                    ${item.name}
                                            </option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                        <br><br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Tiêu đề</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="title" name="title"
                                       value="${model.title}"/>
                            </div>
                        </div>
                        <br><br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Hình đại diện</label>
                            <div class="col-sm-9">
                                <input type="file" name="file" id="uploadImage"/>
                            </div>
                        </div>
                        <br><br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"></label>
                            <div class="col-sm-9">
                                <c:if test="${not empty model.thumbnail}">
                                    <c:set var="image" value="/fileupload/new/${model.thumbnail}"/>
                                </c:if>
                                <img src="${image}" id="viewImage" width="250px" height="150px">
                            </div>
                        </div>
                        <br><br/>
                        <div class="form-group">
                            <label class="col-sm-12 control-label no-padding-right"></label>
                        </div>
                        <br><br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Mô tả ngắn</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="shortDescription"
                                       name="shortDescription" value="${model.shortDescription}"/>
                            </div>
                        </div>
                        <br><br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Nội dung</label>
                        </div>
                        <br><br/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <textarea rows="" cols="" id="content" style="width: 820px; height: 275px;">${model.content}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <c:if test="${not empty model.id}">
                                    <input type="button" class="btn btn-white btn-warning btn-bold"
                                           value="Cập nhật bài viết" id="btnAddOrUpdateNew"/>
                                </c:if>
                                <c:if test="${empty model.id}">
                                    <input type="button" class="btn btn-white btn-warning btn-bold"
                                           value="Thêm bài viết" id="btnAddOrUpdateNew"/>
                                </c:if>
                            </div>
                        </div>
                        <input type="hidden" value="${model.id}" id="id" name="id"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script
>
  var editor = '';
  // hàm này sẽ chạy đầu tiên khi page load lên
  $(document).ready(function () {
    editor = CKEDITOR.replace('content');
    CKFinder.setupCKEditor(editor,'/ckfinder');
  });

  // gọi đối tượng trong jquery bootraps bằng id
  $('#btnAddOrUpdateNew').click(function (e) {
    e.preventDefault(); // tránh nhầm URL
    // get tất cả các giá trị trong form vào 1 mảng
    var data = {};
    var formData = $('#formSubmit').serializeArray();// không gét dc giá trị của editor nên phải get riêng

    // dugf vòng lặp for để duyệt mảng
    $.each(formData, function (i, v) {
      data["" + v.name + ""] = v.value;
    });
    data["content"] = editor.getData();// get gia tri cua editor
    var id = $('#id').val();
    if (id == "") {
      addNew(data);
    } else {
      updateNew(data);
    }
  });

  function addNew(data) {
    $.ajax({
      url: '${APIurl}',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(data), // ở client gọi là javascript object -> json để server hiểu
      dataType: 'json', // trên server trả về client (reponse) kiểu dữ liệu là json
      success: function (result) {
        window.location.href = "${NewURL}?type=edit&id=" + result.id + "&message=insert_success";
      },
      error: function (error) {
        window.location.href = "${NewURL}?type=list&maxPageItem=2&page=1&message=error_system";
      }
    });
  }

  function updateNew(data) {
    $.ajax({
      url: '${APIurl}',
      type: 'PUT',
      contentType: 'application/json',
      data: JSON.stringify(data), // ở client gọi là javascript object -> json để server hiểu
      dataType: 'json', // trên server trả về client (reponse) kiểu dữ liệu là json
      success: function (result) {
        window.location.href = "${NewURL}?type=edit&id=" + result.id + "&message=update_success";
      },
      error: function (error) {
        window.location.href = "${NewURL}?type=list&maxPageItem=2&page=1&message=error_system";
      }
    });
  }
</script>
</body>
</html>