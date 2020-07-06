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

    if(n === 1 && !validateRequiredQuestions()){
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

/**
 * Validate required questions.
 */
function validateRequiredQuestions(){
    const currentTab = $(".tab:visible")
    const requiredFields = currentTab.find($(":input[required]"));
    let valid = true;
    let requiredQuestions = {};

    for (let reqField of requiredFields) {
        requiredQuestions[reqField.id.split('.')[0]] !== undefined ? requiredQuestions[reqField.id.split('.')[0]].push(reqField) : requiredQuestions[reqField.id.split('.')[0]] = [reqField];
    }

    for (let question in requiredQuestions) {
        if (!checkAnswer(requiredQuestions[question])) {
            let errorElement = '';
            if (question.includes('multiMatrix')) {
                errorElement = '#Error_' + question.split('_')[0];
            } else if (question.includes('singleMatrix')) {
                errorElement = '#Error_' + question.split('_')[0];
            } else {
                errorElement = '#Error_' + question;
            }
            $(errorElement).css({ 'display': 'block' });
            valid = false;
        }
    }

    return valid;
}

/**
 * Check question answers.
 * @param {Array} fields 
 */
function checkAnswer(fields) {
    if (fields[0].type === "checkbox" || fields[0].type === "radio") {
        const allFields = $("input[name='"+fields[0].name+"']");
        if(fields[0].type === "radio") return allFields.toArray().some((elem)=> elem.checked && elem.value !== "");
        if(fields[0].type === "checkbox"){
            const checkedFields = allFields.toArray().filter((elem) => elem.checked);
            return checkedFields.length > 0 && checkedFields.filter((elem) => elem.value === "").length === 0;
        }
    } else {
        return fields[0].value !== undefined && fields[0].value !== "";
    }
}

/**
 * Opens the login modal for private surveys
 * @param {String} url The url of the GET request
 * @param {Object} survey The private survey in object
 * @param {String} modal_id The id of the login modal
 */
function openLoginModal(url, survey, modal_id){
    $.ajax({
        type: "GET",
        url: url,
        data: {id: survey},
        dataType: 'html',
        contentType: 'text/html; charset=UTF-8',
        cache: false,
        timeout: 10000,
        success: function (response) {
            $("#modal_holder").html(response);
            $(modal_id).modal("show");
        },
        error: function (e) {
            console.error('ERROR', e);
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
            console.info('[SUCCESS]::[Message]:Login success.');
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
                console.error("ERROR: ", response.responseJSON);
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