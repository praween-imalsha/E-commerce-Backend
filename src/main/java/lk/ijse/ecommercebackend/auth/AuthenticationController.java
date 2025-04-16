package lk.ijse.ecommercebackend.auth;


import lk.ijse.ecommercebackend.repo.UserRepository;
import lk.ijse.ecommercebackend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Autowired
    private UserRepository userRepository;

    // User Registration - now includes email verification step
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    // User Authentication
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    // Email Verification Endpoint
    @PostMapping("/verify")
    public ResponseEntity<AuthenticationResponse> verifyEmail(
            @RequestParam String email,
            @RequestParam String code
    ) {
        return ResponseEntity.ok(service.verifyEmail(email, code));
    }

    // Update User Profile
    @PutMapping("/update/{id}")
    public ResponseEntity<AuthenticationResponse> updateUser(@PathVariable int id, @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.updateUser(id, request));
    }

    // Get User Profile
    @GetMapping("/update/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    // Delete User
    @DeleteMapping("/update/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return ResponseEntity.ok("User account has been deleted successfully.");
    }
    @DeleteMapping("/unverified")
    public ResponseEntity<String> deleteUnverifiedUser(@RequestParam String email) {
        try {
            boolean isDeleted = service.deleteUnverifiedUser(email);
            if (isDeleted) {
                return ResponseEntity.ok("User deleted due to unverified email");
            } else {
                return ResponseEntity.status(404).body("User not found or already verified");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

}
