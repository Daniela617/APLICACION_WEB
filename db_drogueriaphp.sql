/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     11/11/2023 12:08:36 p. m.                    */
/*==============================================================*/


drop table if exists ADMINISTRADOR;

drop table if exists ATENCION_MEDICA;

drop table if exists CATEGORIA;

drop table if exists CLIENTE;

drop table if exists COMPRA;

drop table if exists DETALLE_FACTURA;

drop table if exists DISTRIBUCION;

drop table if exists DOMICILIARIO;

drop table if exists FACTURA;

drop table if exists PRODUCTO;

drop table if exists PROMOCION;

drop table if exists PROVEEDOR;

drop table if exists REGENTE;

drop table if exists REGISTRO_DEVOLUCION;

drop table if exists REPORTE;

drop table if exists ROL;

drop table if exists TIPO_PROMOCION;

drop table if exists USUARIOROL;
drop table if exists TRABAJADOR;
/*==============================================================*/
/* Table: TRABAJADOR                                            */
/*==============================================================*/


create table TRABAJADOR
(
    CC_USUARIO           INT           not null,
    NOMBRE_USUARIO       VARCHAR(15)         not null,
    TEL_USUARIO          VARCHAR(13)         not null,
    LOGIN_USUARIO        VARCHAR(15)         not null,
    CONTRASENIA_USUARIO  VARCHAR(15)         not null,
    PRIMARY KEY (CC_USUARIO)
    
);
/*==============================================================*/
/* Table: ADMINISTRADOR                                         */
/*==============================================================*/
create table ADMINISTRADOR
(
   CC_USUARIO           INT not null,
   SUELDO_ADMIN         numeric(10,0) not null,
   COMISIONES_ADMIN     numeric(10,0) not null,
   primary key (CC_USUARIO)
);


/*==============================================================*/
/* Table: ATENCION_MEDICA                                       */
/*==============================================================*/
create table ATENCION_MEDICA
(
   FECHA_ATMEDICA       date not null,
   CC_USUARIO           INT,
   TIPO_ATMEDICA        varchar(100) not null,
   primary key (FECHA_ATMEDICA)
);

/*==============================================================*/
/* Table: CATEGORIA                                             */
/*==============================================================*/
create table CATEGORIA
(
   COD_CATEGORIA        numeric(3,0) not null,
   NOMBRE_CAT           varchar(50) not null,
   primary key (COD_CATEGORIA)
);

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE
(
   CC_CLIENTE           numeric(10,0) not null,
   FECHA_ATMEDICA       date,
   NOMBRE_CLIENTE       varchar(15) not null,
   DIR_CLIENTE          varchar(15) not null,
   CORREO_CLIENTE       varchar(15) not null,
   primary key (CC_CLIENTE)
);

/*==============================================================*/
/* Table: COMPRA                                                */
/*==============================================================*/
create table COMPRA
(
   ID_COMPRA            numeric(10,0) not null,
   COD_PRODUCTO         numeric(5,0) not null,
   CC_USUARIO           INT,
   FECHA_COMPRA         date not null,
   VALOR_COMPRA         numeric(10,0) not null,
   CNT_PRODUCTOS        numeric(4,0) not null,
   SUBTOTAL_COMPRA      numeric(10,2) not null,
   PRECIO_UNITARIO_COMPRA numeric(10,2) not null,
   primary key (ID_COMPRA)
);

/*==============================================================*/
/* Table: DETALLE_FACTURA                                       */
/*==============================================================*/
create table DETALLE_FACTURA
(
   ID_VENTA             numeric(10,0) not null,
   COD_PROMOCION        numeric(4,0) not null,
   COD_PRODUCTO         numeric(5,0) not null,
   CANTIDAD_PRODUCTOS   numeric(4,0) not null,
   SUBTOTAL_FACTURA     numeric(10,2) not null,
   PRECIO_UNITARIO_FACTURA numeric(10,2) not null,
   primary key (ID_VENTA, COD_PROMOCION, COD_PRODUCTO)
);

/*==============================================================*/
/* Table: DISTRIBUCION                                          */
/*==============================================================*/
create table DISTRIBUCION
(
   COD_PROVEEDOR        numeric(10,0) not null,
   COD_PRODUCTO         numeric(5,0) not null,
   primary key (COD_PROVEEDOR, COD_PRODUCTO)
);

/*==============================================================*/
/* Table: DOMICILIARIO                                          */
/*==============================================================*/
create table DOMICILIARIO
(
   CC_DOMICILIARIO      numeric(15,0) not null,
   NOMBRE_DOMICILIARIO  varchar(20) not null,
   TEL_DOMICILIARIO     numeric(15,0) not null,
   DIR_DOMICILIARIO     varchar(20) not null,
   CORREO_DOMICILIARIO  varchar(20) not null,
   primary key (CC_DOMICILIARIO)
);

