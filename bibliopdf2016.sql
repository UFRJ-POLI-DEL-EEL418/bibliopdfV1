--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.8
-- Dumped by pg_dump version 9.4.8
-- Started on 2016-08-02 19:47:45

--SET statement_timeout = 0;
--SET lock_timeout = 0;
--SET client_encoding = 'UTF8';
--SET standard_conforming_strings = on;
--SET check_function_bodies = false;
--SET client_min_messages = warning;

--DROP DATABASE IF EXISTS bibliopdf2016;
--
-- TOC entry 2057 (class 1262 OID 19168)
-- Name: bibliopdf; Type: DATABASE; Schema: -; Owner: postgres
--

--CREATE DATABASE bibliopdf2016 WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';


--ALTER DATABASE bibliopdf OWNER TO postgres;

--\connect bibliopdf2016

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

--CREATE SCHEMA public;


--ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2058 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

--COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 1 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2060 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 19169)
-- Name: dadoscatalogo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dadoscatalogo (
    patrimonio integer NOT NULL,
    titulo text,
    autoria text,
    veiculo text,
    data_publicacao text,
    nomeoriginalarquivo text
);


ALTER TABLE dadoscatalogo OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 19175)
-- Name: dadoscatalogo_patrimonio_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE dadoscatalogo_patrimonio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dadoscatalogo_patrimonio_seq OWNER TO postgres;

--
-- TOC entry 2061 (class 0 OID 0)
-- Dependencies: 174
-- Name: dadoscatalogo_patrimonio_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE dadoscatalogo_patrimonio_seq OWNED BY dadoscatalogo.patrimonio;


--
-- TOC entry 175 (class 1259 OID 19177)
-- Name: palavras_chave; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE palavras_chave (
    serialpalchave integer NOT NULL,
    palchave text,
    patrimonio integer,
    palchavenormal text
);


ALTER TABLE palavras_chave OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 19183)
-- Name: palavras_chave_serial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE palavras_chave_serial_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE palavras_chave_serial_seq OWNER TO postgres;

--
-- TOC entry 2062 (class 0 OID 0)
-- Dependencies: 176
-- Name: palavras_chave_serial_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE palavras_chave_serial_seq OWNED BY palavras_chave.serialpalchave;


--
-- TOC entry 179 (class 1259 OID 19958)
-- Name: palavrasautorianormal_serial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE palavrasautorianormal_serial_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE palavrasautorianormal_serial_seq OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 19960)
-- Name: palavrasautorianormal; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE palavrasautorianormal (
    serialpalautoria integer DEFAULT nextval('palavrasautorianormal_serial_seq'::regclass) NOT NULL,
    palavra_autoria_normal text,
    patrimonio integer
);


ALTER TABLE palavrasautorianormal OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 19185)
-- Name: palavrastitulonormal; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE palavrastitulonormal (
    serialpaltitulo integer NOT NULL,
    palavra_titulo_normal text,
    patrimonio integer
);


ALTER TABLE palavrastitulonormal OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 19191)
-- Name: palavrastitulonormal_serial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE palavrastitulonormal_serial_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE palavrastitulonormal_serial_seq OWNER TO postgres;

--
-- TOC entry 2063 (class 0 OID 0)
-- Dependencies: 178
-- Name: palavrastitulonormal_serial_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE palavrastitulonormal_serial_seq OWNED BY palavrastitulonormal.serialpaltitulo;


--
-- TOC entry 181 (class 1259 OID 19984)
-- Name: palavrasveiculonormal_serial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE palavrasveiculonormal_serial_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE palavrasveiculonormal_serial_seq OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 19986)
-- Name: palavrasveiculonormal; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE palavrasveiculonormal (
    serialpalveiculo integer DEFAULT nextval('palavrasveiculonormal_serial_seq'::regclass) NOT NULL,
    palavra_veiculo_normal text,
    patrimonio integer
);


ALTER TABLE palavrasveiculonormal OWNER TO postgres;

