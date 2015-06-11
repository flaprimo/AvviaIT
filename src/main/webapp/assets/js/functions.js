(function(){

    var $nomeSkillLabel;

    function onDocumentLoad() {
        // Lista Skill
        $nomeSkillLabel = $("#nomeSkillLabel").find("input")
        $("#skillSchooser").on("click", "li", function() {
            var value = $(this).html();
            $nomeSkillLabel.val(value);
        });
    }

    $(window).ready(onDocumentLoad);

})()