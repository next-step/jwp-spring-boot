package support.security;

import support.ErrorCoded;

public class LoginRequiredException extends Exception implements ErrorCoded {
    private static final long serialVersionUID = 7598014391903123780L;

    private static final String ERROR_CODE = "login.required";

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