--
-- TOC entry 1910 (class 2604 OID 19193)
-- Name: patrimonio; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY dadoscatalogo ALTER COLUMN patrimonio SET DEFAULT nextval('dadoscatalogo_patrimonio_seq'::regclass);


--
-- TOC entry 1911 (class 2604 OID 19194)
-- Name: serialpalchave; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY palavras_chave ALTER COLUMN serialpalchave SET DEFAULT nextval('palavras_chave_serial_seq'::regclass);


--
-- TOC entry 1912 (class 2604 OID 19195)
-- Name: serialpaltitulo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY palavrastitulonormal ALTER COLUMN serialpaltitulo SET DEFAULT nextval('palavrastitulonormal_serial_seq'::regclass);


--
-- TOC entry 2043 (class 0 OID 19169)
-- Dependencies: 173
-- Data for Name: dadoscatalogo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO dadoscatalogo VALUES (181, 'TCP/IP Illustrated (3 Volume Set)', 'Stevens, W. Richard; Wright, Gary R.', 'Addison-Wesley', '2001-01-01 00:00:00', NULL);
INSERT INTO dadoscatalogo VALUES (187, 'Building expert systems in Prolog', 'Dennis Merritt', 'Springer-Verlag, 1989, Amzi Inc, 2000', '1989-01-01 00:00:00', NULL);
INSERT INTO dadoscatalogo VALUES (186, 'Prolog programming for artificial intelligence', 'Ivan Bratko', 'Addision-Wesley', '1990-01-01 00:00:00', NULL);
INSERT INTO dadoscatalogo VALUES (146, 'The Theory of Probability', 'B. Gnedenko', 'MIR Publishers, Moscou', '1978-01-01 00:00:00', '[B._Gnedenko]_The_Theory_of_Probability(BookZZ.org).pdf');
INSERT INTO dadoscatalogo VALUES (148, 'Introduction to Probability', 'Dimitri P. Bertsekas and John N. Tsitsiklis', 'LECTURE NOTES Course 6.041-6.431 M.I.T.', '2000-01-01 00:00:00', '[Dimitri_Bertsekas_And_John_N_Tsitsiklis]_Introduc(Book4You).pdf');
INSERT INTO dadoscatalogo VALUES (147, 'Applied Probability and Stochastic Processes', 'Włodzimierz Bryc', 'Cincinnati Free Texts', '1995-01-01 00:00:00', '[Bryc]_Applied_Probability_and_Stochastic_Processe(BookZZ.org).pdf');
INSERT INTO dadoscatalogo VALUES (176, 'Professional JSP', 'Brown, Simon et alia', 'Programmer to programmer,Wrox Press', '2001-01-01 00:00:00', '2015-spueler-A_spiking_neuronal_model_learning_a_motor_control_task_by_reinforcement_learning_and_structural_synaptic_plasticity.pdf');
INSERT INTO dadoscatalogo VALUES (178, 'Javascript: the good parts', 'Crockford, Douglas', 'Yahoo! Inc., O''Reilly Media Inc.', '2008-01-01 00:00:00', 'javascript_the_good_parts.pdf');
INSERT INTO dadoscatalogo VALUES (180, 'Secrets of the Javascript ninja', 'John Resig', 'Manning Publications', '2009-01-01 00:00:00', 'Secrets.of.the.JavaScript.Ninja.Resig.pdf');
INSERT INTO dadoscatalogo VALUES (182, 'Object-Oriented JavaScript: Create scalable, reusable high-quality JavaScript applications,and libraries', 'Stoyan Stefanov', 'Packt Publishing', '2008-01-01 00:00:00', NULL);
INSERT INTO dadoscatalogo VALUES (179, 'Java for the Web with Servlets, JSP, and EJB: A Developer''s Guide to J2EE Solutions', 'Budi Kurniawan', 'New Riders Publishing', '2002-01-01 00:00:00', 'enunciado_terceiro_trabalho.pdf');
INSERT INTO dadoscatalogo VALUES (177, 'Internetworking with TCP/IP (vols. I, II e III)', 'Comer, D.E.; Stevens, D.L.', 'Prentice-Hall', '1993-01-01 00:00:00', 'enunciado_terceiro_trabalho.pdf');


