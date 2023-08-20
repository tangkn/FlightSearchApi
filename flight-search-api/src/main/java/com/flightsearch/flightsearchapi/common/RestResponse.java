package com.flightsearch.flightsearchapi.common;

public record RestResponse<T>(int status, String message, T payload) {
}
