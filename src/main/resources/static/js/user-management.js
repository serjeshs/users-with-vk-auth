$(document).ready(function () {
    $('#submitForm').click(function (event) {
        event.preventDefault();
        adding_user();
        $("#modalBox").modal('hide');
    });
});

function adding_user() {

    var addingFormData = {
        description : $('#description').val(),
        name : $('#name').val(),
        password : $('#password').val(),
        role : $('#role').val()
    };

    $.ajax({
        type: "POST",
        url: "/api/users",
        data: JSON.stringify(addingFormData),
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            alert("Пользователь успешно создан");
            $("#TableUsers").dataTable()._fnAjaxUpdate();
        },
        error: function (e) {
            alert("проблемы с сохранением");
        }

    })

}
