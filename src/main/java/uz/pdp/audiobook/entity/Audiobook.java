package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "audiobook",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_active_audiobook_title",
                columnNames = {"title", "deleted"}
        )
)

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE audiobook SET deleted = true WHERE id = ?")
public class Audiobook extends AbsIntegerEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer duration;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Attachment coverImage;

    @ManyToOne
    private Category category;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Attachment bookAttachment;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private AudioFile audioFile;
}
