/**
 * Script for Survey.
 * 
 */

$(function() {
    console.info('[Document Ready]');

});

function openSurveyModal() {
    $.ajax({
        type: "GET",
        url: "/apollo/surveys/create",
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $("#modal_holder").html(response);
            $("#modal-create-new-survey").modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}


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
            $("#modal-create-new-group").modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

function submitQuestionGroup(event) {
    // Get survey id
    //var id = getUrlParameter('id');
    

    $("#modal-create-new-group").submit(function(event) {
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
                    $("#modal-create-new-group").modal("hide");
                    
                    
                    
                    // eliminare il modal
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