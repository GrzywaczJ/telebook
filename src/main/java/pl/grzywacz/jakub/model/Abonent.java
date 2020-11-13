package pl.grzywacz.jakub.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Abonent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String sex;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adressID", referencedColumnName = "id")
    private Address address;
}
