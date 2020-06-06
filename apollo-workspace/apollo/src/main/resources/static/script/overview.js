/**
 * Script file for survey overview.
 * 
 */

let questionList = [];
let defaultSelectOption;
let defaultQuestionTitle;


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
            questionType: 'INPUT',
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

    switch (result.questionType) {
        case 'CHOICE':
            index = 0;

            // Multi choice
            if (result.choiceQuestionMultiAnswers != null) {
                aggregation = aggregateMultiChoice(result.choiceQuestionMultiAnswers);
            } 
            // Single Choice
            else if (result.choiceQuestionSingleAnswers != null) {
                aggregation = aggregateSingleChoice(result.choiceQuestionSingleAnswers);
            } else {
                $("#answers_container").append(ALERT);
                $("#alert_text").append('<strong>Attention!</strong> There is no data to display.');
            }

            if (aggregation.options) {
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
                    index+=1;
                }
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
            if (result.multiChoiceMatrixAnswers != null) {
                aggregation = aggregateMultiChoice(result.multiChoiceMatrixAnswers);
            }
            // Single Choice matrix
            else if (result.singleChoiceMatrinxAnswers != null) {
                aggregation = aggregateSingleChoice(result.singleChoiceMatrinxAnswers);
            }

            if (aggregation.options) {
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
            if (result.selectionQuestionAnswers != null) {
                aggregation = aggregateSelectionQuestionAnswer(result).selectionQuestionAnswers;
                
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
