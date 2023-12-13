package edu.unicauca.apliweb.persistence.entities;

import edu.unicauca.apliweb.persistence.entities.Administrador;
import edu.unicauca.apliweb.persistence.entities.Producto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-12T19:35:53")
@StaticMetamodel(Compra.class)
public class Compra_ { 

    public static volatile SingularAttribute<Compra, Date> fechaCompra;
    public static volatile SingularAttribute<Compra, Producto> codProducto;
    public static volatile SingularAttribute<Compra, Integer> cntProductos;
    public static volatile SingularAttribute<Compra, Integer> subtotalCompra;
    public static volatile SingularAttribute<Compra, Administrador> ccUsuario;
    public static volatile SingularAttribute<Compra, Integer> idCompra;
    public static volatile SingularAttribute<Compra, Integer> valorCompra;
    public static volatile SingularAttribute<Compra, Integer> precioUnitarioCompra;

}