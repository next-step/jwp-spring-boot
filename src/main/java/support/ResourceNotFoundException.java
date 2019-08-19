package support;

public class ResourceNotFoundException extends RuntimeException implements ErrorCoded {
    private static final String ERROR_CODE = "resource.not.found";

    private String resourceType;
    private Object id;

    public ResourceNotFoundException(String resourceType, Object id) {
        this.resourceType = resourceType;
        this.id = id;
    }

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

    @Override
    public Object[] getArgs() {
        return new Object[] { resourceType, id };
    }
}
