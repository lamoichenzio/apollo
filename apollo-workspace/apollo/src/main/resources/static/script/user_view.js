/**
 *  
 * User View script.
 *  
 */

let currentTabIndex = 0;
let currentStep = 1;
let tabs = [];

$(function () {
    // Hide all errors
    $("span.error-message").hide();

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
    if (n === 0) {
        $("#prevBtn").css("display", "none");
    } else {
        $("#prevBtn").css("display", "inline");
    }
    if (n === (tabs.length - 1)) {
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
    // Hide errors
    $("span.error-message").toArray().forEach(elem => elem.style.display = "none");

    if(n === 1 && !validateFields()){
        return false;
    }

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

function validateFields(){
    const currentTab = $(".tab:visible")
    const requiredFields = currentTab.find($(":input[required]"));
    const errors = currentTab.find($("span.error-message"));
    let valid = true;
    for(let i = 0; i<requiredFields.length; i++){
        if(!checkField(requiredFields[i], requiredFields)){
            errors.filter((j)=>requiredFields[i].id === errors[j].id).style.display="inline"
            // errors[i].style.display="inline";
            valid = false;
        }
    }
    return valid;
}

function checkField(field, requiredFields){
    let valid;
    if(field.type === "checkbox" || field.type === "radio"){
        valid = requiredFields.filter(i => requiredFields[i].name === field.name).toArray().some((elem)=>elem.checked === true);
    }else{
        valid = field.value !== undefined && field.value !== "";
    }
    return valid;
}

/**
 * Opens the login modal for private surveys
 * @param url The url of the GET request
 * @param survey The private survey in object
 * @param modal_id The id of the login modal
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

function surveyLogin(url, form){
    event.preventDefault();
    let data = $(form).serializeToJSON();
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
                console.log("ERROR: "+responseJSON);
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