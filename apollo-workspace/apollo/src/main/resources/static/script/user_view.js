/**
 *  
 * User View script.
 *  
 */

let currentTabIndex = 0;
let currentStep = 1;
let tabs = [];

$(function () {
    tabs = $(".tab").toArray();

    // Show the first tab
    if (groupIndex) currentTabIndex = groupIndex;
    showTab(currentTabIndex);

    // Register step click event
    $(".step").click(() => {
        if (!$(event.target).hasClass("active")) {
            if ($(event.target).attr('id') > currentStep) nextPrev(1);
            else nextPrev(-1);
            currentStep = $(event.target).attr('id');
        }
    });

});

/**
 * Show tab by index
 * @param {Number} n 
 */
function showTab(n) {
    // Show tab
    tabs[n].style.display = "block";

    if (n == 0) {
        $("#prevBtn").css("display", "none");
    } else {
        $("#prevBtn").css("display", "inline");
    }
    if (n == (tabs.length - 1)) {
        if (readonly) $("#nextBtn").css("display", "none");
        $("#nextBtn").html('<span class="btn-inner--text">' + translations.submit + '</span><span class="btn-inner--icon"><i class="fas fa-save"></i></span>');
    } else {
        if (readonly) $("#nextBtn").css("display", "inline");
        $("#nextBtn").html('<span class="btn-inner--text">' + translations.next + '</span><span class="btn-inner--icon"><i class="fas fa-chevron-right"></i></span>');
    }
    fixStepIndicator(n)
}

/**
 * Go next/back
 * @param {Number} n 1 for next group, -1 for previous group
 */
function nextPrev(n) {
    // Set tab style
    tabs[currentTabIndex].style.display = "none";
    currentTabIndex += n;

    if (currentTabIndex >= tabs.length) {
        $("#form").submit();
        return false;
    }

    showTab(currentTabIndex);
}

/**
 * Removes the "active" class of all steps...
 * @param {Number} n 
 */
function fixStepIndicator(n) {
    
    let i, step = $(".step");
    for (i = 0; i < step.length; i++) {
        step[i].className = step[i].className.replace(" active", "");
    }
    //... and adds the "active" class to the current step:
    step[n].className += " active";
}

/**
 * Open login modal for private survey.
 * @param {String} url 
 * @param {Object} survey 
 * @param {String} modal_id 
 */
function openLoginModal(url, survey, modal_id){
    $.ajax({
        type: "GET",
        url: url,
        data: {id: survey},
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $("#modal_holder").html(response);
            $(modal_id).modal("show");
        },
        error: function (e) {
            console.log('ERROR', e);
        }
    })
}

/**
 * Do login.
 * @param {String} url 
 */
function surveyLogin(url){
    event.preventDefault();
    let data = $('#login_form').serializeToJSON();
    
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        cache: false,
        timeout: 600000,
        success: (response) => {
            if(response.status === "OK"){
                window.location.replace(response.msg);
            }
            if(response.status === "ERROR"){
                $('#modal_login').modal('hide');
                $('#answered_modal').modal('show');
            }
        },
        error: (response) =>{
            if(response.responseJSON.status === "ERROR"){
                $("#error_alert").show();
            }else{
                console.log("ERROR: ", response.responseJSON);
            }
        }
    })
}

/**
 * Set the value of other choice as the textbox input
 */
function otherChoiceValueSetter(textbox) {
    $("#" + textbox.name)[0].value = textbox.value;
}