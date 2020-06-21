/**
 * 
 * Utility js file.
 * 
 */

/**
 * SPINNER
 */
const SPINNER = '<div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status"><span class="sr-only"> Loading...</span></div>';
const LOAD_SPINNER = '<div id="load_spinner" class="spinner-border text-success" role="status"><span class="sr-only"> Loading...</span></div>';

/**
 * ALERT
 */
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

/**
 * ANSWER AGGREGATE
 */
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

/**
 * EMAIL TAGS
 */
const TAG_BEDGE = '<span class="tag badge badge-primary">[email]<span data-role="remove"></span></span>';

/**
 * INVITATION MODAL
 */
const INVITATION_CONFIRM = '<div class="modal fade" id="modal-invitation-confirm" tabindex="-1" role="dialog" aria-hidden="true">'
    +'<div class="modal-dialog modal-dialog-centered modal" role="document">'
    +'<div class="modal-content">'
    +'<div class="modal-body">'
    +'<div class="row">'
    +'<div class="col-12">'
    +'<span id="send_email_confirmation" class="form-control-label">Pubblicando il sondaggio invierai la mail agli utenti invitati. Confermare?</span>'
    +'</div>'
    +'<div class="col-12 mt-2">'
    +'<span id="send_email_error" class="text-xs font-weight-bold text-danger hidden">Non è presente nessun indirizzo mail.</span>'
    +'</div>'
    +'<div class="col-12 py-2"><div class="loader"></div></div>'
    +'</div>'
    +'</div>'
    +'<div class="modal-footer">'
    +'<button id="cancel" type="button" data-dismiss="modal" class="btn btn-sm btn-link text-danger"Cancel></button>'
    +'<button id="publish_invitation_submit" type="submit" translate="yes" class="btn btn-sm btn-primary rounded-pill">Send</button>'
    +'</div>'
    +'</div>'
    +'</div>'
    +'</div>';