package com.album.janez.response;

public class Response<T> {

    public enum State {LOADING, ERROR, DATA}

    private State state;
    private T data;
    private Throwable error;

    public Response() {
        this.state = State.LOADING;
    }

    public Response(T data) {
        this.data = data;
        this.state = State.DATA;
    }

    public Response(Throwable error) {
        this.error = error;
        this.state = State.ERROR;
    }

    public T getData() {
        return data;
    }

    public State getState() {
        return state;
    }

    public boolean isSuccessful() {
        return state == State.DATA && data != null;
    }
}
