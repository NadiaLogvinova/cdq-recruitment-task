package com.cqd.pf.service;

public interface AsyncJob {

    void start(String id, Object params);

}
