package simple.task.planner.dto;

public class ErrorBody {
    private String message;
    private long timestamp;

    public ErrorBody(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorBody() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
