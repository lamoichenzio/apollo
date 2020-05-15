/**
 * Script for Survey.
 *
 */

const CHECK = 'CHECK';
const RADIO = 'RADIO';

var optionList = [];

 // Open modal request
function openModalRequest(url, modalId) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $("#modal_holder").html(response);
            $(modalId).modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

// Open Question Group modal
function openQuestionGroupModal(url, modal_id, survey_id) {
    $.ajax({
        type: "GET",
        url: url,
        data: {
            "id": survey_id
        },
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $("#modal_holder").html(response);
            $(modal_id).modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

// Oper question modal
function openQuestionModal(url, type, modal_id, group_id) {

    let request = { "group_id" : group_id };

    type != null ? request.type = type : null;

    $.ajax({
        type: "GET",
        url: url,
        data: request,
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $("#modal_holder").html(response);
            $(modal_id).modal("show");

            if (type && (type == CHECK || type == RADIO)) {
                optionList = optionList.length > 0 ? [] : optionList;
                setChoiceOptions();
            }
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

function setChoiceOptions() {
    $('#options_container').children().each(function() {
        optionList.push($(this));
    });
}

function adjustChoiceOptions() {
    let i = 0;
    for (let item of optionList) {

        item.attr("id", "choice_option_" + i);

        let input_container = item.find('div[name ="input_container"]');
        input_container.attr("id", "option-" + i);
        input_container.find('input').attr("name", "options[" + i + "]");
        input_container.find('input').attr("id", "options" + i);

        item.find('div[name ="delete_container"]').find('a').attr("id", "delete_choice_" + i);
        item.find('div[name ="delete_container"]').find('a').attr('onclick','deleteOption(event, ' + i + ')');
    
        i += 1;
    }
}

function addOption() {
    let item = optionList[0].clone();
    item.find('div[name ="input_container"]').find('input').val("");

    optionList.push(item);
    adjustChoiceOptions();
    $("#options_container").append(optionList);
}

function deleteOption(event, index) {
    event.preventDefault();

    if (optionList.length == 1) {
        alert("You cannot delete the first element");
    } else {
        optionList[index].remove();
        optionList.splice(index, 1);
        adjustChoiceOptions();
    }

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
// function openQuestionGroupModal(url, survey_id) {
//     console.log(url);
//     $.ajax({
//         type: "GET",
//         url: url,
//         data: {
//           'id': survey_id
//         },
//         dataType: 'html',
//         contentType: 'text/html; charset=UTF-8',
//         cache: false,
//         timeout: 600000,
//         success: function (response) {
//             $("#modal_holder").html(response);
//             $("#modal-create-new-group").modal("show");
//         },
//         error: function (e) {
//             console.log('ERROR', e);
//         }
//     });
// }
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