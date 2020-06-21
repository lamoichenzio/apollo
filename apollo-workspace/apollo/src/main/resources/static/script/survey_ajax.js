/**
 * Script for Survey.
 *
 */

const CHECK = 'CHECK';
const RADIO = 'RADIO';
const SELECT = 'SELECT';

const MATRIX = 'MATRIX';
const STANDARD = 'STANDARD';

var optionList = [];
var optionValues = [];

let emails = [];

$(function () {
    // Tooltip inizialize
    $('[data-toggle="tooltip"]').tooltip();

});

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
function postSurveyRequest(requestModel) {
    // Set Spinner
    $(".loader").prepend(LOAD_SPINNER);

    $.ajax({
        type: "POST",
        url: requestModel.url,
        data: requestModel.body,
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        success: function (response) {
            $("#load_spinner").remove();

            // OK for success
            if (response.status === 'OK') {
                handleSurveySuccessResponse(getUrlPostfix(requestModel.url), response);
            } else {
                handleSurveyErrorResponse(response.msg);
            }
        },
        error: function (e) {
            $("#load_spinner").remove();
            handleSurveyErrorResponse(e.responseJSON.msg);
            console.error('ERROR', e);
        }
    });
}

/**
 * Publish survey.
 * @param {String} url 
 * @param {Object} survey 
 */
function sendPublish(url, survey) {
    let request = new RequestModel(url, JSON.stringify(survey));

    if (survey.secret && !survey.active) {
        $("#modal_holder").append(INVITATION_CONFIRM);
        $("#modal-invitation-confirm").modal("show");
        $("#modal_dismiss").trigger("click");
        // adding translations
        $("#send_email_confirmation").text(translations.sendInvitationConfirm);
        $("#cancel").text(translations.cancel);
        $("#publish_invitation_submit").text(translations.send);
        if (emails && emails.length > 0) {
            // Publish and send emails
            $("#publish_invitation_submit").click(() => {
                postSurveyRequest(request);
            });
        } else {
            $("#publish_invitation_submit").attr("disabled", true);
            $("#send_email_error").show();
        }

    } else {
        postSurveyRequest(request);
    }
}

/**
 * Send and save invitation pool;
 * @param {String} url 
 * @param {String} surveyId 
 */
function sendInvitationPool(url, surveyId) {
    let request = new RequestModel(url, null);
    request.setParams({id : surveyId});

    if (checkEmailPool()) {
        request.body = emails.join(';');
        postSurveyRequest(request);
    }
}

/**
 * Handle survey server call success response.
 * @param {String} url_postfix 
 * @param {Object} response 
 */
function handleSurveySuccessResponse(url_postfix, response) {
    console.info('[SUCCESS]::[Message]: ', response.msg);

    if (url_postfix === 'publish') {
        if (response.msg === "active") {
            surveyPublished(response);
            // Handle close confirm modal
            if (response.result.secret) {
                $("#cancel").trigger("click");
            }
        } else if (response.msg === "inactive") {
            surveyUnpublished(response);
        }
    } else {
        // Close modal
        $("#cancel").trigger("click");
    }
}

/**
 * Handle survey server call error response.
 * @param {String} message 
 */
function handleSurveyErrorResponse(message) {
    console.error('[ERROR]::[Message]: ', message);
    $("#error_message").text(message);
    $("#error_message").show();
}

/**
 * Send emails and update survey.
 * @param {String} url 
 * @param {Object} param 
 */
function checkEmailPool() {
    if (emails.length > 0) {
        let invitationPool = emails.filter(validateEmail);
        if (invitationPool.length != emails.length) {
            $("#invalid_mail_msg").show();
            return false;
        }
        return true;
    } else {
        $("#invalid_mail_msg").show();
    }
    return false;
}

/**
 * Handle response when survey is published.
 * @param {Object} response 
 */
function surveyPublished(response) {
    $("#urlId").val(response.result.urlId);
    $("#success_message").show();
    $("#error_message").hide();
    $("#survey_active").removeClass("badge-danger").addClass("badge-success");
    $('#publish_submit').attr("onclick", 'postSurveyRequest("\/apollo\/surveys\/publish",' + JSON.stringify(response.result) + ')');

    if (translations) {  
        $("#survey_active").text(translations.yes);
        $('#publish_submit').text(translations.deactivate);
    } else {
        $('#survey_active').text("Yes");
        $('#publish_submit').text('Disable');
    }
}

/**
 * Do somethings when a survey is published.
 * @param {Object} response 
 */
function surveyUnpublished(response) {
    $("#urlId").val(response.result.urlId);
    $("#error_message").show();
    $("#success_message").hide();
    $("#survey_active").removeClass("badge-success").addClass("badge-danger");
    $("#survey_active").text("No");
    $('#publish_submit').attr("onclick", 'postSurveyRequest("\/apollo\/surveys\/publish",' + JSON.stringify(response.result) + ')');

    if (translations) {
        $('#publish_submit').text(translations.publish);
    } else {
        $('#publish_submit').text('Publish');
    }
}

/**
 * Regex email validator.
 * @param {String} email 
 */
function validateEmail(email) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
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
    
    switch (getUrlPostfix(url)) {
        case 'create':
            if (model === 'Survey') return {};
            if (model === 'QuestionGroup') return { survey_id : request_param };
            if (model === 'Question') return { group_id: request_param };
        case 'update':
            return { id: request_param };
        case 'delete':
            return { id: request_param };
        case 'publish':
            if (method === 'get') return { id : request_param };
            break;
        case 'invitationpool':
            if (method === 'get') return { id: request_param };
            break;
        default:
            break;
    }
    return null;
}

function getUrlPostfix(url) {
    let url_splitted = url.split('/');
    return url_splitted[url_splitted.length - 1];
}

/**
 * Copy to clipboard utility.
 * @param {String} elem 
 */
function copyToClipboard(elem) {
    event.preventDefault();
    let copyText = $("#" + elem);
    copyText.select();
    document.execCommand("copy");
}

/**
 * Request Model class.
 * 
 */
let RequestModel = function(url, body) {
    this.url = url;
    this.params = null;
    this.body = body;

    this.setParams = function (params) {
        this.params = params;
        for (let elem in this.params) {
            this.url += '?' + elem + '=' + params[elem];
        }
    };
}