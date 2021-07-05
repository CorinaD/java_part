package com.example.demo.access;

import com.example.demo.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface BuildAccessRepository
        extends JpaRepository<BuildAccess, Long> {

    Optional<BuildAccess> findByAppUser(String appUser);

    @Transactional
    @Modifying
    @Query("UPDATE BuildAccess b " +
            "SET b.exitTime = ?2 " +
            "WHERE b.appUser = ?1")
    int updateExitTime(Long appUser,
                          LocalDateTime exitTime);

    @Transactional
    @Modifying
    @Query("SELECT b from BuildAccess b " +
            "WHERE b.appUser = ?1")
    Collection<BuildAccess> findAllEntriesById(AppUser appUser);

    @Transactional
    @Modifying
    @Query("SELECT b.entryTime from BuildAccess b " +
            "WHERE b.appUser = ?1 " +
            " AND b.exitTime = 0")
   Long getEntryTime(AppUser appUser);

    @Transactional
    @Modifying
    @Query("UPDATE BuildAccess b " +
            "SET b.exitTime = ?1 " +
            "WHERE b.appUser = ?2 " +
            " AND  b.exitTime = 0")
    void punchOut(Long exitTime, AppUser appUser);
}
