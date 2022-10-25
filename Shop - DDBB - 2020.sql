-------------------------------
-- Base de datos "TandF_TOOMNOOK"    ---
-- Asignatura: Bases de datos --
-- Sara Grela Carrera --
-- Pablo Izquierdo González --
-- Carlos Jimeno Miguel --
-- Esmeralda Madrazo Quintana --

-- Creación BD
----------------------------------
use master;
go

if exists(select * from sys.databases where name='TandF_TOOMNOOK')
  drop database TandF_TOOMNOOK
go

create database TandF_TOOMNOOK;
go

use TandF_TOOMNOOK;
go

------------------------------------------------------------------------------
------------------------------------Tablas------------------------------------
------------------------------------------------------------------------------

--- Tabla tipoObjeto. Indica si el objeto es un arma (0), armadura (1) o una poción (2).
create table tipoObjeto(
idtipoobjeto int not null,
nombre varchar(8) not null,
constraint pk_idtipoobjeto primary key (idtipoobjeto),
check (idtipoobjeto between 0 and 2)
)
go

--- Tabla material. Indica qué material es.
create table material(
idmaterial int not null identity(1, 1),
nombre varchar(40) not null unique,
constraint pk_idmaterial primary key (idmaterial)
)
go

--- Tabla arma. Indica qué tipo de armas es.
create table arma(
idarma int not null identity(1,1),
nombre varchar(30) not null unique,
constraint pk_arma primary key (idarma)
)
go

--- Tabla efecto. Indica qué efectos pueden tener las pociones.
create table efecto(
idefecto int not null identity(1,1),
nombre varchar(30) not null unique,
constraint pk_efecto primary key (idefecto)
)
go

-- Tabla objeto. Indica qué objeto es, su nombre, su material principal, nivel que tiene, su precio,
-- el tiempo que lleva fabricarlo y si es un arma (0) o una poción (2), de qué tipo es y qué efecto tiene respectivamente.
create table objeto(
idobjeto int not null identity(1, 1),
nombre varchar(20) not null unique,
nivel int not null default 0,
precio smallmoney not null default 0,
diasCreacion int not null default 1,
idtipoobjeto int not null, --- TODO: checkear que se rellene el tipoarma o el efecto cuando corresponda
idarma int null,
idefecto int null,
constraint pk_objeto primary key(idobjeto),
constraint fk_objeto_tipoObjeto foreign key (idtipoobjeto) references tipoObjeto(idtipoobjeto),
constraint fk_objeto_idarma foreign key (idarma) references arma(idarma),
constraint fk_objeto_idefecto foreign key (idefecto) references efecto(idefecto),
check (precio > 0),
check (nivel >= 0),
check (diasCreacion >= 1),
)
go

--- Tabla materialObjeto. Indica los materiales secundarios de un objeto y cuántas unidades se necesitan de él.
create table materialObjeto(
idmaterial int not null,
idobjeto int not null,
unidades int not null default 0,
constraint pk_materialObjeto primary key (idmaterial, idobjeto),
constraint fk_materialObjeto_material foreign key (idmaterial) references material(idmaterial),
constraint fk_materialObjeto_objeto foreign key (idobjeto) references objeto(idobjeto),
check (unidades >= 0)
)
go

-- Tabla tienda. Indica qué tienda es, cómo se llama y cuánto dinero tiene.
create table tienda(
idtienda int not null identity(1, 1),
nombre varchar(20) not null unique,
dinero money not null default 0,
constraint pk_tienda primary key(idtienda),
check (dinero >= 0)
)
go

-- Tabla objetosTienda. Indica qué objetos tiene cada tienda y cuántos tiene de ellos.
create table objetoTienda(
idtienda int not null,
idobjeto int not null,
unidades int not null default 0,
constraint pk_objetoTienda primary key(idtienda, idobjeto),
constraint fk_tiendaObjeto_objeto foreign key (idobjeto) references objeto(idobjeto),
constraint fk_tiendaObjeto_tienda foreign key (idtienda) references tienda(idtienda),
)
go

-- Tabla materialTienda. Indica los materiales que posee la tienda y cuánto dispone de cada uno.
create table materialTienda(
idtienda int not null,
idmaterial int not null,
unidades int not null default 0,
constraint pk_materialTienda primary key (idtienda, idmaterial),
constraint fk_materialTienda_tienda foreign key (idtienda) references tienda(idtienda),
constraint fk_materialTienda_material foreign key (idmaterial) references material(idmaterial),
check (unidades >= 0)
)
go

-- Tabla forja. Indica a qué tienda pertenece.
create table forja(
idforja int not null identity(1, 1),
idtienda int not null,
constraint pk_forja primary key(idforja),
constraint fk_forja_tienda foreign key (idtienda) references tienda(idtienda)
)
go

-- Tabla usuario. Indica su nombre de usuario, el nivel que tiene, su dinero y su fecha de registro. 
create table usuario(
idusuario int  not null identity(1,1),
nombreUsuario varchar(30) not null unique,
dinero money not null default 0,
nivel int not null default 0,
fechaRegistro datetime not null default getdate(),
constraint pk_usuario primary key(idusuario),
check (dinero >= 0),
)
go

-- Tabla socio. Indica quiénes son socios de las forjas. Recoge la fecha en la que han pasado a ser socios.
create table socio(
idsocio int not null identity(1,1),
idusuario int not null unique,
fechaSocio datetime not null default getdate(), --- controlar que sea >= que fechaRegistroUsuario
constraint pk_idsocio primary key (idsocio),
constraint fk_socio_idusuario foreign key (idusuario) references usuario(idusuario)
)
go

--- Tabla materialSocio. Indica los materiales que posee el socio y cuánto dispone de cada uno.
create table materialSocio(
idsocio int not null,
idmaterial int not null,
unidades int not null default 0,
constraint pk_materialSocio primary key (idsocio, idmaterial),
constraint fk_materialSocio_socio foreign key (idsocio) references socio(idsocio),
constraint fk_materialSocio_material foreign key (idmaterial) references material(idmaterial),
check (unidades >= 0)
)
go

--- Tabla estadistica. Indica las estadísticas que existen.
create table estadistica(
idestadistica int not null identity(1,1),
idtipoobjeto int not null, --- Checkear que si es arma, no se rellenen las de armadura y viceversa.
nombre varchar(30) not null unique,
constraint pk_estadistica primary key (idestadistica),
constraint fk_estadistica_idobjeto foreign key (idtipoobjeto) references tipoObjeto(idtipoobjeto),
)
go