--
-- TOC entry 2064 (class 0 OID 0)
-- Dependencies: 174
-- Name: dadoscatalogo_patrimonio_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('dadoscatalogo_patrimonio_seq', 190, true);


--
-- TOC entry 2045 (class 0 OID 19177)
-- Dependencies: 175
-- Data for Name: palavras_chave; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO palavras_chave VALUES (17, 'Matemática', 146, 'MATEMATICA');


--
-- TOC entry 2065 (class 0 OID 0)
-- Dependencies: 176
-- Name: palavras_chave_serial_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('palavras_chave_serial_seq', 17, true);


--
-- TOC entry 2050 (class 0 OID 19960)
-- Dependencies: 180
-- Data for Name: palavrasautorianormal; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO palavrasautorianormal VALUES (173, 'DIMITRI', 148);
INSERT INTO palavrasautorianormal VALUES (174, 'P', 148);
INSERT INTO palavrasautorianormal VALUES (175, 'BERTSEKAS', 148);
INSERT INTO palavrasautorianormal VALUES (176, 'AND', 148);
INSERT INTO palavrasautorianormal VALUES (177, 'JOHN', 148);
INSERT INTO palavrasautorianormal VALUES (178, 'N', 148);
INSERT INTO palavrasautorianormal VALUES (179, 'TSITSIKLIS', 148);
INSERT INTO palavrasautorianormal VALUES (180, 'BROWN', 176);
INSERT INTO palavrasautorianormal VALUES (181, 'SIMON', 176);
INSERT INTO palavrasautorianormal VALUES (182, 'ET', 176);
INSERT INTO palavrasautorianormal VALUES (183, 'ALIA', 176);
INSERT INTO palavrasautorianormal VALUES (185, 'COMER', 177);
INSERT INTO palavrasautorianormal VALUES (186, 'D', 177);
INSERT INTO palavrasautorianormal VALUES (187, 'E', 177);
INSERT INTO palavrasautorianormal VALUES (188, 'STEVENS', 177);
INSERT INTO palavrasautorianormal VALUES (189, 'D', 177);
INSERT INTO palavrasautorianormal VALUES (190, 'L', 177);
INSERT INTO palavrasautorianormal VALUES (193, 'BUDI', 179);
INSERT INTO palavrasautorianormal VALUES (194, 'KURNIAWAN', 179);
INSERT INTO palavrasautorianormal VALUES (147, 'STOYAN', 182);
INSERT INTO palavrasautorianormal VALUES (148, 'STEFANOV', 182);
INSERT INTO palavrasautorianormal VALUES (155, 'STEVENS', 181);
INSERT INTO palavrasautorianormal VALUES (156, 'W', 181);
INSERT INTO palavrasautorianormal VALUES (157, 'RICHARD', 181);
INSERT INTO palavrasautorianormal VALUES (158, 'WRIGHT', 181);
INSERT INTO palavrasautorianormal VALUES (159, 'GARY', 181);
INSERT INTO palavrasautorianormal VALUES (160, 'R', 181);
INSERT INTO palavrasautorianormal VALUES (161, 'WŁODZIMIERZ', 147);
INSERT INTO palavrasautorianormal VALUES (162, 'BRYC', 147);
INSERT INTO palavrasautorianormal VALUES (163, 'DENNIS', 187);
INSERT INTO palavrasautorianormal VALUES (164, 'MERRITT', 187);
INSERT INTO palavrasautorianormal VALUES (165, 'IVAN', 186);
INSERT INTO palavrasautorianormal VALUES (166, 'BRATKO', 186);
INSERT INTO palavrasautorianormal VALUES (167, 'JOHN', 180);
INSERT INTO palavrasautorianormal VALUES (168, 'RESIG', 180);
INSERT INTO palavrasautorianormal VALUES (169, 'CROCKFORD', 178);
INSERT INTO palavrasautorianormal VALUES (170, 'DOUGLAS', 178);
INSERT INTO palavrasautorianormal VALUES (171, 'B', 146);
INSERT INTO palavrasautorianormal VALUES (172, 'GNEDENKO', 146);


