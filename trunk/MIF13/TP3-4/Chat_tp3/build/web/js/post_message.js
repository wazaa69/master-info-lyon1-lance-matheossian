/**
 * Gestion de l'envoi d'un nouveau message au serveur
 * @author Adrian Gaudebert - adrian@gaudeber.fr
 * @author Emmanuel Halter
 */
$(document).ready(function() {
    $("#msg_submit").click(function(e) {
        e.preventDefault();
        var submitInput = $(this);
        var textInput = $("#msg_text");
        submitInput.attr("disabled", "disabled");
        $.ajax({
            type: "POST",
            url: "Stockage.jsp",
            data: ({message: textInput.val()}),
            error: function(data) {
                $("#error").html(data);
                submitInput.removeAttr("disabled");
                textInput.val("");
            },
            success: function() {
                submitInput.removeAttr("disabled");
                textInput.val("");
            }
        });
    });
});