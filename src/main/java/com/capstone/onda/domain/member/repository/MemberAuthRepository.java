package com.capstone.onda.domain.member.repository;

import com.capstone.onda.global.security.dto.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAuthRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findRefreshByAccessToken(String accessToken);
}
