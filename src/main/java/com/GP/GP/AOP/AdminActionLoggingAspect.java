package com.GP.GP.AOP;

import com.GP.GP.entities.AdminActionLog;
import com.GP.GP.repository.AdminActionLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AdminActionLoggingAspect {

    @Autowired
    private AdminActionLogRepository logRepository;

    @Pointcut("execution(* com.GP.GP.roles.admin.controller..*(..))")
    public void adminControllerMethods() {}

    @After("adminControllerMethods()")
    public void logAdminAction(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String actionType = methodName.startsWith("add") ? "ADD" :
                methodName.startsWith("delete") ? "DELETE" :
                        methodName.startsWith("update") ? "UPDATE" : null;

        if (actionType == null) return; // ignore other actions

        Object[] args = joinPoint.getArgs();

        // Fetch current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : "UNKNOWN";

        // Extract entity name from controller class name
        String controllerClass = joinPoint.getTarget().getClass().getSimpleName();
        String entityName = controllerClass.replace("Controller", ""); // e.g., BuildingController → Building

        AdminActionLog log = new AdminActionLog();
        log.setActionType(actionType);
        log.setEntityName(entityName);
        log.setEntityId(extractEntityIdFromArgs(args));
        log.setPerformedBy(username);
        log.setDescription(methodName + " called with args: " + Arrays.toString(args));

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