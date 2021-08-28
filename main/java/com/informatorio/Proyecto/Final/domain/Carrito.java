package com.informatorio.Proyecto.Final.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String generadoPor;

    @CreationTimestamp /*¿?*/
    private LocalDateTime fechaDeCreacion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)/*varios carritos para un usuario*/
    private Usuario usuario;

    @Positive
    @Transient
    private BigDecimal total;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)/*un carrito para muchos detalles(lineasdecarrito)*/
    private List<LineaDeCarrito> lineasDeCarrito = new ArrayList<>();

    @OneToOne(mappedBy = "carrito")/*un carrito tiene una orden*/
    private Orden orden;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneradoPor() {
        return generadoPor;
    }

    public void setGeneradoPor(String generadoPor) {
        this.generadoPor = generadoPor;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTotal() {/*método para sumar los subtotales de cada detalle(linea carrito) de cada producto en el carrito*/
        for (int i=0; i< getLineasDeCarrito().size();i++){
            total=getLineasDeCarrito().get(i).getSubtotal().add(total);
        };
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<LineaDeCarrito> getLineasDeCarrito() {
        return lineasDeCarrito;
    }

    public void setLineasDeCarrito(List<LineaDeCarrito> lineasDeCarrito) {
        this.lineasDeCarrito = lineasDeCarrito;
    }
}
