package com.products.rest

import org.apache.http.entity.ContentType

class RestBuilder {
    String url
    String uriPath
    ContentType contentType = ContentType.APPLICATION_JSON
    Map headers
    Map query
    int retries = 0
    Map bodyParams = [:]

    def withUrl(String url) {
        this.url = url
        return this
    }

    def withContentType(ContentType contentType) {
        this.contentType = contentType
        return this
    }

    def withHeaders(Map headers) {
        this.headers = headers
        return this
    }

    def withQuery(Map query) {
        this.query = query
        return this
    }

    def withRetries(int retries) {
        this.retries = retries
        return this
    }

    def withUriPath(String uriPath) {
        this.uriPath = uriPath
        return this
    }

    def withBodyParams(Map bodyParams) {
        this.bodyParams = bodyParams
        return this
    }

    def build() {
        return new RestClient(url, uriPath, headers, contentType, query, bodyParams, retries)
    }
}