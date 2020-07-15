/**
 * 
 * User script file.
 * 
 */

$(function () {
    $("#file-1").change(() => {
        let filename = $("#file-1").val();
        $("#filename").html(filename.replace(/^.*\\/, ""));
    });
});

/**
 * Display alert.
 * @param {String} url 
 */
function displayalert(url) {
    console.info('[Alert]::[Open]');

    $.ajax({
        type: "GET",
        url: url,
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 10000,
        success: function (response) {
            $("#modal_holder").html(response);
            $("#modal-delete-user").modal("show");
        },
        error: function (e) {
            console.error('ERROR', e);
            $('.alert-container').append(showAlert('error', 'Error!', e.responseJSON.msg)).show();
        }
    });
}

/**
 * Handle submit
 * @param {String} url 
 * @param {String} redirectUrl 
 */
function submit(url, redirectUrl){
    const pwd = $("#password").val();

    $.ajax({
        type: "POST",
        url: url,
        data: pwd,
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: (response) => {
            console.info('[SUCCESS]::[Status]:', response.status);
            if(response.status === "OK"){
                window.location.replace(redirectUrl);
            }
        },
        error: (response) => {
            console.error('ERROR', e);
            if(response.responseJSON.status === "ERROR"){
                $("#pass_error").show();
            }
        }
    });
}