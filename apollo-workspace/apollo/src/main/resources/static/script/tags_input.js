/**
 * Script for custom tags input and invitation pool.
 *
 */

$(function () {
    // Tooltip inizialize
    $('[data-toggle="tooltip"]').tooltip();

    getInvitaionPool();
    registerTagAddingEvents();
    registerTagRemoveEvent();
});

/**
 * Register event for removing tag.
 */
function registerTagRemoveEvent() {
    $('span[data-role="remove"]').click(function (event) {
        let val = $(event.target).parent().text();
        emails.splice(emails.findIndex(elem => elem == val), 1);
        $(event.target).parent().remove();
    });
}

/**
 * Register event for adding tag on keypress and input change.
 */
function registerTagAddingEvents() {
    $('#tagsinput').keypress(function (event) {
        if (event.which == 44 || event.which == 13) {
            event.preventDefault();
            let data = $(this).val().split(',');
            addTag(this, data);
        }
    });

    $('#tagsinput').change(function () {
        let data = $(this).val().split(',');
        addTag(this, data);
    });
}

/**
 * Add tag.
 * @param {HTMLElement} context 
 * @param {Array} data 
 */
function addTag(context, data) {
    if (data.length) {
        data.forEach(item => {
            if (item.trim().length && emails.find(email => email == item.trim()) == undefined) {
                let elem = TAG_BEDGE.replace('[email]', item);
                emails.push(item);
                $('#tags_input_container').append($(elem));
            }
        });
        $(context).val('');
        registerTagRemoveEvent();
    }
}

/**
 * Get existing emails in Invitation Poll and addig its to emails array.
 */
function getInvitaionPool() {
    if ($('.tag.badge.badge-primary').toArray().length) {
        $('.tag.badge.badge-primary').each(function () {
            emails.push($(this).text().trim());
        });
    }
}