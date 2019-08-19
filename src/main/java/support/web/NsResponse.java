package support.web;

public class NsResponse {
    private final boolean success;

    private final Object data;

    public NsResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public static NsResponse success(Object data) {
        return new SuccessResponse(data);
    }

    public static NsResponse fail(String errorMessage) {
        return new FailureResponse(null, errorMessage);
    }

    public static NsResponse fail(Object data, String errorMessage) {
        return new FailureResponse(data, errorMessage);
    }

    private static class SuccessResponse extends NsResponse {
        public SuccessResponse(Object data) {
            super(true, data);
        }

        @Override
        public String toString() {
            return "SuccessResponse{" +
                    "success=" + isSuccess() +
                    ", data=" + getData() +
                    '}';
        }
    }

    private static class FailureResponse extends NsResponse {
        private String errorMessage;

        public FailureResponse(Object data, String errorMessage) {
            super(false, data);
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public String toString() {
            return "FailureResponse{" +
                    "success=" + isSuccess() +
                    ", data=" + getData() +
                    ", errorMessage='" + errorMessage + '\'' +
                    '}';
        }
    }
}
