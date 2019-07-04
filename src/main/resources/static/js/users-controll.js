$(document).ready(function () {
    $('#submitForm').click(function (event) {
        event.preventDefault();
        adding_user();
        $("#modalBox").modal('hide');
    });
    // $('#buttonDelete').submit(function (event) {
    //     event.preventDefault();
    //     delete_user();
    // });

    // $('#buttonDelete').click(function () {
    //     event.preventDefault();
    //     delete_user();
    //     table.row('.selected').remove().draw(false);
    //     document.getElementById("buttonDelete").disabled = true;
    // });
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
        // url: "/api/usersControll",
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

// function delete_user() {
//     var addingFormData = {
//         description: description,
//         name: name,
//         password: password,
//         role: role
//     };
//     $.ajax({
//         // type: "POST",
//         type: "DELETE",
//         // url: "/api/usersControll2",
//         url: "/api/users",
//         data: JSON.stringify(addingFormData),
//         dataType: 'json',
//         contentType: "application/json; charset=utf-8",
//         success: function (data) {
//             // alert("Success of server's adding");
//             $("#TableUsers").dataTable()._fnAjaxUpdate();
//         }
// //                 error: function (e) {
// //                     alert("проблемы с сохранением");
// //                 }
//
//     })
// }