--- Tabla estadisticaObjeto. Indica la estadística que aporta cada objeto.
create table estadisticaObjeto(
idobjeto int not null,
idestadistica int not null,
duracion int not null,
porcentaje decimal(5,2) not null default 0.0,
constraint pk_estadisticaObjeto primary key (idobjeto, idestadistica),
constraint fk_estadisticaObjeto_objeto foreign key (idobjeto) references objeto(idobjeto),
constraint fk_estadisticaObjeto_estadistica foreign key (idestadistica) references estadistica(idestadistica),
check (duracion > 0),
check (porcentaje between 0.0 and 100.00)
)
go

--- Tabla compra. Recoge las compras realizadas por los usuarios, la fecha en la que se realiza, el importe total y en qué tienda
--- se ha realizado.
create table compra(
idcompra int not null identity(1, 1),
idusuario int not null,
idtienda int not null,
fechaCompra datetime not null default getdate(),
precioTotal money default 0,
constraint pk_compra primary key (idcompra),
constraint fk_compra_usuario foreign key (idusuario) references usuario(idusuario),
constraint fk_compra_tienda foreign key (idtienda) references tienda(idtienda),
)
go

--- Tabla lineaCompra. Indica qué objetos se han comprado, cuál es su precio unitario y cuántas unidades.
create table lineaCompra(
idcompra int not null,
idobjeto int not null,
precioUnitario smallmoney not null,
unidades int not null,
constraint pk_lineaCompra primary key (idcompra, idobjeto),
constraint fk_lineaCompra_idcompra foreign key (idcompra) references compra(idcompra),
constraint fk_lineaCompra_idobjeto foreign key (idobjeto) references objeto(idobjeto),
check (precioUnitario > 0),
check (unidades > 0)
)
go

--- Tabla encargo. Recoge el socio que ha hecho el encargo, la fecha de entrega, la fecha en la que se realiza el encargo, a qué forja
-- pertenece, el estado en el que se encuentra y el importe total.
create table encargo(
idencargo int not null identity(1, 1),
idsocio int not null,
idforja int not null,
fechaEntrega datetime not null,
fechaEncargo datetime not null default getdate(),
estado char(15) not null default 'en preparación',
precioTotal money default 0,
constraint pk_encargo primary key (idencargo),
constraint fk_encargo_idsocio foreign key (idsocio) references socio(idsocio),
constraint fk_encargo_idforja foreign key (idforja) references forja(idforja),
check (fechaEntrega > fechaEncargo),
check (estado in ('en preparación', 'terminado', 'entregado'))
)
go

--- Tabla lineaEncargo. Indica qué objetos se han encargado, cuál es su precio unitario y cuántas unidades.
create table lineaEncargo(
idencargo int not null,
idobjeto int not null,
precioUnitario smallmoney not null,
unidades int not null,
constraint pk_lineaEncargo primary key (idencargo, idobjeto),
constraint fk_lineaEncargo_idencargo foreign key (idencargo) references encargo(idencargo),
constraint fk_lineaEncargo_idobjeto foreign key (idobjeto) references objeto(idobjeto),
check (precioUnitario > 0),
check (unidades > 0)
)
go

------------------------------------------------------------------------------
----------------------------------Funciones-----------------------------------
------------------------------------------------------------------------------

CREATE PROCEDURE usp_showerrorinfo
AS
	SELECT ERROR_NUMBER() AS [Numero de Error],
	ERROR_STATE() AS [Estado del Error],
	ERROR_SEVERITY() AS [Severidad del Error],
	ERROR_LINE() AS [Linea],
	ISNULL(ERROR_PROCEDURE(), 'No esta en un proc') AS [Procedimiento],
	ERROR_MESSAGE() AS [Mensaje]
GO


--- 4.1.1
--- Procedimiento que permite la creación de objetos en la Base de Datos.
CREATE OR ALTER PROC creaObjeto @nombre varchar(20), @nivel int, @precio smallmoney, 
@dias int, @idtipoobjeto int, @idarma int, @idefecto int, @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION

		IF EXISTS(select * from objeto where nombre = @nombre)
			begin
				raiserror('Ya existe el objeto.', 16, 1)
			end
	
			INSERT INTO objeto (nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto)
					VALUES (@nombre, @nivel, @precio, @dias, @idtipoobjeto, @idarma, @idefecto);
	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- Procedimiento que permite la inserción de objetos en una tienda.
CREATE OR ALTER PROC insertaObjeto @nombreObjeto varchar(20), @nombreTienda varchar(20), @unidades int, @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION

			DECLARE @idObjeto int
			SELECT @idObjeto = idobjeto FROM objeto where nombre = @nombreObjeto;
			--- Gestionamos si no existe el objeto
			IF NOT EXISTS(select * from objeto where idobjeto = @idObjeto)
			begin
				raiserror('No existe el objeto.', 16, 1)
			end

			DECLARE @idTienda int
			SELECT @idTienda = idtienda FROM tienda where nombre = @nombreTienda;

			--- Gestionamos si no existe la tienda
			IF NOT EXISTS(select * from tienda where nombre = @nombreTienda)
			begin
				raiserror('No existe la tienda.', 16, 1)
			end

			--- Gestionamos si la tienda ya tiene de esos objetos en su catálogo
			IF EXISTS(select * from objetoTienda where idtienda = @idTienda and idobjeto = @idObjeto)
			begin 
				raiserror('La tienda ya dispone de ese objeto en su catálogo.', 16, 1)
			end

			INSERT INTO objetoTienda(idtienda, idobjeto, unidades)
					VALUES (@idTienda, @idObjeto, @unidades);
	COMMIT TRANSACTION
	END TRY
	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- 4.1.2
--- Procedimiento que permite la actualización del precio de los objetos (se hace en toda la Base de Datos
--- porque todas las tiendas se abastecen de un almacén de objetos común).
CREATE OR ALTER PROC actualizaPrecioObjeto @nombre char(20), @precio smallmoney, @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION

		IF NOT EXISTS(select * from objeto where nombre = @nombre)
		 begin
			raiserror('No existe el objeto.', 16, 1)
		end
		UPDATE objeto SET  precio = @precio
	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- 4.1.3
