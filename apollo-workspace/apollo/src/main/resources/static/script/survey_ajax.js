/**
 * Script for Survey.
 *
 */

const CHECK = 'CHECK';
const RADIO = 'RADIO';
const SELECT = 'SELECT';

const MATRIX = 'MATRIX';
const STANDARD = 'STANDARD';

const SPINNER = '<div id="spinner" class="spinner-border text-success" role="status"><span class="sr-only"> Loading...</span></div >';

var optionList = [];
var optionValues = [];


/** SURVEY FUNCTIONS **/

/**
 * Do a GET request for the survey model.
 * @param {String} url 
 * @param {String} modal_id 
 * @param {Object} param 
 */
function getSurveyRequest(url, modal_id, param) {
    event.preventDefault();

    let request = getRequestByUrl(url, param, 'Survey', 'get');

    $.ajax({
        type: "GET",
        url: url,
        data: request,
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 10000,
        success: function (response) {
            $("#modal_holder").html(response);
            $(modal_id).modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

/**
 * Do a POST request for the survey model.
 * @param {String} url
 * @param {String} param
 */
function postSurveyRequest(url, param) {
    event.preventDefault();

    let url_splitted = url.split('/');
    let request = getRequestByUrl(url, param, 'Survey', 'post');
    
    $("#result_container").prepend(SPINNER);

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(request),
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: function (response) {
            $("#spinner").remove();

            if (response.msg == "active") {
                if (url_splitted[url_splitted.length - 1] === 'publish') {
                    surveyPublished(response);
                }
            } 

            if (response.msg == "disabled") {
                if (url_splitted[url_splitted.length - 1] === 'publish') {
                    surveyDisabled(response);
                }
            } 
            
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

/**
 * Do somethings when a survey is published.
 * @param {Object} response 
 */
function surveyPublished(response) {
    $("#urlId").val(response.result.urlId);
    $("#success_message").show();
    $("#survey_active").removeClass("badge-danger").addClass("badge-success");
    $("#survey_active").text("Yes");
}

/**
 * Do somethings when a survey is published.
 * @param {Object} response 
 */
function surveyDisabled(response) {
    $("#urlId").val(response.result.urlId);
    $("#disabled_message").show();
    $("#survey_active").removeClass("badge-success").addClass("badge-danger");
    $("#survey_active").text("No");
}


/** QUESTION GROUP FUNCTIONS **/


/**
 * Do a GET request for the Question Group model.
 * @param {String} url 
 * @param {String} modal_id 
 * @param {String} survey_id 
 */
function getQuestionGroupRequest(url, modal_id, survey_id) {
    let request = getRequestByUrl(url, survey_id, 'QuestionGroup', 'get');

    $.ajax({
        type: "GET",
        url: url,
        data: request,
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 10000,
        success: function (response) {
            $("#modal_holder").html(response);
            $(modal_id).modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}


/** QUESTION FUNCTIONS **/

/**
 * Open question modal for GET request.
 * @param {String} url 
 * @param {String} type 
 * @param {String} modal_id 
 * @param {Object} request_param 
 */
function openQuestionModal(url, type, modal_id, request_param) {

    let request = getRequestByUrl(url, request_param, 'Question', 'get');

    type != null && type !== SELECT ? request.type = type : null;

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

            // Check Choice Question type
            if (type && (type === CHECK || type === RADIO || type === SELECT)) {
                let instance = $("#instance").val();
                setChoiceQuestionAttr(instance);
            }
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    });
}

// TODO: creare post request per domanda
function postQuestion(){

}

function setChoiceQuestionAttr(instance) {
    optionList = optionList.length > 0 ? [] : optionList;
    setChoiceOptions();
    
    if (instance && instance === MATRIX) {
        optionValues = optionValues.length > 0 ? [] : optionValues;
        setOptionValues();
    }

}

function setChoiceOptions() {
    $('#options_container').children().each(function() {
        optionList.push($(this));
    });
}

function setOptionValues() {
    $("#option_values_container").children().each( function() {
        optionValues.push($(this));
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

function adjustOptionValues() {
    let i = 0;
    for (let item of optionValues) {

        item.attr("id", "option_value_" + i);

        let input_value_container = item.find('div[name ="input_value_container"]');
        input_value_container.attr("id", "value-" + i);
        input_value_container.find('input').attr("name", "optionValues[" + i + "]");
        input_value_container.find('input').attr("id", "optionValues" + i);

        item.find('div[name ="delete_value_container"]').find('a').attr("id", "delete_value_" + i);
        item.find('div[name ="delete_value_container"]').find('a').attr('onclick', 'deleteOptionValue(event, ' + i + ')');

        i += 1;
    }
}


/** UTILITY **/

/**
 * Add option.
 */
function addOption() {
    let item = optionList[0].clone();
    item.find('div[name ="input_container"]').find('input').val("");

    optionList.push(item);
    adjustChoiceOptions();
    $("#options_container").append(optionList);
}

/**
 * Delete Option
 * @param {Event} event 
 * @param {Number} index 
 */
function deleteOption(event, index) {
    event.preventDefault();

    if (optionList.length === 1) {
        $('#option_error').find('span').show();
        setTimeout(function() {
            $('#option_error').find('span').hide();
        }, 2000);
    } else {
        optionList[index].remove();
        optionList.splice(index, 1);
        adjustChoiceOptions();
    }
}

/**
 * Add option value.
 */
function addOptionValue() {
    let item = optionValues[0].clone();
    item.find('div[name ="input_value_container"]').find('input').val("");

    optionValues.push(item);
    adjustOptionValues();
    $("#option_values_container").append(optionValues);
}

/**
 * Delete Option value.
 * @param {Event} event 
 * @param {Number} index 
 */
function deleteOptionValue(event, index) {
    event.preventDefault();

    if (optionValues.length === 1) {
        $('#option_value_error').find('span').show();
        setTimeout(function () {
            $('#option_value_error').find('span').hide();
        }, 2000);
    } else {
        optionValues[index].remove();
        optionValues.splice(index, 1);
        adjustOptionValues();
    }
}

/**
 * Get request body by url.
 * @param {String} url 
 * @param {Object} request_param 
 * @param {String} model 
 */
function getRequestByUrl(url, request_param, model, method) {
    let url_splitted = url.split('/');

    switch (url_splitted[url_splitted.length - 1]) {
        case 'create':
            if (model === 'Survey') return {};
            if (model === 'QuestionGroup') return { survey_id : request_param };
            if (model === 'Question') return { group_id: request_param };
        case 'update':
            return { id: request_param };
        case 'delete':
            return { id: request_param };
        case 'publish':
            if (method === 'post') return request_param;
            if (method === 'get') return { id : request_param };
        default:
            break;
    }
    return null;
}

function copyToClipboard(elem) {
    event.preventDefault();
    let copyText = $("#" + elem);
    copyText.select();
    document.execCommand("copy");
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