--
-- TOC entry 2066 (class 0 OID 0)
-- Dependencies: 179
-- Name: palavrasautorianormal_serial_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('palavrasautorianormal_serial_seq', 194, true);


--
-- TOC entry 2047 (class 0 OID 19185)
-- Dependencies: 177
-- Data for Name: palavrastitulonormal; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO palavrastitulonormal VALUES (1099, 'INTRODUCTION', 148);
INSERT INTO palavrastitulonormal VALUES (1100, 'TO', 148);
INSERT INTO palavrastitulonormal VALUES (1101, 'PROBABILITY', 148);
INSERT INTO palavrastitulonormal VALUES (1102, 'PROFESSIONAL', 176);
INSERT INTO palavrastitulonormal VALUES (1103, 'JSP', 176);
INSERT INTO palavrastitulonormal VALUES (1106, 'INTERNETWORKING', 177);
INSERT INTO palavrastitulonormal VALUES (1107, 'WITH', 177);
INSERT INTO palavrastitulonormal VALUES (1108, 'TCP', 177);
INSERT INTO palavrastitulonormal VALUES (1109, 'IP', 177);
INSERT INTO palavrastitulonormal VALUES (1110, 'VOLS', 177);
INSERT INTO palavrastitulonormal VALUES (1111, 'I', 177);
INSERT INTO palavrastitulonormal VALUES (1112, 'II', 177);
INSERT INTO palavrastitulonormal VALUES (1113, 'E', 177);
INSERT INTO palavrastitulonormal VALUES (1114, 'III', 177);
INSERT INTO palavrastitulonormal VALUES (1131, 'JAVA', 179);
INSERT INTO palavrastitulonormal VALUES (1132, 'FOR', 179);
INSERT INTO palavrastitulonormal VALUES (1133, 'THE', 179);
INSERT INTO palavrastitulonormal VALUES (1134, 'WEB', 179);
INSERT INTO palavrastitulonormal VALUES (1135, 'WITH', 179);
INSERT INTO palavrastitulonormal VALUES (1136, 'SERVLETS', 179);
INSERT INTO palavrastitulonormal VALUES (1137, 'JSP', 179);
INSERT INTO palavrastitulonormal VALUES (1138, 'AND', 179);
INSERT INTO palavrastitulonormal VALUES (1139, 'EJB', 179);
INSERT INTO palavrastitulonormal VALUES (1140, 'A', 179);
INSERT INTO palavrastitulonormal VALUES (1141, 'DEVELOPER', 179);
INSERT INTO palavrastitulonormal VALUES (1142, 'S', 179);
INSERT INTO palavrastitulonormal VALUES (1143, 'GUIDE', 179);
INSERT INTO palavrastitulonormal VALUES (1144, 'TO', 179);
INSERT INTO palavrastitulonormal VALUES (1145, 'J2EE', 179);
INSERT INTO palavrastitulonormal VALUES (1146, 'SOLUTIONS', 179);
INSERT INTO palavrastitulonormal VALUES (1045, 'OBJECT', 182);
INSERT INTO palavrastitulonormal VALUES (1046, 'ORIENTED', 182);
INSERT INTO palavrastitulonormal VALUES (1047, 'JAVASCRIPT', 182);
INSERT INTO palavrastitulonormal VALUES (1048, 'CREATE', 182);
INSERT INTO palavrastitulonormal VALUES (1049, 'SCALABLE', 182);
INSERT INTO palavrastitulonormal VALUES (1050, 'REUSABLE', 182);
INSERT INTO palavrastitulonormal VALUES (1051, 'HIGH', 182);
INSERT INTO palavrastitulonormal VALUES (1052, 'QUALITY', 182);
INSERT INTO palavrastitulonormal VALUES (1053, 'JAVASCRIPT', 182);
INSERT INTO palavrastitulonormal VALUES (1054, 'APPLICATIONS', 182);
INSERT INTO palavrastitulonormal VALUES (1055, 'AND', 182);
INSERT INTO palavrastitulonormal VALUES (1056, 'LIBRARIES', 182);
INSERT INTO palavrastitulonormal VALUES (1065, 'TCP', 181);
INSERT INTO palavrastitulonormal VALUES (1066, 'IP', 181);
INSERT INTO palavrastitulonormal VALUES (1067, 'ILLUSTRATED', 181);
INSERT INTO palavrastitulonormal VALUES (1068, '3', 181);
INSERT INTO palavrastitulonormal VALUES (1069, 'VOLUME', 181);
INSERT INTO palavrastitulonormal VALUES (1070, 'SET', 181);
INSERT INTO palavrastitulonormal VALUES (1071, 'APPLIED', 147);
INSERT INTO palavrastitulonormal VALUES (1072, 'PROBABILITY', 147);
INSERT INTO palavrastitulonormal VALUES (1073, 'AND', 147);
INSERT INTO palavrastitulonormal VALUES (1074, 'STOCHASTIC', 147);
INSERT INTO palavrastitulonormal VALUES (1075, 'PROCESSES', 147);
INSERT INTO palavrastitulonormal VALUES (1076, 'BUILDING', 187);
INSERT INTO palavrastitulonormal VALUES (1077, 'EXPERT', 187);
INSERT INTO palavrastitulonormal VALUES (1078, 'SYSTEMS', 187);
INSERT INTO palavrastitulonormal VALUES (1079, 'IN', 187);
INSERT INTO palavrastitulonormal VALUES (1080, 'PROLOG', 187);
INSERT INTO palavrastitulonormal VALUES (1081, 'PROLOG', 186);
INSERT INTO palavrastitulonormal VALUES (1082, 'PROGRAMMING', 186);
INSERT INTO palavrastitulonormal VALUES (1083, 'FOR', 186);
INSERT INTO palavrastitulonormal VALUES (1084, 'ARTIFICIAL', 186);
INSERT INTO palavrastitulonormal VALUES (1085, 'INTELLIGENCE', 186);
INSERT INTO palavrastitulonormal VALUES (1086, 'SECRETS', 180);
INSERT INTO palavrastitulonormal VALUES (1087, 'OF', 180);
INSERT INTO palavrastitulonormal VALUES (1088, 'THE', 180);
INSERT INTO palavrastitulonormal VALUES (1089, 'JAVASCRIPT', 180);
INSERT INTO palavrastitulonormal VALUES (1090, 'NINJA', 180);
INSERT INTO palavrastitulonormal VALUES (1091, 'JAVASCRIPT', 178);
INSERT INTO palavrastitulonormal VALUES (1092, 'THE', 178);
INSERT INTO palavrastitulonormal VALUES (1093, 'GOOD', 178);
INSERT INTO palavrastitulonormal VALUES (1094, 'PARTS', 178);
INSERT INTO palavrastitulonormal VALUES (1095, 'THE', 146);
INSERT INTO palavrastitulonormal VALUES (1096, 'THEORY', 146);
INSERT INTO palavrastitulonormal VALUES (1097, 'OF', 146);
INSERT INTO palavrastitulonormal VALUES (1098, 'PROBABILITY', 146);