--- Procedimiento que permite la actualización de las unidades de los objetos (se hace a nivel
--- específico de cada tienda).
CREATE OR ALTER PROC actualizaUnidadesObjeto @nombreObjeto char(20), @unidades int, @nombreTienda varchar(20), @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION
		DECLARE @idObjeto int
		SELECT @idObjeto = idobjeto FROM objeto where nombre = @nombreObjeto;
		--- Gestionamos si no existe el objeto
		IF NOT EXISTS(select * from objeto where idobjeto = @idObjeto)
		begin
			raiserror('No existe el objeto.', 16, 1)
		end

		DECLARE @idTienda int
		SELECT @idTienda = idtienda FROM tienda where nombre = @nombreTienda;

		--- Gestionamos si no existe la tienda
		IF NOT EXISTS(select * from tienda where nombre = @nombreTienda)
		begin
			raiserror('No existe la tienda.', 16, 1)
		end

		--- Gestionamos si la tienda no tiene de esos objetos en su catálogo
		IF NOT EXISTS(select * from objetoTienda where idtienda = @idTienda and idobjeto = @idObjeto)
		begin
			raiserror('La tienda no dispone de ese objeto en su catálogo.', 16, 1)
		end

		UPDATE objetoTienda SET  unidades = @unidades
	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- 4.1.4
--- Procedimiento que permite el borrado de objetos. Nota: se introduce el nombre porque el id del objeto no se sabe cuál es. 
CREATE OR ALTER PROC borraObjeto @nombre varchar(20), @error int out
AS 
	BEGIN TRY
	BEGIN TRANSACTION

		IF NOT EXISTS(select * from objeto where nombre = @nombre)
			begin
				raiserror('No existe el objeto.', 16, 1)
			end

		DECLARE @idobjeto int
		SELECT @idobjeto = idobjeto FROM objeto WHERE nombre = @nombre;
		DELETE FROM estadisticaObjeto WHERE idobjeto = @idobjeto;
		DELETE FROM materialObjeto WHERE idobjeto = @idobjeto;
		DELETE FROM lineaCompra WHERE idobjeto = @idobjeto;
		DELETE FROM lineaEncargo WHERE idobjeto = @idobjeto;
		DELETE FROM objeto WHERE idobjeto = @idobjeto;

	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- 4.3
--- Vista que permite la consulta de los objetos del catálogo con sus materiales.
--- Para las armas devuelve su tipo bajo el nombre tipoArma
--- Para las pociones devuelve su efecto bajo el nombre efecto
--- Para las armaduras devuelve los atributos de tipoArma y efecto como nulos

create or alter view muestra_catalogo 
	as
	select o.*, m.nombre as material, a.nombre as tipoArma, NULL as efecto from objeto o
		inner join materialObjeto mo
			inner join material m
				on m.idmaterial = mo.idmaterial
			on mo.idobjeto = o.idobjeto
			inner join arma a
				on a.idarma = o.idarma
	union
	select o.*, m.nombre as material, NULL as tipoArma, e.nombre as efecto from objeto o
		inner join materialObjeto mo
			inner join material m
				on m.idmaterial = mo.idmaterial
			on mo.idobjeto = o.idobjeto
			inner join efecto e
				on e.idefecto = o.idefecto
	union
	select o.*, m.nombre as material, NULL as tipoArma, NULL as efecto from objeto o
		inner join materialObjeto mo
			inner join material m
				on m.idmaterial = mo.idmaterial
			on mo.idobjeto = o.idobjeto
		where o.idtipoobjeto = 1

go

CREATE OR ALTER PROC catalogoTienda @nombreTienda varchar(20), @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION

		DECLARE @idTienda int
		SELECT @idTienda = idtienda FROM tienda WHERE nombre = @nombreTienda;
		--- Gestionamos que no exista la tienda
		IF NOT EXISTS(select * from tienda where idtienda = @idTienda)
		begin
			raiserror('No existe la tienda.', 16, 1)
		end

		SELECT mc.*, ot.idtienda, ot.unidades FROM dbo.muestra_catalogo mc INNER JOIN objetoTienda ot on mc.idobjeto = ot.idobjeto
			WHERE ot.idtienda = @idTienda;

	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- 4.4
--- Procedimiento que permite realizar una nueva compra.
Create or alter proc nuevaCompra @nombreUsuario varchar(100), @nombreObjeto varchar(100), @unidades int, @tienda varchar(20), @error int out
as
	BEGIN TRY
	BEGIN TRANSACTION

			--Comprobamos si existe el usuario
			if (select count(*) from usuario
				where usuario.nombreUsuario = @nombreUsuario) = 0
				begin 
					raiserror ('No existe el usuario.', 16, 1)
				end

			--Comprobamos si existe el objeto
			if (select count(*) from objeto
				where objeto.nombre = @nombreObjeto) = 0
				begin 
					raiserror ('No existe el objeto.', 16, 1)
				end

			--Obtengo id objeto
			declare @idobjeto int
			select @idobjeto = o.idobjeto from objeto o
				where o.nombre = @nombreObjeto

			---Comprobamos si existe la tienda
			if(select count(*) from tienda t
				where t.nombre = @tienda) = 0
				begin
					raiserror ('La tienda no existe.',16,1)
				end

			--Obtengo id tienda
			declare @idtienda int
			select @idtienda = t.idtienda from tienda t
				where t.nombre = @tienda


			--Comprobamos si quedan unidades del objeto
			 if(select o.unidades from objetoTienda o
				where o.idobjeto = @idobjeto) < @unidades
					begin 
						raiserror('No quedan unidades de este objeto.', 16, 1)
					end 

			--Comprobamos si quedan 5 unidades o menos del objeto
			 if(select o.unidades from objetoTienda o
				where o.idobjeto = @idobjeto)  < 6
					begin 
				raiserror('Quedan 5 ud o menos de este objeto, se necesita reposición.', 10, 1) with nowait
					end 

	
			declare @precio smallmoney
			select @precio = o.precio from objeto o
				where o.idobjeto = @idobjeto

			set @precio = @precio * @unidades

			declare @idusuario int
			select @idusuario = u.idusuario from usuario u
				where u.nombreUsuario = @nombreUsuario

			INSERT [dbo].[compra] ([idusuario],[idtienda], [fechaCompra], [precioTotal])
			VALUES (@idusuario, @idtienda, sysdatetime(), @precio)

			declare @idCompra int

			select @idCompra = c.idCompra from compra c 
				where c.idusuario = @idusuario and c.fechaCompra = sysdatetime()

			INSERT [dbo].[lineaCompra] ([idcompra], [idobjeto], [precioUnitario], [unidades])
			VALUES (@idCompra, @idobjeto, @precio, @unidades)	

			--- Le descontamos el dinero de la compra al usuario
			UPDATE usuario
				Set dinero = (dinero - @precio)
					where usuario.idusuario = @idusuario

			--- Reducimos las unidades del objeto en tienda
			UPDATE objetoTienda
				Set unidades = (unidades - @unidades)
					Where objetoTienda.idobjeto = @idobjeto

			--- Aumentamos el dinero de la tienda
			UPDATE tienda
				Set dinero = (dinero + @precio)
					where tienda.nombre = @tienda

	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- Procedimientos que anhade objetos a esa compra.
