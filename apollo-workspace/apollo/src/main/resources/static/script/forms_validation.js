/**
 * 
 * Form validation script.
 *
 */

$(function () {

    // Register questions form submit event

    $('#choice_question_form').submit(function (event) {
        let formData = $(this).serializeArray();
        let options = formData.filter(elem => elem.name.includes('options'));
        if (choiceFormValidation(options)) event.preventDefault();
    });

    $('#selection_question_form').submit(function (event) {
        let formData = $(this).serializeArray();
        let options = formData.filter(elem => elem.name.includes('options'));
        if (choiceFormValidation(options)) event.preventDefault();
    });

    $('#matrix_question_form').submit(function (event) {
        let formData = $(this).serializeArray();
        let options = formData.filter(elem => elem.name.includes('options'));
        let values = formData.filter(elem => elem.name.includes('optionValues'));
        if (matrixFormValidation(options, values)) event.preventDefault();
    });

    $('#input_question_form').submit(function (event) {
        // Check file validity
        if (fileValidation()) event.preventDefault();
    });

});

/**
 * Check duplicates in object array.
 * @param {Array} arr 
 */
function hasDuplicates(arr) {
    let counts = [];

    for (let i = 0; i < arr.length; i++) {
        if (counts[arr[i].value] === undefined) {
            counts[arr[i].value] = 1;
        } else {
            return true;
        }
    }
    return false;
}

/**
 * File validation
 */
function fileValidation() {
    let ext = $('#questionfile').val().split('.').pop().toLowerCase();
    
    if (ext !== "" && $.inArray(ext, ['png', 'jpg', 'jpeg']) === -1) {
        $("#file_error").text(translations.fileinvalid);
        $("#file_error").show();
        return true;
    }

    return fileSizeValidation('#questionfile', '#file_error');
}

/**
 * Check file size.
 * @param {String} inputId 
 * @param {String} errorSpan 
 */
function fileSizeValidation(inputId, errorSpan) {
    let fileInput = $(inputId);
    let maxSize = fileInput.data('max-size');
    let errorMsg = translations ? translations.fileinvalidsize : 'Invalid file size';

    if (fileInput.get(0).files.length) {
        let fileSize = fileInput.get(0).files[0].size != 0 ? (fileInput.get(0).files[0].size / 1000000).toFixed(2) : 0; // in MB
        if (fileSize > maxSize) {
            $(errorSpan).text('*' + errorMsg + ': ' + fileSize + ' MB').show();
            return true;
        }
        $(errorSpan).hide();
    }
    return false;
}

/**
 * Choice form validation.
 * @param {Array} options 
 */
function choiceFormValidation(options) {
    if (options.length < 2) {
        $("#form_error_message").show();
        $("#form_error_message").text('*' + translations.singleoption);
        return true;
    }
    if (hasDuplicates(options)) {
        $("#form_error_message").show();
        $("#form_error_message").text('*' + translations.duplicateoptions);
        return true;
    }
    $("#form_error_message").hide();
    return fileValidation();
}

/**
 * MAtrix form validation.
 * @param {Array} options 
 * @param {Array} values 
 */
function matrixFormValidation(options, values) {
    if (options.length < 2 || values.length < 2) {
        $("#form_error_message").show();
        $("#form_error_message").text('*' + translations.singleoption);
        return true;
    }
    if (hasDuplicates(options)) {
        $("#form_error_message").show();
        $("#form_error_message").text('*' + translations.duplicateoptions);
        return true;
    }
    if (hasDuplicates(values)) {
        $("#form_error_message").show();
        $("#form_error_message").text('*' + translations.duplicateoptionval);
        return true;
    }
    $("#form_error_message").hide();
    return fileValidation();
}

