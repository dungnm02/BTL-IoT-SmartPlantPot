package dungnm243.webserver.services;

import dungnm243.webserver.models.User;
import dungnm243.webserver.repos.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String createUser(User user) {
        String errorMessage = "";
        if (isUsernameExist(user.getUsername())) {
            errorMessage += "Username đã tồn tại\n";
        }
        if (isEmailExist(user.getEmail())) {
            errorMessage += "Email đã tồn tại\n";
        }
        if (user.getPassword().length() < 8) {
            errorMessage += "Mật khẩu phải có ít nhất 8 ký tự\n";
        }
        if (errorMessage.isBlank()) {
            userRepo.saveAndFlush(user);
            return "";
        } else {
            return errorMessage;
        }
    }

    public User checkLogin(User checkUser) {
        User user = userRepo.findByUsername(checkUser.getUsername());
        if (user == null || !user.getPassword().equals(checkUser.getPassword())) {
            return null;
        } else {
            return user;
        }
    }


    private boolean isUsernameExist(String username) {
        return userRepo.findByUsername(username) != null;
    }

    private boolean isEmailExist(String email) {
        return userRepo.findByEmail(email) != null;
    }
}