Create or alter proc anhadirObjetoACompra @nombreUsuario varchar(100), @nombreObjeto varchar(100), @unidades int, @tienda varchar(20), @error int out 
as 
	BEGIN TRY
	BEGIN TRANSACTION

		--Comprobamos si existe el usuario
		if (select count(*) from usuario
			where usuario.nombreUsuario = @nombreUsuario) = 0
			begin
				raiserror ('No existe el usuario.', 16, 1)
			end

		---Obtengo idusuario
		declare @idusuario int
		select @idusuario = u.idusuario from usuario u
			where u.nombreUsuario = @nombreUsuario

		---Obtengo idobjeto
		declare @idobjeto int
		select @idobjeto = idobjeto from objeto 
			where nombre = @nombreObjeto

		--Comprobamos si existe el objeto
			if (select count(*) from objeto
				where objeto.nombre = @nombreObjeto) = 0
				begin 
					raiserror ('No existe el objeto.', 16, 1)
				end

		---Comprobamos si existe la tienda
		if(select count(*) from tienda t
			where t.nombre = @tienda) = 0
			begin
				raiserror ('La Tienda no existe.',16,1)
			end

		--Obtengo id tienda
		declare @idtienda int
		select @idtienda = t.idtienda from tienda t
			where t.nombre = @tienda

		--Comprobamos si existe la compra
			if (select count(*) from compra c
				where c.idusuario = @idusuario and c.idtienda = @idtienda and c.fechaCompra = sysdatetime()) = 0
				begin 
					raiserror ('No existe la compra.', 16, 1)
				end

		--Comprobamos si quedan unidades del objeto
		 if(select o.unidades from objetoTienda o
			where o.idobjeto = @idobjeto) < @unidades
				begin 
					raiserror('No quedan unidades de este objeto.', 16, 1)
				end 

			declare @precio smallmoney

			select @precio = o.precio from objeto o
				where o.idobjeto = @idobjeto

			set @precio = @precio * @unidades	

			declare @idCompra int 
			select @idCompra = c.idCompra from compra c 
				where c.idusuario = @idusuario and idtienda = @idtienda and c.fechaCompra = sysdatetime()

		INSERT [dbo].[lineaCompra] ([idcompra], [idobjeto], [precioUnitario], [unidades])
			VALUES (@idCompra, @idobjeto, @precio, @unidades)


		UPDATE usuario
			Set dinero = (dinero - @precio)
				where usuario.idusuario = @idusuario

		UPDATE objetoTienda
			Set unidades = (unidades - @unidades)
				Where idobjeto = @idobjeto
	

		declare @precioActual smallmoney
		select @precioActual = c.precioTotal from compra c 
			where c.idusuario = @idusuario and c.fechaCompra = sysdatetime()


		declare @precioTotal smallmoney
		set @precioTotal  = @precioActual + @precio

		UPDATE compra 
			set precioTotal = @precioTotal
				where idusuario = @idusuario and idtienda = @idtienda and fechaCompra = sysdatetime()

		UPDATE tienda
			Set dinero = (dinero + @precio)
				where tienda.nombre = @tienda

	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--Gestionamos que no se pueda borrar una compra.
Create or alter trigger borraCompra on compra instead of delete
as begin
	raiserror ('No se puede borrar la compra.', 16, 1)
	rollback transaction
end 
go

--- 4.5
--- Procedimiento que permite hacer un nuevo encargo.
Create or alter proc nuevoEncargo @nombresocio varchar(100), @idforja int, @error int out 
as
	BEGIN TRY
	BEGIN TRANSACTION

		--comprobar que el usuario existe 
		if(select count(*) from usuario u
			where u.nombreUsuario = @nombresocio) = 0
			begin
				raiserror ('El usuario no existe.',16,1)
			end

		--comprobar que el usuario es socio
		if(select count(*) from usuario u
			where u.nombreUsuario = @nombresocio and 
				u.idusuario in (select s.idusuario from socio s)) = 0
			begin
				raiserror ('El usuario no es socio.',16,1)
			end

		--Compruebo que la forja existe
		if(select count(*) from forja f
			where f.idforja = @idforja) = 0
			begin
				raiserror ('La forja no existe.',16,1)
			end

		--obtengo el id del objeto y del usuario(socio)
		declare @idsocio int
	
		select @idsocio = u.idusuario from usuario u
			where u.nombreUsuario = @nombresocio


		--inserto el nuevo encargo
		INSERT [dbo].[encargo] ([idsocio],[idforja],[fechaEntrega], [fechaEncargo], [estado], [precioTotal]) 
			VALUES (@idsocio, @idforja, (getdate() + 1) , getdate(), 'en preparación', 0)

	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

