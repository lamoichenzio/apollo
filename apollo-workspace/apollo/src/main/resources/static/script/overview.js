/**
 * Script file for survey overview.
 * 
 */

let questionList = [];
let defaultSelectOption;
let defaultQuestionTitle;

const SPINNER = '<div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status"><span class="sr-only"> Loading...</span></div>';

const CHOICE_SUMMARY = '<div class="row mb-2">'
    + '<div class="col-9">'
    + '<div class="progress-wrapper">'
    + '<span id="option_name" data-toggle="tooltip" class="progress-label text-muted text-sm"></span>'
    + '<div class="progress mt-1 mb-2" style="height: 5px;">'
    + '<div id="option_progress_bar" class="progress-bar bg-purple" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%;"></div>'
    + '</div>'
    + '</div>'
    + '</div>'
    + '<div class="col-3 align-self-end text-right">'
    + '<span id="option_progress_value" data-toggle="tooltip" class="h6 mb-0"></span>'
    + '</div>'
    + '</div>';

const INPUT_SUMMARY = '<div class="card mb-3 border shadow-none">'
    +'<div class="px-3 py-3">'
    +'<div class="row align-items-center">'
    +'<div class="col-auto>'
    +'<span><i class="fas fa-align-left fa-3x"></i></span>'
    +'</div>'
    +'<div class="col ml-n2">'
    +'<p id="text_value" class="card-text small text-muted"></p>'
    +'</div>'
    +'<div class="col-auto">'
    +'<span id="counter_value" class="text-muted"></span>'
    +'</div>'
    +'</div>'
    +'</div>'
    +'</div>';

$(function () {
    defaultSelectOption = $("#default_option");
    defaultQuestionTitle = $("#title_placeholder");

    // Tooltip inizialize
    $('[data-toggle="tooltip"]').tooltip();

    // Handle change group
    $("#group_select").change(function () {
        let selectedGroupId = $(this).children("option:selected").val();
        
        // Reset card-body
        $("#question_title").text('');
        $("#question_select").empty();
        $("#question_select").append(defaultSelectOption);
        $("#answers_paceholder").show();
        $("#answers_container").empty();

        // Call server controller
        if (selectedGroupId != null && selectedGroupId != '') {
            getQuestionsData(selectedGroupId);
        }
    });

    // Handle change question
    $("#question_select").change(function () {
        let selectedQuestionId = $(this).children("option:selected").val();
        let question = questionList.find((elem) => elem.id == selectedQuestionId);

        // Reset answers container
        $("#question_title").text(question.title);
        $("#answers_paceholder").hide();
        $("#answers_container").empty();

        // TO DO: change question select
        if (selectedQuestionId != null && selectedQuestionId != '') {
            // getAnswersData(question);
        }

        let response = { 
            questionType: 'CHOICE',
            choiceQuestionMultiAnswers: [
                {
                    question: {
                        options: ['Opzione 1', 'Opzione 2', 'Opzione 3', 'Opzione 4']
                    },
                    answers: ['Opzione 1', 'Opzione 4']
                },
                {
                    question: {
                        options: ['Opzione 1', 'Opzione 2', 'Opzione 3', 'Opzione 4']
                    },
                    answers: ['Opzione 1', 'Opzione 2']
                },
        ]};
        aggregateResult(response);

    });
});

/**
 * Call server controller in order to retrieve Answers data by selected question.
 * @param {String} question_id
 */
function getAnswersData(question) {

    let request = {
        id: question.id, 
        type: question.type 
    };

    $.ajax({
        type: "POST",
        url: '/apollo/answers/findanswers',
        data: JSON.stringify(request),
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: function (response) {
            $("#spinner").empty();

            // OK for success
            if (response.status === 'OK') {
                // TO DO: Aggregate results
                aggregateResult(response);
            } else {
                console.log(response.msg);
                // TO DO: Open a modal for error notification
            }

        },
        error: function (e) {
            console.log('ERROR', e);
            // TO DO: Redirect to ServerError page
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
            // TO DO: Redirect to ServerError page
        }
    });
}

/**
 * Build aggregation result.
 * @param {Object} result 
 */
function aggregateResult(result) {
    let aggregation;
    let index;

    switch (result.questionType) {
        case 'CHOICE':
            // Multi choice
            index = 0;
            if (result.choiceQuestionMultiAnswers != null) {
                aggregation = aggregateMultiChoice(result.choiceQuestionMultiAnswers);
            } // Single Choice
            else if (result.choiceQuestionSingleAnswers != null) {
                aggregation = aggregateSingleChoice(result.choiceQuestionSingleAnswers);
            }

            for (let op of aggregation.options) {
                let percentValue = op.value ? (op.value / aggregation.total) * 100 : op.value;

                $("#answers_container").append(CHOICE_SUMMARY);
                $("#option_name").text(op.label);
                $("#option_name").attr('id', 'option_name_' + index);
                $("#option_progress_bar").css("width", percentValue + '%');
                $("#option_progress_bar").attr('id', 'option_progress_bar_' + index);
                $("#option_progress_value").text(percentValue + '%');
                $("#option_progress_value").attr('title', op.label);
                $("#option_progress_value").attr('id', 'option_progress_value_' + index);
            }

            break;

        case 'INPUT': 
            index = 0;
            if (result.inputQuestionAnswers != null) {
                aggregation = aggregateInput(result.inputQuestionAnswers);
                for (let val of aggregation.values) {
                    $("#answers_container").append(INPUT_SUMMARY);
                    $("#text_value").text(val.text);
                    $("#text_value").attr('id', 'text_value_' + index);
                    $("#counter_value").text(val.val);
                    $("#counter_value").attr('id', 'counter_value_' + index);
                }
            }
            break;

        case 'MATRIX': break;
        case 'SELECTION': break;
    
        default: break;
    }
    return null;
}

