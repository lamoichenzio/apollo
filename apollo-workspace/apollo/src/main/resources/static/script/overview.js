/**
 * Script file for survey overview.
 * 
 */

let questionList = [];
let defaultSelectOption;

$(function () {
    defaultSelectOption = $("#default_option");

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
        $("#answers_paceholder").hide();
        $("#answers_container").empty();

        // TO DO: change question select
        if (selectedQuestionId != null && selectedQuestionId != '') {
            $("#spinner_container").append(SPINNER_PRIMARY);
            getAnswersData(question);
        }
    });

});


/**
 * Call server controller in order to retrieve Answers data by selected question.
 * @param {String} question_id
 */
function getAnswersData(question) {
    let urlRequest = `/apollo/answers/findanswers/${question.type}/${question.id}`;

    $.ajax({
        type: "GET",
        url: urlRequest,
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: function (response) {
            $("#spinner_container").empty();
   
            // OK for success
            if (response.status === 'OK') {
                $("#question_title").text(question.title);
                aggregateResult(response);
            } else {
                $("#answers_container").append(showAlert('error', translations.errortitle + '!', response.msg));
            }

        },
        error: function (e) {
            console.error('ERROR', e);
            $("#answers_container").append(showAlert('error', translations.errortitle + '!', e));
        }
    });
}

/**
 * Call server controller in order to retrieve questions data by group id.
 * @param {String} groupId
 */
function getQuestionsData(groupId) {
    $.ajax({
        type: "GET",
        url: `/apollo/questions/questionlist/${groupId}`,
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
                    i += 1;
                }
            } else {
                $("#answers_container").append(showAlert('error', translations.errortitle + '!', response.msg));
            }

        },
        error: function (e) {
            console.log('ERROR', e);
            showAlert('error', translations.errortitle + '!', e);
        }
    });
}

/**
 * Build aggregation result.
 * @param {Object} result 
 */
function aggregateResult(result) {
    let aggregation;

    switch (result.question.type) {
        case 'CHOICE':
            // Multi choice
            if (result.type == 'MULTIPLE' && result.values != null) {
                aggregation = aggregateMultiChoice(result);
            } 
            // Single Choice
            else if (result.type == 'SINGLE' && result.values != null) {
                aggregation = aggregateSingleChoice(result);
            } else {
                $("#answers_container").append(showAlert('warning', translations.attention, translations.nodatatodisplay));
                return;
            }

            if (aggregation && aggregation.options) {
                for (let option of aggregation.options) {
                    let percentValue = option.value ? (option.value / aggregation.total) * 100 : option.value;
                    $("#answers_container").append(getChoiceSummary(option.label, percentValue));
                }
            }
            break;

        case 'INPUT': 
            // Set incon
            let inputIcon = getInputSummaryIcon(result.type);

            if (result.values != null && result.values.length) {
                aggregation = aggregateInput(result);
                for (let value of aggregation.values) {
                    $("#answers_container").append(getInputSummary(inputIcon, value.text, value.val));
                }
            } else {
                $("#answers_container").append(showAlert('warning', translations.attention, translations.nodatatodisplay));
                return;
            }
            break;

        case 'MATRIX': 
            // Multi choice matrix
            if (result.type == 'MULTIPLE' && result.values != null) {
                aggregation = aggregateMultiChoiceMatrixAnswer(result);
            }
            // Single Choice matrix
            else if (result.type == 'MULTIPLE' && result.values != null) {
                aggregation = aggregateSingleChoiceMatrixAnswer(result);
            }

            if (aggregation && aggregation.options) {
                for (let option of aggregation.options) {
                    $("#answers_container").append(getMatrixSummary(option.label, option.optionValues));
                }
            } else {
                $("#answers_container").append(showAlert('warning', translations.attention, translations.nodatatodisplay));
            }
            break;

        case 'SELECTION': 
            if (result.values != null) {
                aggregation = aggregateSelectionQuestionAnswer(result);
                
                for (let option of aggregation.options) {
                    let percentValue = option.value ? (option.value / aggregation.total) * 100 : option.value;
                    $("#answers_container").append(getChoiceSummary(option.label, percentValue));
                }
            } else {
                $("#answers_container").append(showAlert('warning', translations.attention, translations.nodatatodisplay));
            }
            break;
    
        default: break;
    }
}

/**
 * Aggregate results for ChoiceQuestionMultiAnswer.
 * @param {Object} answer
 */
