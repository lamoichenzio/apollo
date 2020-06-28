/**
 * 
 * Utility js file.
 * 
 */

/**
 * SPINNER
 */
const SPINNER_PRIMARY = '<div class="spinner-primary spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status"><span class="sr-only"> Loading...</span></div>';
const SPINNER_SUCCESS = '<div class="spinner-success spinner-border text-success" role="status"><span class="sr-only"> Loading...</span></div>';

/**
 * EMAIL TAGS
 */
const TAG_BEDGE = '<span class="tag badge badge-primary">[email]<span data-role="remove"></span></span>';


/**
 * ALERTS
 */

 /**
  * Show alert message on the page.
  * @param {String} type it can assume error, warning, info values 
  * @param {String} title 
  * @param {String} message 
  */
function showAlert(type, title, message) {
    let icon;
    let classList;

    if (type === 'error') {
        icon = '<i class="fas fa-exclamation"></i>';
        classList = 'alert-danger'
    } else if (type === 'warning') {
        icon = '<i class="fas fa-exclamation-triangle"></i>';
        classList = 'alert-warning';
    } else if (type === 'info') {
        icon = '<i class="fas fa-info"></i>';
        classList = 'alert-info';
    }

    return '<div class="alert ' + classList + ' alert-dismissible fade show" role="alert">'
        + '<span class="alert-group-icon text-">' + icon + '</span>&nbsp;'
        + '<strong>' + title + '</strong>&nbsp; ' + message
        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
        + '<span aria-hidden="true">&times;</span>'
        + '</button>'
        + '</div>';
}

/**
 * INVITATIONS
 */

 /**
  * Get invitation modal html element.
  * @param {Object} text 
  */
function getInvitationConfirmModal(text) {
    return '<div class="modal fade" id="modal-invitation-confirm" tabindex="-1" role="dialog" aria-hidden="true">'
        + '<div class="modal-dialog modal-dialog-centered modal" role="document">'
        + '<div class="modal-content">'
        + '<div class="modal-body">'
        + '<div class="row">'
        + '<div class="col-12">'
        + '<span class="form-control-label">' + text.sendInvitationConfirm + '</span>'
        + '</div>'
        + '<div class="col-12 mt-2">'
        + '<span id="send_email_error" class="text-xs font-weight-bold text-danger hidden">' + text.noemailaddress + '</span>'
        + '</div>'
        + '<div class="col-12 py-2"><div class="loader"></div></div>'
        + '</div>'
        + '</div>'
        + '<div class="modal-footer">'
        + '<button id="cancel" type="button" data-dismiss="modal" class="btn btn-sm btn-link text-danger">' + text.cancel + '</button>'
        + '<button id="publish_invitation_submit" type="submit" translate="yes" class="btn btn-sm btn-primary rounded-pill">' + text.send + '</button>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>';
}

/**
* ANSWER AGGREGATE FUNCTIONS
*/

/**
 * Get icon for input summery html element.
 * @param {String} type 
 */
function getInputSummaryIcon(type) {
    if (type == 'TEXT') {
        return '<i class="fas fa-align-left fa-2x"></i>';
    } else if (type == 'NUMBER') {
        return '<i class="fas fa-calculator fa-2x"></i>';
    } else if (type == 'DATE') {
        return '<i class="fas fa-calendar-alt fa-2x"></i>';
    } else {
        return '<i class="fas fa-question fa-2x"></i>';
    }
}

/**
 * Get input summary html element.
 * @param {String} icon A String representing html element for an icon
 * @param {String} label 
 * @param {Number} value 
 */
function getInputSummary(icon, label, value) {
    return '<div class="card mb-3 border shadow-none">'
        + '<div class="px-3 py-3">'
        + '<div class="row align-items-center">'
        + '<div class="col-auto"><span>' + icon + '</span></div>'
        + '<div class="col ml-n2 pl-4"><p  class="card-text small text-muted">' + label + '</p></div>'
        + '<div class="col-auto"><span class="text-muted">' + value + '</span></div>'
        + '</div>'
        + '</div>'
        + '</div>';
}

/**
 * Get choice summary html element.
 * @param {String} label 
 * @param {Number} value 
 */
function getChoiceSummary(label, value) {
    return '<div class="row mb-2">'
        + '<div class="col-10">'
        + '<div class="progress-wrapper">'
        + '<span data-toggle="tooltip" class="progress-label text-muted text-sm">' + label + '</span>'
        + '<div class="progress mt-1 mb-2" style="height: 5px;">'
        + '<div class="progress-bar bg-purple" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:' + (value.toString().length > 5 ? value.toFixed(2) : value) +'%;"></div>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '<div class="col-2 align-self-end text-right">'
        + '<span data-toggle="tooltip" title="' + label + '" class="h6 mb-0">' + (value.toString().length > 5 ? value.toFixed(2) : value) + '</span>'
        + '</div>'
        + '</div>';
}

/**
 * Get matrix summary html element
 * @param {String} label
 * @param {Array} values
 */
function getMatrixSummary(label, values) {
    let matrixOptionElems = '';

    for (let val of values) {
        matrixOptionElems += '<div class="col text-left small">'
            + '<span class="d-block text-sm">' + val.text + '</span>'
            + '<span class="h6 mb-0">' + val.value + '</span>'
            + '</div>';
    }

    return '<div class="card mb-3 border shadow-none">'
        + '<div class="px-3 py-3">'
        + '<div class="row align-items-center">'
        + '<div class="col ml-n2">'
        + '<div class="row">'
        + '<div class="col"><span class="card-text text-sm text-muted">' + label + '</span></div>'
        + '</div>'
        + '<div class="row"> ' + matrixOptionElems + '</div>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>';
}