---Ahnadir objeto a encargo
Create or alter proc ahnadirObjetoEncargo @nombresocio varchar(100), @idforja int, @nombreobjeto varchar(100), @unidades int, @error int out 
as
	BEGIN TRY
	BEGIN TRANSACTION

		--comprobar que existe el objeto
		if(select count(*) from objeto o
			where o.nombre = @nombreobjeto) = 0
			begin
				raiserror ('El objeto no existe.',16,1)
			end

		--comprobar que el usuario existe 
		if(select count(*) from usuario u
			where u.nombreUsuario = @nombresocio) = 0
			begin
				raiserror ('El usuario no existe.',16,1)
			end

		--comprobar que el usuario es socio
		if(select count(*) from usuario u
			where u.nombreUsuario = @nombresocio and 
				u.idusuario in (select s.idusuario from socio s)) = 0
			begin
				raiserror ('El usuario no es socio.',16,1)
			end

		--Compruebo que la forja existe
		if(select count(*) from forja f
			where f.idforja = @idforja) = 0
			begin
				raiserror ('La forja no existe.',16,1)
			end

		--obtener idtienda 
		declare @idtienda int

		select @idtienda = f.idtienda from forja f
			where f.idforja = @idforja

		--obtengo el id del objeto y del usuario(socio)
		declare @idsocio int
		declare @idobjeto int

		select @idobjeto = o.idobjeto from objeto o
			where o.nombre = @nombreobjeto
	
		select @idsocio = u.idusuario from usuario u
			where u.nombreUsuario = @nombresocio

		--- Compruebo que el usuario tiene un encargo en preparacion hoy
		if(select count(*) from encargo e  ---busco el id del encargo creado
			where e.idsocio = @idsocio and e.idforja = @idforja and e.fechaEncargo = getdate() and e.estado = 'en preparación') = 0
			begin
				raiserror ('El socio no tiene un encargo en preparacion en esta forja.',16,1)
			end

		--obtengo los parametros del objeto que necesito
		declare @precio smallmoney
		declare @tiempo int

		select @precio = o.precio, @tiempo = o.diasCreacion from objeto o
				where o.idobjeto = @idobjeto

		---Compruebo que tenga dinero para el objeto
		if(select u.dinero from usuario u
			where u.idusuario = @idsocio) < @precio
			begin
				raiserror ('Dinero insuficiente.',16,1)
			end

		---Compruebo que tenga nivel para el objeto
		if(select u.nivel from usuario u
			where u.idusuario = @idsocio) < (select o.nivel from objeto o 
												where o.idobjeto = @idobjeto)
			begin
				raiserror ('El nivel del usuario es insuficiente.',16,1)
			end
	
		--añado el objeto al encargo (linea encargo)
		--para ello necesito saber el id que se ha puesto automaticamente al encargo que he creado
		declare @idencargo int

		select @idencargo = e.idencargo from encargo e  ---busco el id del encargo creado
			where e.idsocio = @idsocio and e.idforja = @idforja and e.fechaEncargo = getdate() and e.estado = 'en preparación'

	
		INSERT [dbo].[lineaEncargo] ([idencargo], [idobjeto],[precioUnitario],[unidades]) VALUES (@idencargo, @idobjeto, @precio, @unidades)
		---actualizo la fecha entrega del encargo y el precio

		UPDATE encargo  
			set fechaEntrega = (fechaEntrega + @tiempo), ---NOTA: CUANDO SE MUESTRE EL TIEMPO DE ENTREGA ES ESTE - 1
					precioTotal = precioTotal + @precio
				where idencargo = @idencargo

		UPDATE usuario
			set dinero = dinero - @precio
				where idusuario = @idsocio

		UPDATE tienda
			set dinero = dinero + @precio
				where idtienda = @idtienda

	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

---Funcion encargoRecogido
Create or alter proc encargoRecogido @nombresocio varchar(100), @idforja int, @fechaEncargo date, @error int out
as
	BEGIN TRY
	BEGIN TRANSACTION

		--identifico al socio
			declare @idsocio int

			select @idsocio = u.idusuario from usuario u
				where u.nombreUsuario = nombreUsuario
	
		---Compruebo que el encargo exista
			if (select count(*) from encargo e
				where e.idsocio = @idsocio and e.idforja = @idforja and e.fechaEncargo = @fechaEncargo) = 0
				begin
					raiserror ('El ususario no tiene encargos en esta forja.',16,1)
				end
		---Compruebo que este terminado
			if (select count(*) from encargo e
				where e.idsocio = @idsocio and e.idforja = @idforja and e.fechaEncargo = @fechaEncargo and e.estado = 'terminado') = 0
				begin
					raiserror ('El encargo no esta terminado.',16,1)
				end
		---Compruebo que no se haya excedido el tiempo de recogida
			if (select count(*) from encargo e
				where e.idsocio = @idsocio and e.idforja = @idforja and e.fechaEncargo = @fechaEncargo 
						and e.estado = 'terminado' and ((e.fechaEntrega + 2) > getdate()) ) = 0
				begin
					raiserror ('Tiempo de recogida excedido.',16,1)
				end

		---identifico el encargo
		declare @idencargo int

		select @idencargo = e.idencargo from encargo e  ---busco el id del encargo
			where e.idsocio = @idsocio and e.idforja = @idforja and e.fechaEncargo = getdate() and e.estado = 'en preparación'

		update encargo 
			set estado = 'entregado', fechaEntrega = getdate()
				where idencargo = @idencargo

	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

---trigger if fecha entrega + 2 días < getdate nos quedamos los objetos
Create or alter trigger trg_fechaEntrega on dbo.encargo after insert, update, delete 
as begin

	declare @tabla table (
		idencargo int
	)
	
	insert into @tabla select e.idencargo from encargo e
		where (DATEDIFF(DAY, e.fechaEntrega, GETDATE()) > 2)
	
	declare @tablaobjetos table (
		idobjeto int,
		unidades int
	)
	
	insert into @tablaobjetos select l.idobjeto, l.unidades from lineaEncargo l
			where l.idencargo in (select * from @tabla)
	
	---suponemos que los objetos fabricados no estan disponibles en la tienda

	update objetoTienda
		set unidades = unidades + (select unidades from @tablaobjetos)
			where idobjeto in (select idobjeto from @tablaobjetos)
end
go

--- Gestionamos que no se pueda borrar un encargo.
Create or alter trigger borraEncargo on encargo instead of delete
as begin
	raiserror ('Los encargos no se pueden borrar', 16, 1)
	rollback transaction
	return
end 
go

--- 4.7.1
--- Procedimiento que permite insertar materiales.
CREATE OR ALTER PROC insertaMaterial @nombre varchar(40), @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION

		IF EXISTS(select * from material where nombre = @nombre)
			begin
				raiserror('Ya existe ese material.', 16, 1)
			end
			INSERT INTO material (nombre) VALUES (@nombre)
	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- Procedimiento que permite borrar materiales.
CREATE OR ALTER PROC borraMaterial @nombre varchar(40), @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION
		IF NOT EXISTS(select * from material where nombre = @nombre)
			begin
				raiserror('No existe ese material.', 16, 1)
				exec dbo.usp_showerrorinfo
			end
		DELETE FROM material WHERE nombre = @nombre;
	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- 4.7.2
