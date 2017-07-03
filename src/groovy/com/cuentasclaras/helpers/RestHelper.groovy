package com.cuentasclaras.helpers

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

/**
 * Created by gmazzaglia on 2/7/17.
 */
class RestHelper {
    String url
    String uriPath
    ContentType contentType
    Map kibanaErrorTags
    Map headers
    Map query
    int retries = 0
    Map bodyParams
    int timeout

    RestHelper(String url, ContentType contentType, Map kibanaErrorTags, Map headers, Map query, int retries, Map bodyParams, String uriPath, int timeout) {
        this.url = url
        this.uriPath = uriPath
        this.contentType = contentType
        this.kibanaErrorTags = kibanaErrorTags
        this.headers = headers
        this.query = query
        this.retries = retries
        this.bodyParams = bodyParams
        this.timeout = timeout
    }

    def request(Method method) {
        def result = [:]

        try {
            def http = new HTTPBuilder(url);

            if (headers) {
                http.setHeaders(headers)
            }

            http.request(method, contentType) { req ->
                if (method == Method.POST || method == Method.PUT) {
                    body = bodyParams
                }
                if (uriPath) {
                    uri.path = uriPath
                }
                if (query) {
                    uri.query = query
                }

                if (timeout > 0) {
                    req.getParams().setParameter("http.connection.timeout", timeout);
                    req.getParams().setParameter("http.socket.timeout", timeout);
                }

                response.success = { resp, reader ->
                    result = [
                            success: true,
                            status : resp.responseBase.statusLine.statusCode,
                            reader : reader
                    ]
                }
                response.failure = { resp, reader ->
                    result = [
                            success: false,
                            status : resp.responseBase.statusLine.statusCode,
                            reader : reader
                    ]
                }
            }
        } catch (Exception ex) {
            result = [
                    success: false,
                    reader : [
                            error  : ex.getClass().toString(),
                            message: ex.getMessage()
                    ]
            ]
            ex.printStackTrace()
        }

        if (!result.success && retries > 0) {
            retries--
            result = request(method)
        }
        return result
    }

    def doGet() {
        return request(Method.GET)
    }

    def doPost() {
        return request(Method.POST)
    }

    def doPut() {
        return request(Method.PUT)
    }

    def doDelete() {
        return request(Method.DELETE)
    }
}
