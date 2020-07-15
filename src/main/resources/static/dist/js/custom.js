/* show file value after file select */
document.querySelector('.custom-input-file').addEventListener('change',function(e){
    var fileName = document.getElementById("questionfile").files[0].name;
    var nextSibling = e.target.nextElementSibling
    nextSibling.innerText = fileName
})
