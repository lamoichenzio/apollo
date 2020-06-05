/**
 * Script file for survey overview.
 * 
 */

let questionList = [];
let defaultSelectOption;
let defaultQuestionTitle;

$(function () {
    defaultSelectOption = $("#default_option");
    defaultQuestionTitle = $("#question_title");

    // Tooltip inizialize
    $('[data-toggle="tooltip"]').tooltip();

    // Handle change group
    $("#group_select").change(function () {
        let selectedGroupId = $(this).children("option:selected").val();
        $("#question_title").text(defaultQuestionTitle);
        getQuestionsData(selectedGroupId);
    });

    // Handle change question
    $("#question_select").change(function () {
        let selectedQuestionId = $(this).children("option:selected").val();
        let question = questionList.find((elem) => elem.id == selectedQuestionId);
        $("#question_title").text(question.title);
        // TO DO: change question select
        getAnswersData(question);
    });
});

/**
 * Call server controller in order to retrieve Answers data by selected question.
 * @param {String} question_id
 */
function getAnswersData(question) {

    $.ajax({
        type: "GET",
        url: '/apollo/answers/findanswers',
        data: JSON.stringify({ id: question.id, type: question.type }),
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: function (response) {

            // 1 for success
            if (response.status === 'OK') {
            } else {
            }

        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

/**
 * Call server controller in order to retrieve questions data by group id.
 * @param {String} group_id
 */
function getQuestionsData(group_id) {
    $.ajax({
        type: "GET",
        url: '/apollo/questions/questionlist',
        data: { id:group_id },
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: function (response) {
            console.log(response);
            // OK for success
            if (response.status === 'OK') {
                questionList = response.questions;

                // Rebuild question select
                $("#question_select").empty();
                $("#question_select").append(defaultSelectOption);

                let i = 1;
                for (let data of questionList) {
                    let option = '<option value="' + data.id + '">' + i + '</option>';
                    $("#question_select").append(option);
                }
            } else {
                console.log(response.msg);
                // TO DO: Open a modal for error notification
            }

        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}