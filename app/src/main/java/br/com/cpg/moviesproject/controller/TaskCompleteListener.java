package br.com.cpg.moviesproject.controller;


public interface TaskCompleteListener<T> {
    void onTaskComplete(T result);
}
