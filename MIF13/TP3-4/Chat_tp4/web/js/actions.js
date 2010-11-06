function recupererMessages() {
    loadXMLAsynchroneously('GET', 'messages.jsp', null, 'messages');
    document.location = '#dernierMessage';
    setTimeout("recupererMessages()",5000);
}

function stockerMessage(nomChamps) {
    var message = document.getElementById(nomChamps);
    sendRequestAsynchroneously('POST', 'messages.jsp', 'message=' + message.value);
    message.value = "";
}