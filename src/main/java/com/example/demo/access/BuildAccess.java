package com.example.demo.access;

import com.example.demo.appuser.AppUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity

public class BuildAccess {
    @SequenceGenerator(
            name = "access_sequence",
            sequenceName = "access_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "access_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private Long entryTime = 0l;
    @Column(nullable = false)
    private Long exitTime = 0l;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public BuildAccess(Long entryTime, AppUser appUser) {
        this.entryTime = entryTime;
        this.appUser = appUser;
    }
}