--- Procedimiento que permite insertar los materiales de un objeto.
CREATE OR ALTER PROC insertaMaterialObjeto @nombreMaterial varchar(40), @nombreObjeto varchar(20), @unidades int, @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION

		DECLARE @idmaterial int;
		DECLARE @idobjeto int;
		SELECT @idmaterial = idmaterial FROM material WHERE nombre = @nombreMaterial;
		SELECT @idobjeto = idobjeto FROM objeto WHERE nombre = @nombreObjeto;

		--- Gestionamos si no existe el material.
		IF NOT EXISTS(select * from material where idmaterial = @idmaterial)
			begin
				raiserror('No existe el material.', 16, 1)
			end

		--- Gestionamos si no existe el objeto.
		IF NOT EXISTS(select * from objeto where idobjeto = @idobjeto)
			begin
				raiserror('No existe el objeto.', 16, 1)
			end

		--- Gestionamos si ya existe ese material para el objeto
		IF EXISTS(select * from materialObjeto where idmaterial = @idmaterial and idobjeto = @idobjeto)
			begin
				raiserror('Ya existe el material para ese objeto.', 16, 1)
			end
		INSERT INTO materialObjeto (idmaterial, idobjeto, unidades) VALUES (@idmaterial, @idobjeto, @unidades)
	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- 4.7.3
--- Procedimiento que permite insertar los materiales de la tienda.
CREATE OR ALTER PROC insertaMaterialTienda @nombreTienda varchar(20), @nombreMaterial varchar(40), @unidades int, @error int out
AS
	BEGIN TRY
	BEGIN TRANSACTION

		DECLARE @idmaterial int;
		DECLARE @idTienda int;
		SELECT @idmaterial = idmaterial FROM material WHERE nombre = @nombreMaterial;
		SELECT @idTienda = idtienda FROM tienda WHERE nombre = @nombreTienda;

		--- Gestionamos si no existe el material.
		IF NOT EXISTS(select * from material where idmaterial = @idmaterial)
			begin
				raiserror('No existe el material.', 16, 1)
			end

		--- Gestionamos si no existe la tienda.
		IF NOT EXISTS(select * from tienda where idtienda = @idTienda)
			begin
				raiserror('No existe la tienda.', 16, 1)
			end

		--- Gestionamos si ya existe ese material en la tienda.
		IF EXISTS(select * from materialTienda where idmaterial = @idmaterial and idtienda = @idTienda)
			begin
				raiserror('Ya existe el material en la tienda.', 16, 1)
			end
		INSERT INTO materialTienda(idtienda, idmaterial, unidades) VALUES (@idTienda, @idmaterial, @unidades)
	COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		rollback transaction
        exec dbo.usp_showerrorinfo
        set @error = -1 -- si se salta al raiserror se pone @error a -1
        return
	END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	RETURN 
go

--- Procedimiento que permite actualizar las unidades de los materiales de la tienda.
CREATE OR ALTER PROC actualizaMaterialTienda @nombreTienda varchar(20), @nombreMaterial varchar(40), @unidades int, @error int out
AS
BEGIN TRY
	BEGIN TRANSACTION

	DECLARE @idmaterial int;
	DECLARE @idTienda int;
	SELECT @idmaterial = idmaterial FROM material WHERE nombre = @nombreMaterial;
	SELECT @idTienda = idtienda FROM tienda WHERE nombre = @nombreTienda;

	--- Gestionamos si no existe el material.
	IF NOT EXISTS(select * from material where idmaterial = @idmaterial)
		begin
			raiserror('No existe el material.', 16, 1)
		end
	--- Gestionamos si no existe la tienda.
	IF NOT EXISTS(select * from tienda where idtienda = @idTienda)
		begin
			raiserror('No existe la tienda.', 16, 1)
		end
	--- Gestionamos si no existe ese material en la tienda.
	IF NOT EXISTS(select * from materialTienda where idmaterial = @idmaterial and idtienda = @idTienda)
		begin
			raiserror('No existe el material en la tienda.', 16, 1)
		end
	UPDATE materialTienda SET unidades = @unidades;
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
		rollback transaction
		exec dbo.usp_showerrorinfo
		set @error = -1 -- si se salta al raiserror se pone @error a -1
		return;
END CATCH
	SET @error = 0
	RETURN
go

--- Gestionamos que se intente borrar un material de la tienda.
CREATE OR ALTER TRIGGER borraMaterialTienda ON materialTienda INSTEAD OF DELETE
AS 
BEGIN
	DECLARE @idTienda int;
	DECLARE @idMaterial int;
	SELECT @idTienda = idtienda, @idMaterial = idmaterial FROM deleted;

	UPDATE materialTienda SET unidades = 0 WHERE idtienda = @idTienda AND @idMaterial = idmaterial;
	PRINT('Las unidades del objeto se han actualizado a 0.')
END
go

--- 4.7.4
--- Procedimiento que permite insertar los materiales de un socio.
CREATE OR ALTER PROC insertaMaterialSocio @idSocio int, @nombreMaterial varchar(40), @unidades int, @error int out
AS
BEGIN TRY
	BEGIN TRANSACTION

	DECLARE @idmaterial int;
	SELECT @idmaterial = idmaterial FROM material WHERE nombre = @nombreMaterial;

	--- Gestionamos si no existe el material.
	IF NOT EXISTS(select * from material where idmaterial = @idmaterial)
		begin
			raiserror('No existe el material.', 16, 1)
		end
	--- Gestionamos si no existe el socio.
	IF NOT EXISTS(select * from socio where idsocio = @idSocio)
		begin
			raiserror('El usuario no es socio.', 16, 1)
		end
	--- Gestionamos si el socio ya tiene ese material.
	IF EXISTS(select * from materialSocio where idmaterial = @idmaterial and idsocio = @idSocio)
		begin
			raiserror('El socio ya tiene ese material.', 16, 1)
		end
	INSERT INTO materialSocio(idsocio, idmaterial, unidades) VALUES (@idSocio, @idmaterial, @unidades)
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
		rollback transaction
		exec dbo.usp_showerrorinfo
		set @error = -1 -- si se salta al raiserror se pone @error a -1
		return;
END CATCH
	SET @error = 0
	RETURN
COMMIT TRANSACTION
go

