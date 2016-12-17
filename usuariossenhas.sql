--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.8
-- Dumped by pg_dump version 9.4.8
-- Started on 2016-07-20 16:52:07

--SET statement_timeout = 0;
--SET lock_timeout = 0;
--SET client_encoding = 'UTF8';
--SET standard_conforming_strings = on;
--SET check_function_bodies = false;
--SET client_min_messages = warning;

--DROP DATABASE IF EXISTS usuariossenhas;
--
-- TOC entry 2002 (class 1262 OID 21219)
-- Name: usuariossenhas; Type: DATABASE; Schema: -; Owner: postgres
--

--CREATE DATABASE usuariossenhas WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';


--ALTER DATABASE usuariossenhas OWNER TO postgres;

--\connect usuariossenhas

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

--CREATE SCHEMA public;


--ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2003 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

--COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 1 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 174 (class 1259 OID 21225)
-- Name: grupos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE grupos (
    usuario character varying(15) NOT NULL,
    grupo character varying(15) NOT NULL
);


ALTER TABLE grupos OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 21220)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuarios (
    usuario character varying(15) NOT NULL,
    senha character varying(15)
);


ALTER TABLE usuarios OWNER TO postgres;

--
-- TOC entry 1997 (class 0 OID 21225)
-- Dependencies: 174
-- Data for Name: grupos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO grupos VALUES ('leao', 'admin');


--
-- TOC entry 1996 (class 0 OID 21220)
-- Dependencies: 173
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuarios VALUES ('leao', 'leao');


--
-- TOC entry 1884 (class 2606 OID 21224)
-- Name: usuario_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuario_pk PRIMARY KEY (usuario);


--
-- TOC entry 1886 (class 2606 OID 21229)
-- Name: usuariogrupo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grupos
    ADD CONSTRAINT usuariogrupo PRIMARY KEY (usuario, grupo);


--
-- TOC entry 2004 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-07-20 16:52:09

--
-- PostgreSQL database dump complete
--