--
-- TOC entry 2067 (class 0 OID 0)
-- Dependencies: 178
-- Name: palavrastitulonormal_serial_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('palavrastitulonormal_serial_seq', 1146, true);


--
-- TOC entry 2052 (class 0 OID 19986)
-- Dependencies: 182
-- Data for Name: palavrasveiculonormal; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO palavrasveiculonormal VALUES (22, 'PACKT', 182);
INSERT INTO palavrasveiculonormal VALUES (23, 'PUBLISHING', 182);
INSERT INTO palavrasveiculonormal VALUES (26, 'ADDISON', 181);
INSERT INTO palavrasveiculonormal VALUES (27, 'WESLEY', 181);
INSERT INTO palavrasveiculonormal VALUES (28, 'CINCINNATI', 147);
INSERT INTO palavrasveiculonormal VALUES (29, 'FREE', 147);
INSERT INTO palavrasveiculonormal VALUES (30, 'TEXTS', 147);
INSERT INTO palavrasveiculonormal VALUES (31, 'SPRINGER', 187);
INSERT INTO palavrasveiculonormal VALUES (32, 'VERLAG', 187);
INSERT INTO palavrasveiculonormal VALUES (33, '1989', 187);
INSERT INTO palavrasveiculonormal VALUES (34, 'AMZI', 187);
INSERT INTO palavrasveiculonormal VALUES (35, 'INC', 187);
INSERT INTO palavrasveiculonormal VALUES (36, '2000', 187);
INSERT INTO palavrasveiculonormal VALUES (37, 'ADDISION', 186);
INSERT INTO palavrasveiculonormal VALUES (38, 'WESLEY', 186);
INSERT INTO palavrasveiculonormal VALUES (39, 'MANNING', 180);
INSERT INTO palavrasveiculonormal VALUES (40, 'PUBLICATIONS', 180);
INSERT INTO palavrasveiculonormal VALUES (41, 'YAHOO!', 178);
INSERT INTO palavrasveiculonormal VALUES (42, 'INC', 178);
INSERT INTO palavrasveiculonormal VALUES (43, 'O', 178);
INSERT INTO palavrasveiculonormal VALUES (44, 'REILLY', 178);
INSERT INTO palavrasveiculonormal VALUES (45, 'MEDIA', 178);
INSERT INTO palavrasveiculonormal VALUES (46, 'INC', 178);
INSERT INTO palavrasveiculonormal VALUES (47, 'MIR', 146);
INSERT INTO palavrasveiculonormal VALUES (48, 'PUBLISHERS', 146);
INSERT INTO palavrasveiculonormal VALUES (49, 'MOSCOU', 146);
INSERT INTO palavrasveiculonormal VALUES (50, 'LECTURE', 148);
INSERT INTO palavrasveiculonormal VALUES (51, 'NOTES', 148);
INSERT INTO palavrasveiculonormal VALUES (52, 'COURSE', 148);
INSERT INTO palavrasveiculonormal VALUES (53, '6', 148);
INSERT INTO palavrasveiculonormal VALUES (54, '041', 148);
INSERT INTO palavrasveiculonormal VALUES (55, '6', 148);
INSERT INTO palavrasveiculonormal VALUES (56, '431', 148);
INSERT INTO palavrasveiculonormal VALUES (57, 'M', 148);
INSERT INTO palavrasveiculonormal VALUES (58, 'I', 148);
INSERT INTO palavrasveiculonormal VALUES (59, 'T', 148);
INSERT INTO palavrasveiculonormal VALUES (60, 'PROGRAMMER', 176);
INSERT INTO palavrasveiculonormal VALUES (61, 'TO', 176);
INSERT INTO palavrasveiculonormal VALUES (62, 'PROGRAMMER', 176);
INSERT INTO palavrasveiculonormal VALUES (63, 'WROX', 176);
INSERT INTO palavrasveiculonormal VALUES (64, 'PRESS', 176);
INSERT INTO palavrasveiculonormal VALUES (66, 'PRENTICE', 177);
INSERT INTO palavrasveiculonormal VALUES (67, 'HALL', 177);
INSERT INTO palavrasveiculonormal VALUES (71, 'NEW', 179);
INSERT INTO palavrasveiculonormal VALUES (72, 'RIDERS', 179);
INSERT INTO palavrasveiculonormal VALUES (73, 'PUBLISHING', 179);