--- Procedimiento que permite actualizar las unidades de los materiales que tiene un socio.
CREATE OR ALTER PROC actualizaMaterialSocio @idSocio int, @nombreMaterial varchar(40), @unidades int, @error int out
AS
BEGIN TRY
BEGIN TRANSACTION

	DECLARE @idmaterial int;
	SELECT @idmaterial = idmaterial FROM material WHERE nombre = @nombreMaterial;

	--- Gestionamos si no existe el material.
	IF NOT EXISTS(select * from material where idmaterial = @idmaterial)
		begin
			raiserror('No existe el material.', 16, 1)
		end
	--- Gestionamos si no existe el socio.
	IF NOT EXISTS(select * from socio where idsocio = @idSocio)
		begin
			raiserror('El usuario no es socio.', 16, 1)
		end
	--- Gestionamos si el socio no dispone de ese material.
	IF NOT EXISTS(select * from materialSocio where idmaterial = @idmaterial and idsocio = @idSocio)
		begin
			raiserror('El usuario no dispone de ese material.', 16, 1)
		end
	UPDATE materialSocio SET unidades = @unidades;
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
	rollback transaction
		exec dbo.usp_showerrorinfo
		set @error = -1 -- si se salta al raiserror se pone @error a -1
		return;
END CATCH

	SET @error = 0
	RETURN;
go

--- Gestionamos que se intente borrar un material del socio.
CREATE OR ALTER TRIGGER borraMaterialSocio ON materialSocio INSTEAD OF DELETE
AS 
BEGIN
	DECLARE @idSocio int;
	DECLARE @idMaterial int;
	SELECT @idSocio = idsocio, @idMaterial = idmaterial FROM deleted;

	UPDATE materialSocio SET unidades = 0 WHERE idsocio = @idSocio AND @idMaterial = idmaterial;
	PRINT('Las unidades del objeto se han actualizado a 0.')
END
go

--- 4.8
--- Procedimiento que permite insertar un socio.
Create or alter proc nuevoSocio @idusuario int, @error int out 
as
BEGIN TRY
BEGIN TRANSACTION

	INSERT [dbo].[socio] ([idusuario],[fechaSocio]) 
			VALUES (@idusuario, getdate())
	
	if @@ERROR <> 0
		begin
			raiserror ('El usuario ya es socio.',16,1)
		end
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
	rollback transaction
		exec dbo.usp_showerrorinfo
		set @error = -1 -- si se salta al raiserror se pone @error a -1
		return;
END CATCH
	set @error = 0
	RETURN;
go

--- Procedimiento que permite borrar un socio.
Create proc eliminarSocio @idusuario int, @error int out
as
BEGIN TRY
	BEGIN TRANSACTION
	delete from socio where idusuario = @idusuario

	if @@ERROR <> 0
		begin
			raiserror ('El usuario no es socio.',16,1)
		end
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
		rollback transaction
		exec dbo.usp_showerrorinfo
		set @error = -1 -- si se salta al raiserror se pone @error a -1
		return;
END CATCH
	set @error = 0 -- si no hay error se pone @error a 0
	return;
go

--- Gestionamos que no se puedan actualizar los usuarios.
Create or alter trigger actualizaSocios on dbo.socio instead of update
as begin

	Declare @msg varchar (250)

	set @msg = 'No se puede actualizar la tabla de socios'
	rollback transaction
	raiserror (@msg,16,1)
	return
end
go

--- 4.9.1 
--- Procedimiento que permite la inserción de estadísticas de un objeto.
CREATE OR ALTER PROC insertaEstadistica @nombreObjeto varchar(20), @nombreEstadistica varchar(30), @duracion int, @porcentaje decimal(5,2), @error int out
AS
BEGIN TRY
BEGIN TRANSACTION
	DECLARE @idobjeto int;
	DECLARE @idestadistica int;
	SELECT @idobjeto = idobjeto FROM objeto WHERE nombre = @nombreObjeto;
	SELECT @idestadistica = idestadistica FROM estadistica WHERE nombre = @nombreEstadistica;

	--- Gestionamos que no exista la estadística.
	IF NOT EXISTS(select * from estadistica where idestadistica = @idestadistica)
		begin
			raiserror ('No existe la estadística.', 16, 1)
		end
	--- Gestionamos que no exista el objeto.
	IF NOT EXISTS(select * from objeto where idobjeto = @idobjeto)
		begin
			raiserror ('No existe el objeto.', 16, 1)
		end
	--- Gestionamos que no exista esa estadística para el objeto dado.
	IF EXISTS(select * from estadisticaObjeto where idobjeto = @idobjeto and idestadistica = @idestadistica)
		begin
			raiserror('Ya existe esta estadística para el objeto seleccionado.', 16, 1)
		end
	INSERT INTO estadisticaObjeto (idobjeto, idestadistica, duracion, porcentaje) VALUES (@idobjeto, @idestadistica, @duracion, @porcentaje)
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
		rollback transaction
		exec dbo.usp_showerrorinfo
		set @error = -1 -- si se salta al raiserror se pone @error a -1
		return;
END CATCH
	set @error = 0 -- si no hay error se pone @error a 0
	return;

go
	
--- 4.9.2
--- Procedimiento que permite borrar las características del objeto.
CREATE OR ALTER PROC borraEstadistica @nombreObjeto varchar(20), @nombreEstadistica varchar(30), @error int out
AS
BEGIN TRY
BEGIN TRANSACTION
	DECLARE @idobjeto int;
	DECLARE @idestadistica int;
	SELECT @idobjeto = idobjeto FROM objeto WHERE nombre = @nombreObjeto;
	SELECT @idestadistica = idestadistica FROM estadistica WHERE nombre = @nombreEstadistica;

	IF NOT EXISTS(select * from estadisticaObjeto where idobjeto = @idobjeto and idestadistica = @idestadistica)
		begin
			raiserror('No existe la estadística para el objeto seleccionado.', 16, 1)
		end
	DELETE FROM estadisticaObjeto WHERE idobjeto = @idobjeto and idestadistica = @idestadistica;
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
		rollback transaction
		exec dbo.usp_showerrorinfo
		set @error = -1 -- si se salta al raiserror se pone @error a -1
		return;
END CATCH

	set @error = 0 -- si no hay error se pone @error a 0
	return;

	SET @error = 0
COMMIT TRANSACTION
go

------------------------------------------------------------------------------
-------------------------Inserción de datos base------------------------------
------------------------------------------------------------------------------

