package edu.unicauca.apliweb.persistence.entities;

import edu.unicauca.apliweb.persistence.entities.Compra;
import edu.unicauca.apliweb.persistence.entities.Proveedor;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-12T19:35:53")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile ListAttribute<Producto, Proveedor> proveedorList;
    public static volatile SingularAttribute<Producto, Integer> codProducto;
    public static volatile SingularAttribute<Producto, Date> fechaVencimientoPrd;
    public static volatile SingularAttribute<Producto, Integer> precioPublicoPrd;
    public static volatile SingularAttribute<Producto, Integer> productoCantidad;
    public static volatile SingularAttribute<Producto, String> laboratorio;
    public static volatile ListAttribute<Producto, Compra> compraList;
    public static volatile SingularAttribute<Producto, String> nombreProducto;
    public static volatile SingularAttribute<Producto, Integer> precioCompraPrd;

}