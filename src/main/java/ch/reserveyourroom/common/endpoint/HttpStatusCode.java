package ch.reserveyourroom.common.endpoint;

/**
 * Status code representing the HTTP code response sent to the client.
 */
public enum HttpStatusCode {

    // SUCCESS
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NON_AUTHORITATIVE(203),
    NO_CONTENT(204),

    // CLIENT ERROR
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    REQUEST_TIMEOUT(408),
    CONFLICT(409),
    GONE(410),
    PRECONDITION_FAILED(412),
    PAYLOAD_TOO_LARGE(413),
    UNSUPPORTED_MEDIA_TYPE(415),
    RANGE_NOT_SATISFIABLE(416),
    UNPROCCESSABLE_ENTITY(422),
    UPGRADE_REQUIRED(426),
    PRECONDITION_REQUIRED(428),

    // SERVER ERROR
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    SERVICE_UNAVAILABLE(503);

    private int statusCode;

    HttpStatusCode(int code) {
        this.statusCode = code;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
