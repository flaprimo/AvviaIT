(function(){

    var $nomeSkillLabel;

    function onDocumentLoad() {
        // Lista Skill
        $nomeSkillLabel = $("#nomeSkillLabel").find("input");
        $("#skillSchooser").on("click", "li", function() {
            var value = $(this).html();
            $nomeSkillLabel.val(value);
            $("input.button").click();
        });
        $("#addSkillForm").click(function() {
            $nomeSkillLabel.val("");
        });
        $(".showRiscontri").click(function() {
            $(this).hide().parent().find('.riscontro').show();
        });
    }

    $(window).ready(onDocumentLoad);

})();