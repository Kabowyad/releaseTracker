package org.neon.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Release Not Found")
public class ResourceNotFoundException extends RuntimeException {

}
