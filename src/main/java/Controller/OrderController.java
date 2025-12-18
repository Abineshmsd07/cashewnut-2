package Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techhaven.service.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request, Principal connectedUser) {
        // connectedUser.getName() returns the email from the JWT Token
        orderService.placeOrder(connectedUser.getName(), request);
        return ResponseEntity.ok("Order placed successfully");
    }
}
