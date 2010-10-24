/**
 * Gestion de l'actualisation de la liste des messages
 * @author Adrian Gaudebert - adrian@gaudeber.fr
 * @author Emmanuel Halter
 */
$(document).ready(function(){
    // Actualisation de la liste des messages
    var xslDoc;
    $.get("Messages.xsl", null, function(data) {
        xslDoc = data;
        var xmlDoc;
        function getMessages() {
            $.get("Messages.jsp", null, function(data) {
                xmlDoc = data;
                $.xslt({
                    xml:xmlDoc,
                    xsl:xslDoc,
                    xmlCache:false,
                    callback: function(data) {
                        $("#messages").prepend(data);
                    }
                }, "xml");
            });
        }
        getMessages();
        $.timer(1000, function(){
            getMessages();
        });
    }, "xml");
});
