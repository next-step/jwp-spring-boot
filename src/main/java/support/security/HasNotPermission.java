package support.security;

import support.ErrorCoded;

public class HasNotPermission extends RuntimeException implements ErrorCoded {
    private static final String ERROR_CODE = "has.not.permission";

    private String resourceType;
    private Object resourceId;
    private Object accessorId;

    public HasNotPermission(String resourceType, Object resourceId, Object accessorId) {
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.accessorId = accessorId;
    }

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

    @Override
    public Object[] getArgs() {
        return new Object[] { resourceType, resourceId, accessorId };
    }
}
