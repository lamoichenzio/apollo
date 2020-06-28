/**
 * Form validation script.
 *
 */

$(function () {

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
});

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

function fileValidation() {
    let ext = $('#questionfile').val().split('.').pop().toLowerCase();
    if (ext !== "" && $.inArray(ext, ['png', 'jpg', 'jpeg']) == -1) {
        $("#file_error").text(translations.fileinvalid);
        $("#file_error").show();
        return true;
    }
    return false;
}

function choiceFormValidation(options) {
    if (options.length < 2) {
        $("#form_error_message").show();
        $("#form_error_message").text('*Single option not allowed.');
        return true;
    }
    if (hasDuplicates(options)) {
        $("#form_error_message").show();
        $("#form_error_message").text('*Duplicate options.');
        return true;
    }
    return fileValidation();
}

function matrixFormValidation(options, values) {
    if (options.length < 2 || values.length < 2) {
        $("#form_error_message").show();
        $("#form_error_message").text('*Single option/value not allowed.');
        return true;
    }
    if (hasDuplicates(options)) {
        $("#form_error_message").show();
        $("#form_error_message").text('*Duplicate options.');
        return true;
    }
    if (hasDuplicates(values)) {
        $("#form_error_message").show();
        $("#form_error_message").text('*Duplicate range values.');
        return true;
    }
    return fileValidation();
}