/**
 * Aggregate results for ChoiceQuestionMultiAnswer.
 * @param {Array} answers 
 */
function aggregateMultiChoice(answers) {
    let aggregation = { options:[], otherValues: '', total: 0 };

    answers[0].question.options.forEach(elem => {
        aggregation.options.push({ label: elem, value: 0 });
    });

    // Fill aggregation element
    let other = 0;
    for (let data of answers) {
        data.answers.forEach(elem => {
            aggregation.options.forEach(item => {
                if (item.label == elem) item.value += 1;
                else { other += 1; aggregation.otherValues += elem + ',' }
            });
            aggregation.total += 1;
        });
    }

    if (answers[0].question.otherChoice) aggregation.options.push({ label: 'other', value: other });
    aggregation.otherValues = aggregation.otherValues.length ? aggregation.otherValues.slice(0, -1) : null;

    return aggregation;
}

/**
 * Aggregate results for ChoiceQuestionSingleAnswer.
 * @param {Array} answers 
 */
function aggregateSingleChoice(answers) {
    let aggregation = { options: [], otherValues: '', total: 0 };

    answers[0].question.options.forEach(elem => {
        aggregation.options.push({ label: elem, value: 0 });
    });

    // Fill aggregation element
    let other = 0;
    for (let data of answers) {
        aggregation.options.forEach(item => {
            if (item.label == data.answer) item.value += 1;
            else { other += 1; aggregation.otherValues += elem + ',' }
            aggregation.total += 1;
        });
    }

    if (answers[0].question.otherChoice) aggregation.options.push({ label: 'other', value: other });
    aggregation.otherValues = aggregation.otherValues.length ? aggregation.otherValues.slice(0, -1) : null;

    return aggregation;
}

/**
 * Aggregate results for InputQuestionAnswer.
 * @param {Array} answers 
 */
function aggregateInput(answers) {
    let aggregation = { values: [], total: 0, type: null};

    // Fill aggregation element
    for (let data of answers) {
        if (aggregation.values.length) {
            aggregation.values.forEach(elem => {
                if (elem.text == data.answer) elem.val += 1;
            });
        } else {
            aggregation.values.push({ text: data.answer, val: 0});
        }

        aggregation.total += 1;
    }
    aggregation.type = answers[0].question.type;

    return aggregation;
}

/**
 * Aggregate results for MultiChoiceMatrixAnswer.
 * @param {Array} answers 
 */
function aggregateMultiChoiceMatrixAnswer(answers) {
    let aggregation = { options: [], total: 0 };

    answers[0].question.options.forEach(elem => {
        aggregation.options.push({
            label: elem, 
            optionValues: answers[0].question.optionValues.map(val => {
                val = { text: val, value: 0 };
            }) 
        });
    });

    // Fill aggregation element
    for (let data of answers) {
        for (let [key, matrixValues] of Object.entries(data.answers)) {
            aggregation.options.forEach(item => {
                if (item.label == key) {
                    matrixValues.values.forEach(val => {
                        item.optionValues.forEach(optVal => { 
                            if (val == optVal.text) optVal.value += 1;
                            aggregation.total += 1;
                        });
                    });
                }
            });
        }
    }

    return aggregation;
}

/**
 * Aggregate results for SingleChoiceMatrixAnswer.
 * @param {Array} answers 
 */
function aggregateSingleChoiceMatrixAnswer(answers) {
    let aggregation = { options: [], total: 0 };

    answers[0].question.options.forEach(elem => {
        aggregation.options.push({
            label: elem,
            optionValues: answers[0].question.optionValues.map(val => {
                val = { text: val, value: 0 };
            })
        });
    });

    // Fill aggregation element
    for (let data of answers) {
        for (let [key, matrixValue] of Object.entries(data.answers)) {
            aggregation.options.forEach(item => {
                if (item.label == key) {
                    item.optionValues.forEach(optVal => {
                        if (matrixValue == optVal.text) optVal.value += 1;
                        aggregation.total += 1;
                    });
                }
            });
        }
    }

    return aggregation;
}

/**
 * Aggregate results for ChoiceQuestionSingleAnswer.
 * @param {Array} answers 
 */
function aggregateSelectionQuestionAnswer(answers) {
    let aggregation = { options: [], total: 0 };

    answers[0].question.options.forEach(elem => {
        aggregation.options.push({ label: elem, value: 0 });
    });

    // Fill aggregation element
    for (let data of answers) {
        aggregation.options.forEach(item => {
            if (item.label == data.answer) item.value += 1;
            aggregation.total += 1;
        });
    }

    return aggregation;
}