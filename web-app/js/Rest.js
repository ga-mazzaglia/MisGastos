/**
 * Created by gmazzaglia on 21/7/16.
 */
var Rest = {

    BASE_PATH: "/ajax",

    doGet: function(url, callback){
        Rest._request(url, "GET", {}, callback);
    },

    doPost: function(url, args, callback){
        Rest._request(url, "POST", args, callback);
    },

    doDelete: function(url, bodyParams, callback){
        Rest._request(url, "DELETE", bodyParams, callback);
    },

    _request: function(url, method, args, successCallback){
        url = Rest.BASE_PATH + url
        $.ajax({
            url: url,
            type: method,
            data: args,
            dataType: "json",
            //contentType: 'application/json; charset=utf-8',
            success: function(data) {
                successCallback(data);
            },
            error: function(jqXHR, textStatus, errorThrown ) {
                successCallback(jqXHR);
            }
        });
    }
}
