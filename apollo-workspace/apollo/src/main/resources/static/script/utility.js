const SPINNER = '<div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status"><span class="sr-only"> Loading...</span></div>';

const ALERT_ERROR = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'
    +'<div id="alert_text_error">'
    +'<span class="alert-group-icon text-"><i class="fas fa-exclamation-triangle"></i></span>&nbsp;'
    +'</div>'
    +'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
    +'<span aria-hidden="true">&times;</span>'
    +'</button>'
    +'</div>';

const ALERT_WARNING = '<div class="alert alert-warning alert-dismissible fade show" role="alert">'
    + '<div id="alert_text_warning">'
    + '<span class="alert-group-icon text-"><i class="fas fa-info-circle"></i></span>&nbsp;'
    + '</div>'
    + '<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
    + '<span aria-hidden="true">&times;</span>'
    + '</button>'
    + '</div>';

const INPUT_SUMMARY = '<div class="card mb-3 border shadow-none">'
    + '<div class="px-3 py-3">'
    + '<div class="row align-items-center">'
    + '<div class="col-auto">'
    + '<span id="input_icon">[icon]</span>'
    + '</div>'
    + '<div class="col ml-n2 pl-4">'
    + '<p id="text_value" class="card-text small text-muted"></p>'
    + '</div>'
    + '<div class="col-auto">'
    + '<span id="counter_value" class="text-muted"></span>'
    + '</div>'
    + '</div>'
    + '</div>'
    + '</div>';

const CHOICE_SUMMARY = '<div class="row mb-2">'
    + '<div class="col-10">'
    + '<div class="progress-wrapper">'
    + '<span id="option_name" data-toggle="tooltip" class="progress-label text-muted text-sm"></span>'
    + '<div class="progress mt-1 mb-2" style="height: 5px;">'
    + '<div id="option_progress_bar" class="progress-bar bg-purple" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%;"></div>'
    + '</div>'
    + '</div>'
    + '</div>'
    + '<div class="col-2 align-self-end text-right">'
    + '<span id="option_progress_value" data-toggle="tooltip" class="h6 mb-0"></span>'
    + '</div>'
    + '</div>';

const MATRIX_SUMMARY = '<div class="card mb-3 border shadow-none">'
    +'<div class="px-3 py-3">'
    +'<div class="row align-items-center">'
    +'<div class="col ml-n2">'
    +'<div class="row">'
    +'<div class="col">'
    +'<span id="option_name" class="card-text text-sm text-muted"></span>'
    +'</div>'
    +'</div>'
    +'<div id="option_list_elements"></div>'
    +'</div>'
    +'</div>'
    +'</div>'
    +'</div>';

const MATRIX_OPTION_ELEM = '<div class="row">'
    +'<div class="col text-left small">'
    +'<span id="option_text" class="d-block text-sm"></span>'
    +'<span id="option_value" class="h6 mb-0"></span>'
    +'</div>'
    +'</div>';

const TAG_BEDGE = '<span class="tag badge badge-primary">[email]<span data-role="remove"></span></span>';