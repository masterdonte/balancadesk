DROP DATABASE BALANCATMP;
CREATE DATABASE BALANCATMP;

-- rodar o programa principal para gerar as tabelas

INSERT INTO [BALANCATMP].dbo.CONFIGBALANCA (BALANCA, MODELO, PORTA, BAUDRATE, COPIAS, RELATORIO, LEITORSERVER, LEITORPORTA, MACHOST)
SELECT BALANCA, MODELO, PORTA, BAUDRATE, COPIAS, RELATORIO, LEITORSERVER, LEITORPORTA, MACHOST FROM DBS2GPI.dbo.CONFIGBALANCA

UPDATE [BALANCATMP].dbo.CONFIGBALANCA SET COPIAS = 4

INSERT INTO [BALANCATMP].dbo.Usuario (id, login, nome, senha)
SELECT ID_USUARIO, USERNAME, NOME, SENHA FROM DBS2GPI.dbo.USUARIO WHERE ASSINATURA IS NOT NULL OR ID_USUARIO in (165,987)

UPDATE [BALANCATMP].dbo.Usuario SET SENHA = '6E339C3D6E83626A7A4148C6DEAA17F2' --emap2018

UPDATE [BALANCATMP].dbo.Usuario SET SENHA = '2719C1F15C21AA4CAE209F52A142C6B0' WHERE ID = 165

INSERT INTO [BALANCATMP].dbo.Usuario (id, login, nome, senha) values(1040, 'ccoemap', 'CCO', '1872C544CF592FAE201E0BB963ACCF2D') -- c0nt1ng3nc1@

INSERT INTO [BALANCATMP].dbo.Navio 
SELECT ID_NAVIO, UPPER(NOME) FROM DBS2GPI.dbo.NAVIO

INSERT INTO [BALANCATMP].dbo.Cliente (id, nome, cnpj, cnpjmatriz) 
SELECT ID_CLIENTE, UPPER(NOME), CNPJ, CNPJ_MATRIZ FROM DBS2GPI.dbo.CLIENTE

INSERT INTO [BALANCATMP].dbo.Viagem(id,navioid,statusviagem,dataatracacao,datapartida) 
SELECT ID_VIAGEM, FK_ID_NAVIO, FK_ID_STATUS_VIAGEM, DATA_ATRACACAO, DATA_PARTIDA FROM DBS2GPI.dbo.VIAGEM

INSERT INTO [BALANCATMP].dbo.Bl (id, viagemid, clienteid, numero, descricao)
SELECT ID_BL, FK_ID_VIAGEM, FK_ID_CLIENTE, UPPER(NRO_BL), UPPER(DESCRICAO) FROM DBS2GPI.dbo.BL

INSERT INTO [BALANCATMP].dbo.Veiculo (id, placa)
SELECT ID_VEICULO, PLACA FROM DBS2GPI.dbo.VEICULO WHERE PLACA IS NOT NULL AND LEN(PLACA) = 7

INSERT INTO [BALANCATMP].dbo.Turno(id, descricao, horainicio, horafim)
SELECT ID_TURNO_BALANCA, DESCRICAO, HORA_INICIO, HORA_FIM FROM DBS2GPI.dbo.TURNO_BALANCA

--- RODAR A FUNCAO NA IDE NO BANCO [BALANCATMP]

CREATE FUNCTION [dbo].[FNC_TURNO_BALANCA_ID]
( 
	@HORA VARCHAR(5)
	
)
RETURNS INT
AS
BEGIN
		
   DECLARE @ID_TURNO INT;
	SELECT @ID_TURNO = A.ID
		FROM
		(
		SELECT B.ID, CONVERT(DATETIME, '2000/01/01 ' + B.HORAINICIO, 103) DATA_INICIO,
		CASE WHEN B.HORAINICIO <= B.HORAFIM THEN 
		  CONVERT(DATETIME, '2000/01/01 ' + B.HORAFIM, 103)
		ELSE
		  CONVERT(DATETIME, '2000/01/02 ' + B.HORAFIM, 103)
		END DATA_FIM
		FROM TURNO B
		) A
	WHERE
	CONVERT(DATETIME, '2000/01/01 ' + @HORA, 103) BETWEEN A.DATA_INICIO 
		AND A.DATA_FIM
	OR
	CONVERT(DATETIME, '2000/01/02 ' + @HORA, 103) BETWEEN DATA_INICIO 
		AND A.DATA_FIM

	-- Return the result of the function
	RETURN @ID_TURNO

