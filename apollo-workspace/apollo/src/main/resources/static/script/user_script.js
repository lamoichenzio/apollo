function displayalert(url){
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
            console.log('ERROR', e);
        }
    });
}

function submit(url, redirectUrl){
    const pass = $("#password").val()
    $.ajax({
        type: "POST",
        url: url,
        data: pass,
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: (response) => {
            console.log(response);
            if(response.status === "OK"){
                window.location.replace(redirectUrl);
            }
        },
        error: (response) => {
            if(response.responseJSON.status === "ERROR"){
                $("#pass_error").show();
            }
        }
    });
}