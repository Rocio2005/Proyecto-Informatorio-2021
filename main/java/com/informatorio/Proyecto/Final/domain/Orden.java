package com.informatorio.Proyecto.Final.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*UN CARRITO TIENE UNA ÚNICA ORDEN Y UNA ORDEN PERTENECE A UN ÚNICO CARRITO */
    @OneToOne
    @JoinColumn(name="carrito_id")
    private Carrito carrito;

    /*DETALLE DE LA COMPRA
    @ManyToOne(fetch = FetchType.LAZY)
    Producto producto;
    @NotBlank
    private Integer cantidad;
    @NotBlank
    private BigDecimal precioUnitario;
    @Transient
    private BigDecimal subtotal;*/

    @CreationTimestamp /*¿?*/
    private LocalDateTime fechaDeCreacion;

    @NotBlank
    @Size(min = 2,max = 200)/*para indicar que la observacion no tenga mas de 200 caracteres*/
    private String observacion;

    /*RELACION CON USUARIO:un usuario puede cerrar varios carritos con sus respectivas ordenes,puede tener muchas ordens a su nombre*/
    @ManyToOne(fetch = FetchType.LAZY)/*varias ordenes para un usuario*/
    Usuario usuario;

    /*GET Y SET*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;

    }
    /*
    public List<LineaDeCarrito> getDetalleDeCarrito(){
        return getCarrito().getLineasDeCarrito();
    }*/

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    /*public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }*/

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
