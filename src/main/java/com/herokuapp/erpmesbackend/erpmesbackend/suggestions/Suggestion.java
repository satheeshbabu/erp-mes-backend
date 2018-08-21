package com.herokuapp.erpmesbackend.erpmesbackend.suggestions;

import com.herokuapp.erpmesbackend.erpmesbackend.employees.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Phase phase;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToOne
    private Employee author;

    @Column(nullable = false)
    @OneToMany
    //@JoinColumn(name = "recipients_id")
    private List<Employee> recipients;

    @Column(nullable = false)
    private LocalDateTime creationTime;

    public Suggestion(String name, String description, Employee author, List<Employee> consignees) {
        this.phase = Phase.REPORTED;
        this.name = name;
        this.description = name;
        this.author = author;
        this.creationTime = LocalDateTime.now();
    }

    public boolean checkIfDataEquals(Suggestion suggestion) {
        return name.equals(suggestion.getName()) &&
                description.equals(suggestion.getDescription()) &&
                author.checkIfDataEquals(suggestion.getAuthor()) &&
                creationTime.isEqual(suggestion.getCreationTime());
    }
}