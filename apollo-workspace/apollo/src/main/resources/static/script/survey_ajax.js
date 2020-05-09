/**
 * Script for Survey.
 * 
 */

$(function() {
    console.info('[Document Ready]');

});

function openChoicheQuestionModal(group_id) {
		
	const id = group_id;
    
	$.ajax({
        type: "GET",
        url: "/apollo/questions/choicequestion/create?id=" + id,
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $("#modal_question_holder").html(response);
            $("#modal-new-choice-question").modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

function submitChoicheQuestion(event) {
    
    $("#modal-new-choice-question").submit(function(event) {
        event.preventDefault();

        let request = $("#question_choice_form").serialize();
        let postUrl = "/apollo/questions/choicequestion/create";
        
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


function openSurveyModal() {
	
	console.log("pippo");
	
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

function submitSurvey(event) {
    // Get survey id
    //var id = getUrlParameter('id');
    
    $("#modal-create-new-survey").submit(function(event) {
        event.preventDefault();

        let request = $("#survey_form").serialize();
        let postUrl = "/apollo/survey/create";
        
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


function openQuestionGroupModal(survey_id) {
	
    const id = survey_id;
	
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