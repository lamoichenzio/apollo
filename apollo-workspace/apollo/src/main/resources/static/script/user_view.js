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
    // Hide tab
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