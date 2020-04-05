package ch.hearc.dfts.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}