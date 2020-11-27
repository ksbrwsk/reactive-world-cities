package de.ksbrwsk.citiies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("world_city")
public class City {
    @Id
    private Long id;
    private String name;
    private String country;
    private String subcountry;
    private String geonameId;
}
