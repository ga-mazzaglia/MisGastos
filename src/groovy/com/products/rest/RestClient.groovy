package com.products.rest

import grails.converters.JSON
import org.apache.http.HttpStatus
import org.apache.http.entity.ContentType
import javax.net.ssl.HttpsURLConnection

class RestClient {

    String url
    String uriPath
    ContentType contentType
    Map headers
    Map query
    int retries
    Map bodyParams

    RestClient(String url, String uriPath, Map headers, ContentType contentType, Map query, Map bodyParams, int retries) {
        this.url = url
        this.uriPath = uriPath
        this.contentType = contentType
        this.headers = headers
        this.query = query
        this.retries = retries
        this.bodyParams = bodyParams
    }

    public Map request(String method) {
        def result = [status: HttpStatus.SC_SERVICE_UNAVAILABLE, response: null];

        println "----- RestClient -----";
        println "URL: ${this.url}";
        println "PATH: ${this.uriPath}";
        println "HEADER: ${this.headers}";
        println "----------------------"

        try {
            String url = this.url + this.uriPath;
            if(this.query && this.query.size()){
                url += "?" + this.query.collect { k,v -> "$k=$v" }.join('&')
            }
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("content-type", "application/json");
            headers.each { key, value ->
                con.setRequestProperty(key, value);
            }
            con.setDoOutput(true);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (200 <= responseCode && responseCode <= 299) {
                br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            } else {
                br = new BufferedReader(new InputStreamReader((con.getErrorStream())));
            }
            String body = br.text;
            def json = JSON.parse(body);
            println json
            result = [
                    status      : con.getResponseCode(),
                    response    : json

            ];
        } catch (Exception ex) {
            ex.printStackTrace();
            result = [
                    success     : false,
                    response    : null
            ];
        }

        if (!result.success && retries > 0) {
            retries--;
            request(method);
        }

        return result;
    }

    def doGet() {
        return request("GET");
    }

    def doPost() {
        return request("POST");
    }

    def doPut() {
        return request("PUT");
    }

    def doDelete() {
        return request("DELETE");
    }
}