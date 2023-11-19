package com.github.balcon.venue.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.balcon.venue.servlet.JsonLazyFilter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@FieldNameConstants
public class Band extends AbstractNamedEntity {
    @Builder.Default
    @OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = JsonLazyFilter.class)
    @JsonManagedReference
    private List<Musician> musicians = new ArrayList<>();

    public void addMusician(Musician musician) {
        musicians.add(musician);
    }
}
