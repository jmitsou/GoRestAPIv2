package careerdevs.API.GoRest.API.v2.models;

public class GoRestAPIResponse {

    public class GoRestResponse {
        private Object meta;
        private Object data;

        public Object getMeta() {
            return meta;
        }

        public void setMeta(Object meta) {
            this.meta = meta;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
