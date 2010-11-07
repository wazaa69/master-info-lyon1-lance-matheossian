function recupererMessages() {
    loadXMLAsynchroneously('get', 'messages.jsp', null, 'tabMessagesContenu');
    document.location = '#dernierMessage';
    setTimeout("recupererMessages()",5000);
}

function stockerMessage(nomChamps) {
    var message = document.getElementById(nomChamps);
    sendRequestAsynchroneously('post', 'messages.jsp', 'message=' + message.value);
    message.value = "";
}