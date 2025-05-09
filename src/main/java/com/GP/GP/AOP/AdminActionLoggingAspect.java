package com.GP.GP.AOP;

import com.GP.GP.entities.AdminActionLog;
import com.GP.GP.repository.AdminActionLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AdminActionLoggingAspect {

    @Autowired
    private AdminActionLogRepository logRepository;

    @Pointcut("execution(* com.GP.GP.roles.admin.controller..*(..)) || execution(* com.GP.GP.roles.user.controller.ComplaintController.*(..))")
    public void adminControllerMethods() {
    }

    @After("adminControllerMethods()")
    public void logAdminAction(JoinPoint joinPoint) {
        // Get HTTP method
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return;

        HttpServletRequest request = attributes.getRequest();
        if ("GET".equalsIgnoreCase(request.getMethod())) return; // Ignore GET

        String methodName = joinPoint.getSignature().getName();
        String actionType = methodName.startsWith("add") ? "ADD" :
                methodName.startsWith("delete") ? "DELETE" :
                        methodName.startsWith("update") ? "UPDATE" : "ACTION";

        Object[] args = joinPoint.getArgs();

        // Fetch authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : "UNKNOWN";

        // Extract entity name
        String controllerClass = joinPoint.getTarget().getClass().getSimpleName();
        String entityName = controllerClass.replace("Controller", "");

        // Create a human-readable description
        String readableDescription = switch (actionType) {
            case "ADD" -> "Created a new " + entityName;
            case "UPDATE" -> "Updated an existing " + entityName;
            case "DELETE" -> "Deleted a " + entityName;
            default -> methodName + " executed on " + entityName;
        };
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }

        AdminActionLog log = new AdminActionLog();
        log.setActionType(actionType);
        log.setEntityName(entityName);
        log.setEntityId(extractEntityIdFromArgs(args));
        log.setPerformedBy(username + ",ip " + ipAddress );
        log.setDescription(readableDescription);
//       ظظ log.setIpAddress(ipAddress);

        logRepository.save(log);
    }

    private String extractEntityIdFromArgs(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Integer) {
                return arg.toString(); // You can improve this by checking parameter names or types
            }
        }
        return "N/A";
    }
}