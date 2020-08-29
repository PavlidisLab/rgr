package ubc.pavlab.rdp.model;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = { "uberonId" })
@ToString(of = { "uberonId" })
public abstract class Organ {

    @Column(name = "uberon_id", length = 14)
    private String uberonId;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