function aggregateMultiChoice(answer) {
    let aggregation = { options:[], otherValues: '', total: 0 };

    answer.question.options.forEach(elem => {
        aggregation.options.push({ label: elem, value: 0 });
    });

    // Fill aggregation element
    let other = 0;
    for (let data of answer.values) {
        let otherVal = null;
        data.answers.forEach(elem => {
            let row = aggregation.options.find(item => item.label == elem);
            if (row != undefined) {
                row.value += 1;
            } else {
                other += 1;
                aggregation.otherValues += otherVal + ',';
            }
            aggregation.total += 1;
        });

    }

    if (answer.question.otherChoice) aggregation.options.push({ label: 'Other', value: other });
    aggregation.otherValues = aggregation.otherValues.length ? aggregation.otherValues.slice(0, -1) : null;

    return aggregation;
}

/**
 * Aggregate results for ChoiceQuestionSingleAnswer.
 * @param {Object} answer
 */
function aggregateSingleChoice(answer) {
    let aggregation = { options: [], otherValues: '', total: 0 };

    answer.question.options.forEach(elem => {
        aggregation.options.push({ label: elem, value: 0 });
    });

    // Fill aggregation element
    let other = 0;
    for (let data of answer.values) {
        let otherVal = null;
        let row = aggregation.options.find(item => item.label == data.answers[0]);
        if (row != undefined) {
            row.value += 1;
        } else {
            other += 1;
            aggregation.otherValues += otherVal + ',';
        }
        aggregation.total += 1;
    }

    if (answer.question.otherChoice) aggregation.options.push({ label: 'Other', value: other });
    aggregation.otherValues = aggregation.otherValues.length ? aggregation.otherValues.slice(0, -1) : null;

    return aggregation;
}

/**
 * Aggregate results for InputQuestionAnswer.
 * @param {Object} answer
 */
function aggregateInput(answer) {
    let aggregation = { values: [], total: 0, type: null};

    // Fill aggregation element
    for (let data of answer.values) {
        if (aggregation.values.length) {
            let row = aggregation.values.find(item => item.label == data.answers[0]);
            if (row != undefined) {
                row.val += 1;
            } else {
                aggregation.values.push({ text: data.answers[0], val: 1 });
            }
        } else {
            aggregation.values.push({ text: data.answers[0], val: 1 });
        }

        aggregation.total += 1;
    }

    aggregation.type = answer.question.type;

    return aggregation;
}

/**
 * Aggregate results for MultiChoiceMatrixAnswer.
 * @param {Object} answer
 */
function aggregateMultiChoiceMatrixAnswer(answer) {
    let aggregation = { options: [], total: 0 };

    answer.question.options.forEach(elem => {
        aggregation.options.push({
            label: elem, 
            optionValues: answer.question.optionValues.map(val => val = { text: val, value: 0 }) 
        });
    });

    // Fill aggregation element
    for (let data of answer.values) {
        for (let [key, matrixValues] of Object.entries(data.matrixAnswers)) {
            aggregation.options.forEach(item => {
                if (item.label == key) {
                    matrixValues.forEach(val => {
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
 * @param {Object} answer
 */
function aggregateSingleChoiceMatrixAnswer(answer) {
    let aggregation = { options: [], total: 0 };

    answer.question.options.forEach(elem => {
        aggregation.options.push({
            label: elem,
            optionValues: answer.question.optionValues.map(val => {
                val = { text: val, value: 0 };
            })
        });
    });

    // Fill aggregation element
    for (let data of answer.values) {
        for (let [key, matrixValue] of Object.entries(data.matrixAnswers)) {
            aggregation.options.forEach(item => {
                if (item.label == key) {
                    item.optionValues.forEach(optVal => {
                        if (matrixValue[0] == optVal.text) optVal.value += 1;
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
 * @param {Object} answer
 */
function aggregateSelectionQuestionAnswer(answer) {
    let aggregation = { options: [], total: 0 };

    answer.question.options.forEach(elem => {
        aggregation.options.push({ label: elem, value: 0 });
    });

    // Fill aggregation element
    for (let data of answer.values) {
        let row = aggregation.options.find(item => item.label == data.answers[0]);
        if (row != undefined) {
            row.value += 1;
        } else {
            other += 1;
            aggregation.otherValues += otherVal + ',';
        }
        aggregation.total += 1;
    }

    return aggregation;
}

/**
 * Get total number of questions in a survey.
 * @param {Array} groups 
 */
function getTotQuestions(groups) {
    let counter = 0;
    for (let group of groups) {
        counter += group.questions ? group.questions.length : 0;
    }
    return counter;
}