package ash.advanced.app.v2;

import ash.advanced.trace.TraceStatus;
import ash.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {
    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);

            return "OKAY";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 정상 흐름으로 처리되는 것을 방지하기 위해 예외를 꼭 던져주어야 함
        }
    }
}
