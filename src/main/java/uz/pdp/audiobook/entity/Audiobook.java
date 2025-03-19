package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE audiobook SET deleted = true WHERE id = ?")
public class Audiobook extends AbsIntegerEntity {

    @Column(nullable = false, unique = true)
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
