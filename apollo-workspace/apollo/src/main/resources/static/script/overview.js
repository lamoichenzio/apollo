/**
 * Script file for survey overview.
 * 
 */

let response_body = {
    question_id: "",
    question_type: "",
    total_answer: "",
    list_answer: {

    }
};

$(function () {
    // Tooltip inizialize
    $('[data-toggle="tooltip"]').tooltip();

    // Get Answers data

    // Handle change group
    $("#group_select").change(function () {
        let selectedGroup = $(this).children("option:selected").val();
        // TO DO: change group select
        getQuestionsData(selectedGroup);
    });

    // Handle change question
    $("#question_select").change(function () {
        let selectedQuestion = $(this).children("option:selected").val();
        // $("#question_title").text(selectedQuestion);
        // TO DO: change question select
        getAnswersData(selectedQuestion);
    });
});

function getAnswersData(request) {

    $.ajax({
        type: "GET",
        url: '/apollo/answers/findanswers',
        data: JSON.stringify(request),
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: function (response) {

            // 1 for success and 0 for failure
            if (response.status) {
            } else {
            }

        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

function getQuestionsData(request) {
    $.ajax({
        type: "GET",
        url: '/apollo/questions/list',
        data: { id:request },
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: function (response) {
            console.log(response);
            // 1 for success and 0 for failure
            if (response.status) {
                let default_option = $("#default_option");
                $("#question_select").empty();
                $("#question_select").append(default_option);
                let i = 1;
                for (let data of response.result) {
                    let option = '<option value="' + data.title + '">' + i + '</option>';
                    $("#question_select").append(option);
                }
            } else {
            }

        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}