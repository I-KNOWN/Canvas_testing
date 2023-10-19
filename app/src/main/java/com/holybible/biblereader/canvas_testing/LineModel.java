package com.holybible.biblereader.canvas_testing;

public class LineModel {
    MyPoint startpoint, endpoint;

    public LineModel(MyPoint startpoint, MyPoint endpoint) {
        this.startpoint = startpoint;
        this.endpoint = endpoint;
    }

    public MyPoint getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(MyPoint startpoint) {
        this.startpoint = startpoint;
    }

    public MyPoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(MyPoint endpoint) {
        this.endpoint = endpoint;
    }
}
