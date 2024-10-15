package bwp.hhn.backend.harmonyhomenetlogic.entity.mainTables;

import bwp.hhn.backend.harmonyhomenetlogic.entity.sideTables.PossessionHistory;
import bwp.hhn.backend.harmonyhomenetlogic.entity.sideTables.UserDocumentPermission;
import bwp.hhn.backend.harmonyhomenetlogic.utils.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Users", indexes = {
        @Index(name = "idx_user_email_unq", columnList = "Email", unique = true)
})
public class User {

    @Id
    @Column(name = "UUID_id")
    private UUID uuidID;

    @Column(name = "First_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "Last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "Email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Role", length = 8)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "Phone_number", nullable = false, length = 11)
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "Create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "Update_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PossessionHistory> possessionHistories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProblemReport> problemReports;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<NotificationType> notificationTypes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Poll> polls;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Vote> votes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Announcement> payments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Topic> topics;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserDocumentPermission> userDocumentPermissions;

}