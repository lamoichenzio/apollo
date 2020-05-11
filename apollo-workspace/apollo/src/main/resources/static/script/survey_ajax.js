/**
 * Script for Survey.
 *
 */

function openModalRequest(url, modalHolder, modalId) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $(modalHolder).html(response);
            $(modalId).modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

function openChoicheQuestionModal(url, group_id) {

    $.ajax({
        type: "GET",
        url: url,
        data: {
            "id": group_id
        },
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

//
// function submitChoicheQuestion(event) {
//
//     $("#modal-new-choice-question").submit(function (event) {
//         event.preventDefault();
//
//         let request = $("#question_choice_form").serialize();
//         let postUrl = $("#question_choice_form").attr("action");
//
//         $.post(
//             postUrl,
//             request,
//             function (response) {
//                 $("#modal_question_holder").html(response);
//                 $("#modal-new-choice-question").modal("show");
//             }
//         );
//         return false;
//     });
// }
//
//
// function openSurveyModal() {
//     $.ajax({
//         type: "GET",
//         url: "/apollo/surveys/create",
//         dataType: 'html',
//         contentType: 'text/html; charset=UTF-8',
//         cache: false,
//         timeout: 600000,
//         success: function (response) {
//             $("#modal_holder").html(response);
//             $("#modal-create-new-survey").modal("show");
//         },
//         error: function (e) {
//             console.log('ERROR', e);
//         }
//     });
// }
//
// function submitSurvey(event) {
//     $("#modal-create-new-survey").submit(function (event) {
//         event.preventDefault();
//
//         let request = $("#survey_form").serialize();
//         let postUrl = $("#survey_form").attr("action");
//
//         console.log(postUrl);
//         $.post(
//             postUrl,
//             request,
//             function (response) {
//                 $("#modal_question_holder").html(response);
//                 $("#modal-new-choice-question").modal("show");
//             }
//         );
//         return false;
//     });
// }
//
//
function openQuestionGroupModal(url, survey_id) {
    console.log(url);
    $.ajax({
        type: "GET",
        url: url,
        data: {
          'id': survey_id
        },
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
//
//
// function submitQuestionGroup(event) {
//
//     $("#modal-create-new-group").submit(function (event) {
//         event.preventDefault();
//
//         let request = $("#question_group_form").serialize();
//         let postUrl = $("##question_group_form").action();
//
//         $.post(
//             postUrl,
//             request,
//             function (response) {
//                 $("#modal_holder").html(response);
//                 $("#modal-create-new-group").modal("show");
//             }
//         );
//         return false;
//     });
// }
//
//
// var getUrlParameter = function getUrlParameter(sParam) {
//     let sPageURL = window.location.search.substring(1),
//         sURLVariables = sPageURL.split('&'),
//         sParameterName,
//         i;
//
//     for (i = 0; i < sURLVariables.length; i++) {
//         sParameterName = sURLVariables[i].split('=');
//
//         if (sParameterName[0] === sParam) {
//             return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
//         }
//     }
// };