--
-- TOC entry 2068 (class 0 OID 0)
-- Dependencies: 181
-- Name: palavrasveiculonormal_serial_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('palavrasveiculonormal_serial_seq', 73, true);


--
-- TOC entry 1920 (class 2606 OID 19197)
-- Name: palavras_chave_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY palavras_chave
    ADD CONSTRAINT palavras_chave_pk PRIMARY KEY (serialpalchave);


--
-- TOC entry 1926 (class 2606 OID 19968)
-- Name: palavrasautorianormal_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY palavrasautorianormal
    ADD CONSTRAINT palavrasautorianormal_pk PRIMARY KEY (serialpalautoria);


--
-- TOC entry 1923 (class 2606 OID 19199)
-- Name: palavrastitulonormal_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY palavrastitulonormal
    ADD CONSTRAINT palavrastitulonormal_pk PRIMARY KEY (serialpaltitulo);


--
-- TOC entry 1929 (class 2606 OID 19994)
-- Name: palavrasveiculonormal_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY palavrasveiculonormal
    ADD CONSTRAINT palavrasveiculonormal_pk PRIMARY KEY (serialpalveiculo);


--
-- TOC entry 1917 (class 2606 OID 19201)
-- Name: patrimonio_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dadoscatalogo
    ADD CONSTRAINT patrimonio_pk PRIMARY KEY (patrimonio);


