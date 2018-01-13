package com.nikitagordia.aplay.Managers;

/**
 * Created by root on 13.01.18.
 */

public class PairKeepManager {

    private int w, h;

    public PairKeepManager() {
        this.w = -1;
        this.h = -1;
    }

    public boolean equals(PairKeepManager pair) {
        return ((pair.w == w) && (pair.h == h));
    }

    public void set(int w, int h) {
        this.w = w;
        this.h = h;
    }
}
