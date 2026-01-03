package hust.project.freshfridge.application.service;

import hust.project.freshfridge.domain.constant.ActionType;
import hust.project.freshfridge.domain.entity.SystemLog;
import hust.project.freshfridge.domain.repository.ISystemLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemLogService {

    private final ISystemLogRepository systemLogRepository;

    @Async
    public void log(Long userId, ActionType action, String entityType, Long entityId, 
                    HttpServletRequest request, Map<String, Object> additionalDetails) {
        Map<String, Object> details = additionalDetails != null ? new HashMap<>(additionalDetails) : new HashMap<>();

        SystemLog log = SystemLog.builder()
                .userId(userId)
                .action(action.name())
                .entityType(entityType)
                .entityId(entityId)
                .details(details)
                .ipAddress(getClientIpAddress(request))
                .userAgent(request != null ? request.getHeader("User-Agent") : null)
                .createdAt(LocalDateTime.now())
                .build();

        systemLogRepository.save(log);
    }

    public void log(Long userId, ActionType action, String entityType, Long entityId) {
        log(userId, action, entityType, entityId, null, null);
    }

    public void log(Long userId, ActionType action) {
        log(userId, action, null, null, null, null);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        if (request == null) return null;

        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