/*==============================================================*/
/* Table: FACTURA                                               */
/*==============================================================*/
create table FACTURA
(
   ID_VENTA             numeric(10,0) not null,
   CC_CLIENTE           numeric(10,0) not null,
   CC_DOMICILIARIO      numeric(15,0),
   CC_USUARIO           INT not null,
   FECHA_VENTA          date not null,
   VALOR_VENTA          numeric(10,0) not null,
   primary key (ID_VENTA)
);

/*==============================================================*/
/* Table: PRODUCTO                                              */
/*==============================================================*/
create table PRODUCTO
(
   COD_PRODUCTO         numeric(5,0) not null,
   COD_DEVOLUCION       numeric(15,0),
   COD_CATEGORIA        numeric(3,0) not null,
   NOMBRE_PRODUCTO      varchar(15) not null,
   PRECIO_PUBLICO_PRD   numeric(7,0) not null,
   PRECIO_COMPRA_PRD    numeric(7,0) not null,
   FECHA_VENCIMIENTO_PRD date not null,
   PRODUCTO_CANTIDAD    numeric(4,0) not null,
   LABORATORIO          varchar(15) not null,
   primary key (COD_PRODUCTO)
);

/*==============================================================*/
/* Table: PROMOCION                                             */
/*==============================================================*/
create table PROMOCION
(
   COD_PROMOCION        numeric(4,0) not null,
   COD_PRODUCTO         numeric(5,0) not null,
   FECHAINICIO_PROMO    date not null,
   FECHAFIN_PROMO       date not null,
   primary key (COD_PROMOCION, COD_PRODUCTO, FECHAINICIO_PROMO)
);

/*==============================================================*/
/* Table: PROVEEDOR                                             */
/*==============================================================*/
create table PROVEEDOR
(
   COD_PROVEEDOR        numeric(10,0) not null,
   NOMBRE_PROVEEDOR     varchar(20) not null,
   TEL_PROVEEDOR        varchar(13) not null,
   CORREO_PROVEEDOR     varchar(45) not null,
   DIR_PROVEEDOR        varchar(45) not null,
   primary key (COD_PROVEEDOR)
);

/*==============================================================*/
/* Table: REGENTE                                               */
/*==============================================================*/
create table REGENTE
(
   CC_USUARIO           INT not null,
   CERTIFICADO_REGENTE  longblob not null,
   EPS_REGENTE          varchar(15) not null,
   primary key (CC_USUARIO)
);

/*==============================================================*/
/* Table: REGISTRO_DEVOLUCION                                   */
/*==============================================================*/
create table REGISTRO_DEVOLUCION
(
   COD_DEVOLUCION       numeric(15,0) not null,
   CC_CLIENTE           numeric(10,0) not null,
   CC_USUARIO           INT not null,
   FECHA_DEVOLUCION     date not null,
   MOTIVO_DEVOLUCION    text not null,
   primary key (COD_DEVOLUCION)
);

/*==============================================================*/
/* Table: REPORTE                                               */
/*==============================================================*/
create table REPORTE
(
   FECHA_REPORTE        date not null,
   CC_USUARIO           INT not null,
   REGISTRO_REPORTE     numeric(3,3) not null,
   TIPO_REPORTE         varchar(11) not null,
   primary key (FECHA_REPORTE)
);

/*==============================================================*/
/* Table: ROL                                                   */
/*==============================================================*/
create table ROL
(
   ID_ROL               numeric(2,0) not null,
   NOMBRE_ROL           varchar(15) not null,
   primary key (ID_ROL)
);

/*==============================================================*/
/* Table: TIPO_PROMOCION                                        */
/*==============================================================*/
create table TIPO_PROMOCION
(
   COD_PROMOCION        numeric(4,0) not null,
   VALOR_PROMOCION      numeric(8,0) not null,
   DESCRIPCION          varchar(100) not null,
   primary key (COD_PROMOCION)
);

/*==============================================================*/
/* Table: USUARIOROL                                            */
/*==============================================================*/
create table USUARIOROL
(
   CC_USUARIO          INT not null,
   ID_ROL               numeric(2,0) not null,
   FECHAINICIOVIGENCIA  date not null,
   FECHAFINVIGENCIA     date not null,
   primary key (CC_USUARIO, ID_ROL, FECHAINICIOVIGENCIA)
);
ALTER TABLE ADMINISTRADOR ADD CONSTRAINT FK_ADMINISTRADOR_USUARIO FOREIGN KEY (CC_USUARIO)
      REFERENCES TRABAJADOR(CC_USUARIO) on delete restrict on update restrict;

