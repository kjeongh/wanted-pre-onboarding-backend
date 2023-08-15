package onboarding.wanted.backend.domain.user.entity;

import lombok.*;
import onboarding.wanted.backend.domain.user.constant.UserRole;
import onboarding.wanted.backend.global.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@SQLDelete(sql = "UPDATE user SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is NULL")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) default 'ROLE_USER'")
    private UserRole userRole;
}
