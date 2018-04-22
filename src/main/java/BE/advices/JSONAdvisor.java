package BE.advices;

import BE.models.JsonViews;
import BE.security.enums.Privileges;
import com.fasterxml.jackson.annotation.JsonView;
import netscape.security.Privilege;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Final call before serialising response to JSON. Structures response in expected format.
 */


@RestControllerAdvice(value = "BE.controllers")
public class JSONAdvisor implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    private MappingJacksonValue getOrCreateContainer(Object body) {
        return (body instanceof MappingJacksonValue ? (MappingJacksonValue) body : new MappingJacksonValue(body));

    }

    private Boolean isContainer(Object body) {
        return (body instanceof MappingJacksonValue);

    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        Map<String, Object> newBody = new HashMap<>();
        MappingJacksonValue container;


        if (body == null) return null;
        else {
            newBody.put("status", "success");

            if (isContainer(body)){
                container = getOrCreateContainer(body);
                newBody.put("data", container.getValue());
                container.setValue(newBody);
            } else {
                newBody.put("data", body);
                container = getOrCreateContainer(newBody);
            }
        }

        beforeBodyWriteInternal(container, selectedContentType, returnType, request, response);
        return container;
    }


    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

        Class<?> viewClass = JsonViews.UserView.class;
        if(bodyContainer.getSerializationView()!=null){
            viewClass = bodyContainer.getSerializationView();
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            if (authorities.stream().anyMatch(o -> o.getAuthority().equals(Privileges.ADMIN))) {
                viewClass = JsonViews.AdminView.class;
            }
            bodyContainer.setSerializationView(viewClass);
        }
    }
}

