package com.example.urlshortner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "url_mapping",
        indexes = {
                @Index(name = "idx_short_key", columnList = "short_key")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq_gen")
    @SequenceGenerator(
            name = "url_seq_gen",
            sequenceName = "url_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "long_url", nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(name = "short_key", unique = true, length = 20)
    private String shortKey;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;


    @Column(name = "click_count", nullable = false)
    private Long clickCount = 0L;
}