--- Usuarios
SET IDENTITY_INSERT [dbo].[usuario] ON 
INSERT [dbo].[usuario] ([idusuario], [nombreUsuario], [dinero], [nivel], [fechaRegistro]) 
VALUES (1, 'Master molon', 20, 1, '17/04/2020')
INSERT [dbo].[usuario] ([idusuario], [nombreUsuario], [dinero], [nivel], [fechaRegistro]) 
VALUES (2, 'Usuario1', 10, 1, '17/04/2020')
INSERT [dbo].[usuario] ([idusuario], [nombreUsuario], [dinero], [nivel], [fechaRegistro]) 
VALUES (3, 'Usuario2', 40, 3, '17/04/2020')
INSERT [dbo].[usuario] ([idusuario], [nombreUsuario], [dinero], [nivel], [fechaRegistro]) 
VALUES (4, 'Usuario3', 50, 6, '17/04/2020')
INSERT [dbo].[usuario] ([idusuario], [nombreUsuario], [dinero], [nivel], [fechaRegistro]) 
VALUES (5, 'Usuario4', 200, 10, '17/04/2020')
SET IDENTITY_INSERT [dbo].[usuario] OFF

--- Socios
SET IDENTITY_INSERT [dbo].[socio] ON 
INSERT [dbo].[socio] ([idsocio], [idusuario],[fechaSocio]) 
VALUES (1, 1,'18/04/2020')
SET IDENTITY_INSERT [dbo].[socio] OFF

--- Tiendas
SET IDENTITY_INSERT [dbo].[tienda] ON 
INSERT [dbo].[tienda] ([idtienda],[nombre],[dinero]) VALUES (1, 'Tienda1', 0)
SET IDENTITY_INSERT [dbo].[tienda] OFF

--- Forjas
SET IDENTITY_INSERT [dbo].[forja] ON 
INSERT [dbo].[forja] ([idforja],[idtienda]) VALUES (1, 1)
SET IDENTITY_INSERT [dbo].[forja] OFF 

--- Armas
SET IDENTITY_INSERT [dbo].[arma] ON 
INSERT [dbo].[arma] ([idarma], [nombre]) VALUES (1, 'Espada basica')
INSERT [dbo].[arma] ([idarma], [nombre]) VALUES (2, 'Hacha basica')
INSERT [dbo].[arma] ([idarma], [nombre]) VALUES (0, 'NULL')
SET IDENTITY_INSERT [dbo].[arma] OFF

--- Efectos
SET IDENTITY_INSERT [dbo].[efecto] ON 
INSERT [dbo].[efecto] ([idefecto], [nombre]) VALUES (0, 'NULL')
SET IDENTITY_INSERT [dbo].[efecto] OFF

--- Tipos de objetos
INSERT [dbo].[tipoObjeto] ([idtipoobjeto], [nombre]) VALUES (0, 'Arma')
INSERT [dbo].[tipoObjeto] ([idtipoobjeto], [nombre]) VALUES (1, 'Armadura')
INSERT [dbo].[tipoObjeto] ([idtipoobjeto], [nombre]) VALUES (2, 'Pocion')

--- Objetos
SET IDENTITY_INSERT [dbo].[objeto] ON 
INSERT [dbo].[objeto] ([idobjeto], [nombre], [nivel], [precio], [diasCreacion], [idtipoobjeto], [idarma], [idefecto])
VALUES (1, 'Espada basica', 1, 5, 1, 0, 1, NULL)
INSERT [dbo].[objeto] ([idobjeto], [nombre], [nivel], [precio], [diasCreacion], [idtipoobjeto], [idarma], [idefecto])
VALUES (2, 'Hacha basica', 1, 6, 1, 0, 2, NULL)
SET IDENTITY_INSERT [dbo].[objeto] OFF

--- objetoTienda
INSERT [dbo].[objetoTienda] ([idtienda],[idobjeto],[unidades]) VALUES (1, 1, 3)
INSERT [dbo].[objetoTienda] ([idtienda],[idobjeto],[unidades]) VALUES (1, 2, 2)

--- Compras
SET IDENTITY_INSERT [dbo].[compra] ON
INSERT [dbo].[compra] ([idcompra], [idusuario],[idtienda], [fechaCompra], [precioTotal]) VALUES (1, 1, 1, '18/04/2020', 0)
SET IDENTITY_INSERT [dbo].[compra] OFF

--- Líneas de compras
INSERT [dbo].[lineaCompra] ([idcompra], [idobjeto], [precioUnitario], [unidades]) VALUES (1, 1, 5, 2)
go

--- Encargos
SET IDENTITY_INSERT [dbo].[encargo] ON
INSERT [dbo].[encargo] ([idencargo], [idsocio],[idforja],[fechaEntrega], [fechaEncargo], [estado]) 
	VALUES (1, 1, 1, '20/04/2020', '18/04/2020', 'en preparación')
INSERT [dbo].[encargo] ([idencargo], [idsocio],[idforja],[fechaEntrega], [fechaEncargo], [estado]) 
	VALUES (2, 1, 1, '20/04/2020', '18/04/2020', 'en preparación')
SET IDENTITY_INSERT [dbo].[encargo] OFF

--- Líneas de encargos
-- Encargo 1
INSERT [dbo].[lineaEncargo] ([idencargo], [idobjeto],[precioUnitario],[unidades]) VALUES (1, 1, 5, 2)
-- Encargo 2
INSERT [dbo].[lineaEncargo] ([idencargo], [idobjeto],[precioUnitario],[unidades]) VALUES (2, 1, 5, 2)
INSERT [dbo].[lineaEncargo] ([idencargo], [idobjeto],[precioUnitario],[unidades]) VALUES (2, 2, 6, 1)

------------------------------------------------------------------------------
----------------------------- Copia de seguridad -----------------------------
------------------------------------------------------------------------------

use TandF_TOOMNOOK;
go
-- Crear dispotitivo de copia de seguridad TandF
EXEC sp_addumpdevice 'disk', 'TandF', 'C:\pruebaBackup\TandF.Bak';  

-- Backup inicial, sin usar dispositivo de copia
BACKUP DATABASE TandF_TOOMNOOK
TO DISK ='C:\pruebaBackup\TandF.Bak' -- crea un fichero .bak
WITH FORMAT;
GO

-- Realizamos el backup usando el dispositivo de copia de seguridad creado anteriormente
BACKUP DATABASE TandF_TOOMNOOK
TO TandF
WITH FORMAT;
go