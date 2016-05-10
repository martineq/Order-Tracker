package com.example.uriel.ordertracker.App.Model;

import static java.lang.Thread.sleep;

interface Command {
    void execute();
}

public class Timer implements Runnable {

    private int time;
    private Thread thread;
    private Command command;

    Timer(int time, Command command) {
        this.time = time;
        this.thread = null;
        this.command = command;
    }

    public void start() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void stop() {
        this.thread.interrupt();
    }

    @Override
    public void run() {
        try {
            while (true) {
                command.execute();
                sleep(time);
            }
        }

        catch (InterruptedException e) {}
    }
}
