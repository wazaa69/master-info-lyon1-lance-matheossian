/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function loadXsl() {
    var xmlDoc;
    $.ajax({
        type: "GET",
        url: "request/time.xsl",
        dataType: "html",
        error: function() {
            $("#messages").append("Can't load XSL");
            xmlDoc = false;
        },
        success: function(data) {
            xmlDoc = data;
        },
        async: false
    });
    return xmlDoc;
}
function loadXml() {
    var xmlDoc = false;
    $.ajax({
        type: "GET",
        url: "request/get_time.jsp",
        dataType: "html",
        error: function() {
            $("#messages").append("Can't load XML");
            xmlDoc = false;
        },
        success: function(data) {
            xmlDoc = data;
        },
        async: false
    });
    return xmlDoc;
}
function displayTime(xmlDoc, xslDoc) {
    $("#clock").xslt({
        xml:xmlDoc,
        xsl:xslDoc,
        xmlCache:false
    });
}
$(document).ready(function(){
    var xsl = loadXsl();
    var xml = false;
    xml = loadXml();
    displayTime(xml, xsl);
    $.timer(1000, function(){
        xml = loadXml();
        displayTime(xml, xsl);
    });
});
