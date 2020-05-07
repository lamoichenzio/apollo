/**
 * Script for Survey.
 * 
 */

$(function() {
    console.info('[Document Ready]');

});


function openQuestionGroupModal() {
    const id = $("#id").val();
    $.ajax({
        type: "GET",
        url: "/apollo/questiongroups/create?id="+id,
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $("#modal_holder").html(response);
            $("#question_group_modal").modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

function submitQuestionGroup(event) {
    // Get survey id
    //var id = getUrlParameter('id');
    

    $("#question_group_modal").submit(function(event) {
        event.preventDefault();

        let request = $("#question_group_form").serialize();
        let postUrl = "/apollo/questiongroups/create";
        
        $.post(
            postUrl,
            request,
            function (response) {
                let isErr = 'hasError';
                // when there are an error then show error
                if (response.indexOf(isErr) > -1) {
                    // TO DO: handle error and return in survey detail page
                    console.log('ERROR');
                } else {
                    console.log('SUCCESS');
                    // TO DO: close modal and add new section in survey detail page
                }
            }
        );
        return false;
    });
}

var getUrlParameter = function getUrlParameter(sParam) {
    let sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};