function recupererMessages() {
    loadXMLAsynchroneously('GET', 'messages.jsp', null, 'listeMessages');
    document.location = '#EnBas';
    setTimeout("recupererMessages()",5000);
}

function stockerMessage() {
    var message = document.getElementById("saisie");
    sendRequestAsynchroneously('POST', 'messages.jsp', 'message=' + message.value);
    message.value = "";
}