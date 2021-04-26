package ar.com.alianza.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "satellite")
@Data
public class Satellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double x;
    private Double y;

    @Builder
    public Satellite(String name, Double x, Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Satellite() {
    }
}
