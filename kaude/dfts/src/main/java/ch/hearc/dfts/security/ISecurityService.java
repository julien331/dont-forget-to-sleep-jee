package ch.hearc.dfts.security;

public interface ISecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