ALTER TABLE REGENTE ADD CONSTRAINT FK_REGENTE_USUARIO FOREIGN KEY (CC_USUARIO)
      REFERENCES TRABAJADOR(CC_USUARIO) on delete restrict on update restrict;

ALTER TABLE USUARIOROL ADD CONSTRAINT FK_USUARIOROL_USUARIO FOREIGN KEY (CC_USUARIO)
      REFERENCES TRABAJADOR(CC_USUARIO) on delete restrict on update restrict;

alter table ATENCION_MEDICA add constraint FK_EJECUTA foreign key (CC_USUARIO)
      references REGENTE (CC_USUARIO) on delete restrict on update restrict;

alter table CLIENTE add constraint FK_RECIBE2 foreign key (FECHA_ATMEDICA)
      references ATENCION_MEDICA (FECHA_ATMEDICA) on delete restrict on update restrict;

alter table COMPRA add constraint FK_DETALLE_COMPRA foreign key (COD_PRODUCTO)
      references PRODUCTO (COD_PRODUCTO) on delete restrict on update restrict;

alter table COMPRA add constraint FK_REALIZA foreign key (CC_USUARIO)
      references ADMINISTRADOR (CC_USUARIO) on delete restrict on update restrict;

alter table DETALLE_FACTURA add constraint FK_DETALLE_FACTURA1 foreign key (COD_PRODUCTO)
      references PRODUCTO (COD_PRODUCTO) on delete restrict on update restrict;

alter table DETALLE_FACTURA add constraint FK_DETALLE_FACTURA2 foreign key (ID_VENTA)
      references FACTURA (ID_VENTA) on delete restrict on update restrict;

alter table DETALLE_FACTURA add constraint FK_DETALLE_FACTURA3 foreign key (COD_PROMOCION)
      references TIPO_PROMOCION (COD_PROMOCION) on delete restrict on update restrict;

alter table DISTRIBUCION add constraint FK_DISTRIBUCION foreign key (COD_PRODUCTO)
      references PRODUCTO (COD_PRODUCTO) on delete restrict on update restrict;

alter table DISTRIBUCION add constraint FK_DISTRIBUCION2 foreign key (COD_PROVEEDOR)
      references PROVEEDOR (COD_PROVEEDOR) on delete restrict on update restrict;

alter table FACTURA add constraint FK_DESPACHA foreign key (CC_DOMICILIARIO)
      references DOMICILIARIO (CC_DOMICILIARIO) on delete restrict on update restrict;

alter table FACTURA add constraint FK_REALIZA2 foreign key (CC_CLIENTE)
      references CLIENTE (CC_CLIENTE) on delete restrict on update restrict;

alter table FACTURA add constraint FK_REGISTRA foreign key (CC_USUARIO)
      references REGENTE (CC_USUARIO) on delete restrict on update restrict;

alter table PRODUCTO add constraint FK_CONTIENE3 foreign key (COD_DEVOLUCION)
      references REGISTRO_DEVOLUCION (COD_DEVOLUCION) on delete restrict on update restrict;

alter table PRODUCTO add constraint FK_SE_CLASIFICA foreign key (COD_CATEGORIA)
      references CATEGORIA (COD_CATEGORIA) on delete restrict on update restrict;

alter table PROMOCION add constraint FK_CUENTA_CON foreign key (COD_PROMOCION)
      references TIPO_PROMOCION (COD_PROMOCION) on delete restrict on update restrict;

alter table PROMOCION add constraint FK_SE_APLICA foreign key (COD_PRODUCTO)
      references PRODUCTO (COD_PRODUCTO) on delete restrict on update restrict;

alter table REGISTRO_DEVOLUCION add constraint FK_REGISTRA2 foreign key (CC_USUARIO)
      references REGENTE (CC_USUARIO) on delete restrict on update restrict;

alter table REGISTRO_DEVOLUCION add constraint FK_SOLICITA foreign key (CC_CLIENTE)
      references CLIENTE (CC_CLIENTE) on delete restrict on update restrict;

alter table REPORTE add constraint FK_REGISTRA3 foreign key (CC_USUARIO)
      references REGENTE (CC_USUARIO) on delete restrict on update restrict;

alter table USUARIOROL add constraint FK_TIENE4 foreign key (ID_ROL)
      references ROL (ID_ROL) on delete restrict on update restrict;
/*
alter table USUARIOROL add constraint FK_TIENE5 foreign key (CC_USUARIO)
      references REGENTE (CC_USUARIO) on delete restrict on update restrict;

alter table USUARIOROL add constraint FK_TIENE6 foreign key (CC_USUARIO)
      references ADMINISTRADOR (CC_USUARIO) on delete restrict on update restrict;

*/