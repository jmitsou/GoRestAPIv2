package careerdevs.API.GoRest.API.v2.models;

public class GoRestResponseMulti {

    private GoRestMeta meta;
    private GoRestAPIUser[] data;

    public GoRestMeta getMeta() {
        return meta;
    }

    public void setMeta(GoRestMeta meta) {
        this.meta = meta;
    }

    public GoRestAPIUser[] getData() {
        return data;
    }

    public void setData(GoRestAPIUser[] data) {
        this.data = data;
    }
}