--
-- TOC entry 1915 (class 1259 OID 19983)
-- Name: ndx_data_publicacao; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ndx_data_publicacao ON dadoscatalogo USING btree (data_publicacao);


--
-- TOC entry 1924 (class 1259 OID 19974)
-- Name: ndx_palavra_autoria_normal; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ndx_palavra_autoria_normal ON palavrasautorianormal USING btree (palavra_autoria_normal COLLATE "C");


--
-- TOC entry 1921 (class 1259 OID 19202)
-- Name: ndx_palavra_titulo_normal; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ndx_palavra_titulo_normal ON palavrastitulonormal USING btree (palavra_titulo_normal COLLATE "C");


--
-- TOC entry 1927 (class 1259 OID 20000)
-- Name: ndx_palavra_veiculo_normal; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ndx_palavra_veiculo_normal ON palavrasveiculonormal USING btree (palavra_veiculo_normal COLLATE "C");


--
-- TOC entry 1918 (class 1259 OID 19975)
-- Name: ndx_palchavenormal; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ndx_palchavenormal ON palavras_chave USING btree (palchavenormal COLLATE "C");


--
-- TOC entry 1930 (class 2606 OID 19203)
-- Name: patrimonio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY palavras_chave
    ADD CONSTRAINT patrimonio_fk FOREIGN KEY (patrimonio) REFERENCES dadoscatalogo(patrimonio) ON DELETE CASCADE;


--
-- TOC entry 1931 (class 2606 OID 19208)
-- Name: patrimonio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY palavrastitulonormal
    ADD CONSTRAINT patrimonio_fk FOREIGN KEY (patrimonio) REFERENCES dadoscatalogo(patrimonio) ON DELETE CASCADE;


--
-- TOC entry 1932 (class 2606 OID 19969)
-- Name: patrimonio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY palavrasautorianormal
    ADD CONSTRAINT patrimonio_fk FOREIGN KEY (patrimonio) REFERENCES dadoscatalogo(patrimonio) ON DELETE CASCADE;


--
-- TOC entry 1933 (class 2606 OID 19995)
-- Name: patrimonio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY palavrasveiculonormal
    ADD CONSTRAINT patrimonio_fk FOREIGN KEY (patrimonio) REFERENCES dadoscatalogo(patrimonio) ON DELETE CASCADE;


--
-- TOC entry 2059 (class 0 OID 0)
-- Dependencies: 7
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-08-02 19:47:46

--
-- PostgreSQL database dump complete
--