END


/*
 CREATE TABLE DBS2GPI.dbo.CONFIGBALANCA(  
	ID INT IDENTITY(1,1),  
	BALANCA varchar (20),
	MODELO varchar (20),
	PORTA varchar (20),
	BAUDRATE INT,
	COPIAS INT,
	RELATORIO varchar (20),
	LEITORSERVER varchar (20),
	LEITORPORTA INT,
	MACHOST varchar (20)
);

insert into DBS2GPI.dbo.CONFIGBALANCA(BALANCA, MODELO, PORTA, BAUDRATE, COPIAS, RELATORIO, LEITORSERVER, LEITORPORTA, MACHOST) VALUES('B1'  , 'FILIZOLA', 'COM1', 9600, 3, 'PapelTermico', 'localhost', 12345, '2C-59-E5-BE-F2-4F');
insert into DBS2GPI.dbo.CONFIGBALANCA(BALANCA, MODELO, PORTA, BAUDRATE, COPIAS, RELATORIO, LEITORSERVER, LEITORPORTA, MACHOST) VALUES('B2'  , 'LIDER'   , 'COM1', 2400, 3, 'PapelTermico', 'localhost', 12345, '2C-59-E5-BE-E2-62');
insert into DBS2GPI.dbo.CONFIGBALANCA(BALANCA, MODELO, PORTA, BAUDRATE, COPIAS, RELATORIO, LEITORSERVER, LEITORPORTA, MACHOST) VALUES('B3'  , 'LIDER'   , 'COM1', 2400, 3, 'PapelTermico', 'localhost', 12345, '2C-59-E5-BE-E2-64');
insert into DBS2GPI.dbo.CONFIGBALANCA(BALANCA, MODELO, PORTA, BAUDRATE, COPIAS, RELATORIO, LEITORSERVER, LEITORPORTA, MACHOST) VALUES('B4'  , 'LIDER'   , 'COM1', 2400, 3, 'PapelTermico', 'localhost', 12345, 'A4-5D-36-C2-22-81');
insert into DBS2GPI.dbo.CONFIGBALANCA(BALANCA, MODELO, PORTA, BAUDRATE, COPIAS, RELATORIO, LEITORSERVER, LEITORPORTA, MACHOST) VALUES('BTST', 'FILIZOLA', 'COM2', 9600, 3, 'PapelTermico', 'localhost', 12345, 'F4-8E-38-FE-54-37');



select * from Navio n where n.nome like '%XXX%'

select * from Viagem v where v.navioid = idnavio

-- verificar se tem produtos para a viagem

select * from Bl p where p.viagemid = idviagem

update viagem set dataatracacao = getdate(), statusviagem = 3, datapartida = null where id = XXX

-- inserir o valor de id manualmente uma vez que a chave nao é gerada automaticamente
INSERT INTO [BALANCATMP].dbo.Bl (id, viagemid, clienteid, numero, descricao) values (12345, idviagem, idcliente, numero, descricao )


select c.nome as CLIENTE, p.descricao as PRODUTO, sum(b.pesoliq) as TOT_PESO_LIQ from Balanca b 
inner join Bl p on p.id = b.blid
inner join Cliente c on c.id = p.clienteid
inner join Viagem v on v.id = p.viagemid
where v.id = 5613 and b.pesoliq > 0
group by c.nome, p.descricao

-- rodando_mvn
mvn assembly:assembly

<build>
	<finalName>Scale</finalName>

	<plugins>
		<plugin>
			<artifactId>maven-assembly-plugin</artifactId>
			<configuration>
				<descriptorRefs>
					<descriptorRef>jar-with-dependencies</descriptorRef>
				</descriptorRefs>
				<archive>
					<manifest>
						<mainClass>com.emap.scale.Principal</mainClass>
					</manifest>
				</archive>
			</configuration>
		</plugin>
	</plugins>

</build>
*/