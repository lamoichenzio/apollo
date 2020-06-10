/**
 * Script file for survey overview.
 * 
 */

let questionList = [];
let defaultSelectOption;

let response_mock = {
    question: {
        type: 'INPUT',
        options: null,
        otherChoice: false
    },
    type: null,
    values: [
        {
            answers: ['Pippo pluto e paperino']
        },
        {
            answers: ['Pippo e pluto']
        },
        {
            answers: ['Minnie e topolino']
        },
        {
            answers: ['Non lo so']
        },
        {
            answers: ['Boh']
        }
    ]
};

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
        $("#question_title").text(question.title);
        $("#answers_paceholder").hide();
        $("#answers_container").empty();

        // TO DO: change question select
        if (selectedQuestionId != null && selectedQuestionId != '') {
            $("#spinner").append(SPINNER);
            getAnswersData(question);
        }
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
                aggregateResult(response);
            } else {
                $("#answers_container").append(ALERT);
                $("#alert_text").append('<strong>Error!</strong>' + response.msg);
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
                $("#answers_container").append(ALERT);
                $("#alert_text").append('<strong>Error!</strong>' + response.msg);
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

    switch (result.question.type) {
        case 'CHOICE':
            index = 0;

            // Multi choice
            if (result.type == 'MULTIPLE' && result.values != null) {
                aggregation = aggregateMultiChoice(result);
            } 
            // Single Choice
            else if (result.type == 'SINGLE' && result.values != null) {
                aggregation = aggregateSingleChoice(result);
            } else {
                $("#answers_container").append(ALERT);
                $("#alert_text").append('<strong>Attention!</strong> There is no data to display.');
            }

            if (aggregation && aggregation.options) {

                for (let op of aggregation.options) {
                    let percentValue = op.value ? (op.value / aggregation.total) * 100 : op.value;
                    
                    $("#answers_container").append(CHOICE_SUMMARY);
                    $("#option_name").text(op.label);
                    $("#option_name").attr('id', 'option_name_' + index);
                    $("#option_progress_bar").css(
                        "width", 
                        (percentValue.toString().length > 5 ? percentValue.toFixed(2) : percentValue) + '%');
                    $("#option_progress_bar").attr('id', 'option_progress_bar_' + index);
                    $("#option_progress_value").text((percentValue.toString().length > 5 ? percentValue.toFixed(2) : percentValue) + '%');
                    $("#option_progress_value").attr('title', op.label);
                    $("#option_progress_value").attr('id', 'option_progress_value_' + index);
                    index+=1;
                }
            }
            break;

        case 'INPUT': 
            index = 0;

            if (result.values != null) {
                aggregation = aggregateInput(result);
                for (let val of aggregation.values) {
                    $("#answers_container").append(INPUT_SUMMARY);
                    $("#text_value").text(val.text);
                    $("#text_value").attr('id', 'text_value_' + index);
                    $("#counter_value").text(val.val);
                    $("#counter_value").attr('id', 'counter_value_' + index);
                    index+=1;
                }
            } else {
                $("#answers_container").append(ALERT);
                $("#alert_text").append('<strong>Attention!</strong> There is no data to display.');
            }
            break;

        case 'MATRIX': 
            index = 0;

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
                    $("#answers_container").append(MATRIX_SUMMARY);
                    $("#option_name").text(option.label);
                    $("#option_name").attr('id', 'option_name_' + index);

                    let j = 0;
                    for (let optVal of option.optionValues) {
                        $("#option_list_elements").append(MATRIX_OPTION_ELEM);
                        $("#option_text").text(optVal.text);
                        $("#option_text").attr('id', 'option_text_' + i +'_' +j);
                        $("#option_value").text(optVal.value);
                        $("#option_value").attr('id', 'option_value_' + i +'_' +j);
                        j+=1;
                    }
                    index+=1;
                }
            } else {
                $("#answers_container").append(ALERT);
                $("#alert_text").append('<strong>Attention!</strong> There is no data to display.');
            }

            break;

        case 'SELECTION': 
            index = 0;
            if (result.values != null) {
                aggregation = aggregateSelectionQuestionAnswer(result);
                
                for (let op of aggregation.options) {
                    let percentValue = op.value ? (op.value / aggregation.total) * 100 : op.value;

                    $("#answers_container").append(CHOICE_SUMMARY);
                    $("#option_name").text(op.label);
                    $("#option_name").attr('id', 'option_name_' + index);
                    $("#option_progress_bar").css("width", (percentValue.toString().length > 5 ? percentValue.toFixed(2) : percentValue) + '%');
                    $("#option_progress_bar").attr('id', 'option_progress_bar_' + index);
                    $("#option_progress_value").text((percentValue.toString().length > 5 ? percentValue.toFixed(2) : percentValue) + '%');
                    $("#option_progress_value").attr('title', op.label);
                    $("#option_progress_value").attr('id', 'option_progress_value_' + index);
                    index+=1;
                }
            } else {
                $("#answers_container").append(ALERT);
                $("#alert_text").append('<strong>Attention!</strong> There is no data to display.');
            }
            break;
    
        default: break;
    }
    return null;
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
            optionValues: answer.question.optionValues.map(val => {
                val = { text: val, value: 0 };
            }) 
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
