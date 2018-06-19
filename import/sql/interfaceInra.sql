#
# DUMP FILE
#
# Database is ported from MS Access
#------------------------------------------------------------------
# Created using "MS Access to MySQL" form http://www.bullzip.com
# Program Version 5.2.252
#
# OPTIONS:
#   sourcefilename=C:\Users\thurgi\Desktop\INRA\Envoi-Novelios\Base31072014\Interface Medical.mdb
#   sourceusername=
#   sourcepassword=
#   sourcesystemdatabase=
#   destinationdatabase=interfaceInra
#   storageengine=InnoDB
#   dropdatabase=0
#   createtables=1
#   unicode=1
#   autocommit=1
#   transferdefaultvalues=1
#   transferindexes=1
#   transferautonumbers=1
#   transferrecords=1
#   columnlist=1
#   tableprefix=
#   negativeboolean=0
#   ignorelargeblobs=0
#   memotype=LONGTEXT
#

CREATE DATABASE IF NOT EXISTS `interfaceInra`;
USE `interfaceInra`;

#
# Table structure for table 'analyse session 12/2000'
#

DROP TABLE IF EXISTS `analyse session 12/2000`;

CREATE TABLE `analyse session 12/2000` (
  `N°` INTEGER NOT NULL AUTO_INCREMENT, 
  `matricule` VARCHAR(50), 
  `nom_agent` VARCHAR(50), 
  `prenom_agent` VARCHAR(50), 
  `Champ4` VARCHAR(50), 
  `Champ5` INTEGER, 
  `Champ6` VARCHAR(50), 
  `Champ7` VARCHAR(50), 
  PRIMARY KEY (`N°`)
) ENGINE=innodb DEFAULT CHARSET=utf8;

SET autocommit=1;

#
# Dumping data for table 'analyse session 12/2000'
#

INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (1, '13466M', 'ABELARD', 'CHRISTINE', '13466M', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (2, '13466M', 'ABELARD', 'CHRISTINE', '13466M', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (3, '13466M', 'ABELARD', 'CHRISTINE', '13466M', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (10, '16123A', 'AUPERIN', 'BENOIT', '16123A', 1, 'NFS - Plaquettes', 'BEAULIEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (11, '15869Z', 'AUTRET', 'YANNICK', '15869Z', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (12, '15869Z', 'AUTRET', 'YANNICK', '15869Z', 1, 'Cholinestérases', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (13, '15869Z', 'AUTRET', 'YANNICK', '15869Z', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (14, '15869Z', 'AUTRET', 'YANNICK', '15869Z', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (22, '72535S', 'BASTERGUE', 'PATRICK', '72535S', 1, 'Vitesse de sédimentation', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (23, '72535S', 'BASTERGUE', 'PATRICK', '72535S', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (29, '12501N', 'BENARD', 'YANNICK', '12501N', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (30, '12501N', 'BENARD', 'YANNICK', '12501N', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (31, '12501N', 'BENARD', 'YANNICK', '12501N', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (32, '78365E', 'BERNARD', 'SYLVIE', '78365E', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (33, '78365E', 'BERNARD', 'SYLVIE', '78365E', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (34, '78365E', 'BERNARD', 'SYLVIE', '78365E', 1, 'Glycémie', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (35, '78365E', 'BERNARD', 'SYLVIE', '78365E', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (37, '88888Y', 'BILOUS', 'MAELLE', '88888Y', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (38, '88888Y', 'BILOUS', 'MAELLE', '88888Y', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (39, '88888Y', 'BILOUS', 'MAELLE', '88888Y', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (46, '69685V', 'BOBINEAU', 'ISABELLE', '69685V', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (47, '69685V', 'BOBINEAU', 'ISABELLE', '69685V', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (48, '69685V', 'BOBINEAU', 'ISABELLE', '69685V', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (73, '68535V', 'CARRE', 'WILFRID', '68535V', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (74, '68535V', 'CARRE', 'WILFRID', '68535V', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (75, '68535V', 'CARRE', 'WILFRID', '68535V', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (89, '15801A', 'CHEVREL', 'DANIEL', '15801A', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (90, '15801A', 'CHEVREL', 'DANIEL', '15801A', 1, 'Vitesse de sédimentation', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (91, '16034D', 'CLOCHEFERT', 'NATHALIE', '16034D', 1, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (92, '16034D', 'CLOCHEFERT', 'NATHALIE', '16034D', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (93, '16034D', 'CLOCHEFERT', 'NATHALIE', '16034D', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (108, '18059E', 'DAMON', 'MARIE', '18059E', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (109, '18059E', 'DAMON', 'MARIE', '18059E', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (110, '18059E', 'DAMON', 'MARIE', '18059E', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (111, '15377P', 'DAVID', 'CHRYSTELE', '15377P', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (112, '16686M', 'DEBORDE', 'CATHERINE', '16686M', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (113, '16686M', 'DEBORDE', 'CATHERINE', '16686M', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (120, '62838C', 'DELALANDE', 'LAURIE', '62838C', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (121, '62838C', 'DELALANDE', 'LAURIE', '62838C', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (122, '62838C', 'DELALANDE', 'LAURIE', '62838C', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (123, '14421A', 'DELANOE', 'JACKY', '14421A', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (124, '14421A', 'DELANOE', 'JACKY', '14421A', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (125, '10046V', 'DELOURME', 'REGINE', '10046V', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (129, '17043A', 'DENIOT', 'GWENAELLE', '17043A', 2, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (130, '17043A', 'DENIOT', 'GWENAELLE', '17043A', 2, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (131, '17043A', 'DENIOT', 'GWENAELLE', '17043A', 2, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (132, '62638K', 'DEUTSCH', 'STEPHANIE-MARIE', '62638K', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (133, '62638K', 'DEUTSCH', 'STEPHANIE-MARIE', '62638K', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (134, '62638K', 'DEUTSCH', 'STEPHANIE-MARIE', '62638K', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (138, '10841J', 'DIOT', 'CHRISTIAN', '10841J', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (139, '10841J', 'DIOT', 'CHRISTIAN', '10841J', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (140, '10841J', 'DIOT', 'CHRISTIAN', '10841J', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (141, '15432Z', 'DOLIVET', 'ANNE', '15432Z', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (142, '15432Z', 'DOLIVET', 'ANNE', '15432Z', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (143, '15432Z', 'DOLIVET', 'ANNE', '15432Z', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (144, '06627D', 'EBER', 'FREDERIQUE', '06627D', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (145, '06627D', 'EBER', 'FREDERIQUE', '06627D', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (146, '06627D', 'EBER', 'FREDERIQUE', '06627D', 1, 'Vitesse de sédimentation', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (147, '07131B', 'ECOLAN', 'PATRICK', '07131B', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (148, '07131B', 'ECOLAN', 'PATRICK', '07131B', 1, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (149, '07131B', 'ECOLAN', 'PATRICK', '07131B', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (156, '12641Q', 'FILLAUT', 'MARTINE', '12641Q', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (157, '12641Q', 'FILLAUT', 'MARTINE', '12641Q', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (158, '12641Q', 'FILLAUT', 'MARTINE', '12641Q', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (162, '05991M', 'FOSTIER', 'ALEXIS', '05991M', 2, 'Bilan hépatique', 'BEAULIEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (163, '05991M', 'FOSTIER', 'ALEXIS', '05991M', 2, 'Urée créatinine', 'BEAULIEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (164, '05991M', 'FOSTIER', 'ALEXIS', '05991M', 2, 'NFS - Plaquettes', 'BEAULIEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (177, '64462S', 'GERVAIS', 'LAURENT', '64462S', 2, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (178, '64462S', 'GERVAIS', 'LAURENT', '64462S', 2, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (179, '64462S', 'GERVAIS', 'LAURENT', '64462S', 2, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (190, '18141T', 'GONDRET', 'FLORENCE', '18141T', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (191, '18141T', 'GONDRET', 'FLORENCE', '18141T', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (192, '18141T', 'GONDRET', 'FLORENCE', '18141T', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (193, '16459Q', 'GREHAL', 'CYRILLE', '16459Q', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (194, '16459Q', 'GREHAL', 'CYRILLE', '16459Q', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (195, '16459Q', 'GREHAL', 'CYRILLE', '16459Q', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (196, '16459Q', 'GREHAL', 'CYRILLE', '16459Q', 1, 'Cholinestérases', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (197, '18249L', 'GRENIER', 'ERIC', '18249L', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (198, '18249L', 'GRENIER', 'ERIC', '18249L', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (199, '18249L', 'GRENIER', 'ERIC', '18249L', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (200, '13890Y', 'GUERIN', 'SYLVIE', '13890Y', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (201, '13890Y', 'GUERIN', 'SYLVIE', '13890Y', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (202, '14654D', 'GUERIN', 'CHRISTIAN', '14654D', 1, 'Glycémie', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (203, '14654D', 'GUERIN', 'CHRISTIAN', '14654D', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (204, '14654D', 'GUERIN', 'CHRISTIAN', '14654D', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (205, '14654D', 'GUERIN', 'CHRISTIAN', '14654D', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (216, '51903R', 'HELIAS', 'VALERIE', '51903R', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (217, '51903R', 'HELIAS', 'VALERIE', '51903R', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (218, '51903R', 'HELIAS', 'VALERIE', '51903R', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (222, '15608Q', 'HERAULT', 'FREDERIC', '15608Q', 2, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (223, '15608Q', 'HERAULT', 'FREDERIC', '15608Q', 2, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (224, '15608Q', 'HERAULT', 'FREDERIC', '15608Q', 2, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (234, '15633S', 'HUCHET', 'NICOLE', '15633S', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (235, '18292H', 'JACQUOT', 'EMMANUEL', '18292H', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (236, '18292H', 'JACQUOT', 'EMMANUEL', '18292H', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (237, '18292H', 'JACQUOT', 'EMMANUEL', '18292H', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (242, '18252P', 'JAN', 'GWENAEL', '18252P', 2, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (243, '18252P', 'JAN', 'GWENAEL', '18252P', 2, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (244, '18252P', 'JAN', 'GWENAEL', '18252P', 1, 'Vitesse de sédimentation', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (245, '18252P', 'JAN', 'GWENAEL', '18252P', 2, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (255, '10924Z', 'LABBE-AUPERIN', 'CATHERINE', '10924Z', 1, 'Urée créatinine', 'BEAULIEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (256, '10924Z', 'LABBE-AUPERIN', 'CATHERINE', '10924Z', 1, 'Bilan hépatique', 'BEAULIEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (257, '10924Z', 'LABBE-AUPERIN', 'CATHERINE', '10924Z', 1, 'NFS - Plaquettes', 'BEAULIEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (263, '15286Q', 'LAMBERTON', 'PHILIPPE', '15286Q', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (280, '12613K', 'LE GOUEVEC', 'FRANCIS', '12613K', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (281, '12613K', 'LE GOUEVEC', 'FRANCIS', '12613K', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (282, '15578H', 'LE MOUEL', 'THIBAUD', '15578H', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (283, '15578H', 'LE MOUEL', 'THIBAUD', '15578H', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (284, '15578H', 'LE MOUEL', 'THIBAUD', '15578H', 1, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (285, '07511P', 'LE QUENNEC', 'YVON', '07511P', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (286, '07511P', 'LE QUENNEC', 'YVON', '07511P', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (287, '07511P', 'LE QUENNEC', 'YVON', '07511P', 1, 'Glycémie', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (288, '07511P', 'LE QUENNEC', 'YVON', '07511P', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (289, '07511P', 'LE QUENNEC', 'YVON', '07511P', 1, 'Cholestérol Triglycérides', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (292, '15716H', 'LEBRET', 'BENEDICTE', '15716H', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (293, '15716H', 'LEBRET', 'BENEDICTE', '15716H', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (296, '16466Y', 'LEBRETON', 'LIONEL', '16466Y', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (299, '06489D', 'LEFAUCHEUR', 'LOUIS', '06489D', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (308, '18074W', 'LEMOSQUET', 'SOPHIE', '18074W', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (309, '18074W', 'LEMOSQUET', 'SOPHIE', '18074W', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (310, '18074W', 'LEMOSQUET', 'SOPHIE', '18074W', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (312, '15081S', 'LEROUX', 'ALEXANDRA', '15081S', 2, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (313, '07537S', 'LETANNEUR', 'J CLAUDE', '07537S', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (314, '07537S', 'LETANNEUR', 'J CLAUDE', '07537S', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (315, '07537S', 'LETANNEUR', 'J CLAUDE', '07537S', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (319, '10637M', 'LOUVEAU', 'ISABELLE', '10637M', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (320, '10637M', 'LOUVEAU', 'ISABELLE', '10637M', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (321, '10637M', 'LOUVEAU', 'ISABELLE', '10637M', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (324, '10831Y', 'LURON', 'ISABELLE', '10831Y', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (325, '10831Y', 'LURON', 'ISABELLE', '10831Y', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (326, '10831Y', 'LURON', 'ISABELLE', '10831Y', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (327, '14074Y', 'MADEC', 'MARIE-NOELLE', '14074Y', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (328, '14074Y', 'MADEC', 'MARIE-NOELLE', '14074Y', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (329, '14074Y', 'MADEC', 'MARIE-NOELLE', '14074Y', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (330, '15289T', 'MAILLARD', 'MARIE BERNADETTE', '15289T', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (331, '15289T', 'MAILLARD', 'MARIE BERNADETTE', '15289T', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (332, '15289T', 'MAILLARD', 'MARIE BERNADETTE', '15289T', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (333, '10240F', 'MALBERT', 'CHARLES', '10240F', 1, 'Sérologie brucellique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (334, '10240F', 'MALBERT', 'CHARLES', '10240F', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (335, '10240F', 'MALBERT', 'CHARLES', '10240F', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (337, '15577G', 'MARCHE', 'LAURENT', '15577G', 2, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (338, '15577G', 'MARCHE', 'LAURENT', '15577G', 2, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (339, '15577G', 'MARCHE', 'LAURENT', '15577G', 2, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (340, '71985V', 'MEJIA GUADARRAMA', 'CESAR AUGUSTO', '71985V', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (341, '71985V', 'MEJIA GUADARRAMA', 'CESAR AUGUSTO', '71985V', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (342, '71985V', 'MEJIA GUADARRAMA', 'CESAR AUGUSTO', '71985V', 1, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (343, '18413P', 'METZINGER', 'VALERIE', '18413P', 2, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (344, '18413P', 'METZINGER', 'VALERIE', '18413P', 2, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (345, '18413P', 'METZINGER', 'VALERIE', '18413P', 2, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (352, '13460F', 'MONNIER', 'ALAIN', '13460F', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (353, '13460F', 'MONNIER', 'ALAIN', '13460F', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (354, '13460F', 'MONNIER', 'ALAIN', '13460F', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (356, '04752Q', 'MOUNIER', 'ANNE-MARIE', '04752Q', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (357, '04752Q', 'MOUNIER', 'ANNE-MARIE', '04752Q', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (358, '04752Q', 'MOUNIER', 'ANNE-MARIE', '04752Q', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (360, '10279Y', 'MOUROT', 'JACQUES', '10279Y', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (361, '10279Y', 'MOUROT', 'JACQUES', '10279Y', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (375, '64020M', 'PAPURA', 'DACIANA', '64020M', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (376, '64020M', 'PAPURA', 'DACIANA', '64020M', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (377, '64020M', 'PAPURA', 'DACIANA', '64020M', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (381, '11746S', 'PASQUIER', 'ANNE', '11746S', 1, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (382, '11746S', 'PASQUIER', 'ANNE', '11746S', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (383, '11746S', 'PASQUIER', 'ANNE', '11746S', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (391, '77021T', 'PHILIPPE', 'MARINE', '77021T', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (392, '77021T', 'PHILIPPE', 'MARINE', '77021T', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (393, '77021T', 'PHILIPPE', 'MARINE', '77021T', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (403, '18076Y', 'PLANTARD', 'OLIVIER', '18076Y', 2, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (404, '14061J', 'PORTANGUEN', 'JACQUES', '14061J', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (413, '18116R', 'QUESNEL', 'HELENE', '18116R', 2, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (414, '18116R', 'QUESNEL', 'HELENE', '18116R', 2, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (415, '18116R', 'QUESNEL', 'HELENE', '18116R', 2, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (416, '15757C', 'QUINQUIS', 'PATRICE', '15757C', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (417, '15757C', 'QUINQUIS', 'PATRICE', '15757C', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (418, '15757C', 'QUINQUIS', 'PATRICE', '15757C', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (435, '59443M', 'ROBERT', 'OLIVIER', '59443M', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (436, '59443M', 'ROBERT', 'OLIVIER', '59443M', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (437, '59443M', 'ROBERT', 'OLIVIER', '59443M', 1, 'Glycémie', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (438, '59443M', 'ROBERT', 'OLIVIER', '59443M', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (442, '14636J', 'ROME', 'VERONIQUE', '14636J', 2, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (443, '14636J', 'ROME', 'VERONIQUE', '14636J', 2, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (444, '14636J', 'ROME', 'VERONIQUE', '14636J', 2, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (447, '03184L', 'ROUZE', 'JOELLE', '03184L', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (448, '03184L', 'ROUZE', 'JOELLE', '03184L', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (449, '03184L', 'ROUZE', 'JOELLE', '03184L', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (450, '16946V', 'RUFFIO', 'VERONIQUE', '16946V', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (451, '16946V', 'RUFFIO', 'VERONIQUE', '16946V', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (452, '16946V', 'RUFFIO', 'VERONIQUE', '16946V', 1, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (461, '10416X', 'SARNIGUET', 'ALAIN', '10416X', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (465, '10794H', 'SOUMET', 'VALERIE', '10794H', 1, 'Bilan hépatique', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (466, '10794H', 'SOUMET', 'VALERIE', '10794H', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (467, '10794H', 'SOUMET', 'VALERIE', '10794H', 1, 'Urée créatinine', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (468, '04479T', 'TANGUY', 'ANNE-MARIE', '04479T', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (469, '04479T', 'TANGUY', 'ANNE-MARIE', '04479T', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (475, '15735D', 'THIBAULT', 'JEAN-NOEL', '15735D', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (476, '15735D', 'THIBAULT', 'JEAN-NOEL', '15735D', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (477, '15735D', 'THIBAULT', 'JEAN-NOEL', '15735D', 1, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (489, '16158N', 'VALETTE', 'SYLVIE', '16158N', 2, 'Urée créatinine', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (490, '16158N', 'VALETTE', 'SYLVIE', '16158N', 2, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (491, '16158N', 'VALETTE', 'SYLVIE', '16158N', 2, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (502, '09554K', 'BOSSARD', 'PASCAL', '09554K', 1, 'Bilan hépatique', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (503, '62644R', 'GARNIER', 'FABIENNE', '62644R', 1, 'NFS - Plaquettes', 'RENNES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (504, '16127E', 'LETERME', 'NATHALIE', '16127E', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (505, '10938C', 'RENARD VIETORD', NULL, '10938C', 1, 'NFS - Plaquettes', 'LE RHEU');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (506, '11590Y', 'JACQUELIN PEYRAUD', 'YOLANDE', '11590Y', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (507, '11590Y', 'JACQUELIN PEYRAUD', 'YOLANDE', '11590Y', 1, 'Urée créatine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (508, '11590Y', 'JACQUELIN PEYRAUD', 'YOLANDE', '11590Y', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (509, '10669X', 'PERE', 'MARIE CHRISTINE', '10669X', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (510, '10669X', 'PERE', 'MARIE CHRISTINE', '10669X', 1, 'Uréee créatine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (511, '10669X', 'PERE', 'MARIE CHRISTINE', '10669X', 1, 'Bilan hépatique', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (512, '18276Q', 'BOUDON', 'ANNE', ' 18276Q', 1, 'NFS - Plaquettes', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (513, '18276Q', 'BOUDON', 'ANNE', ' 18276Q', 1, 'Urée créatinine', 'SAINT_GILLES');
INSERT INTO `analyse session 12/2000` (`N°`, `matricule`, `nom_agent`, `prenom_agent`, `Champ4`, `Champ5`, `Champ6`, `Champ7`) VALUES (514, '18276Q', 'BOUDON', 'ANNE', ' 18276Q', 1, 'Bilan hépatique', 'SAINT_GILLES');
# 212 records

#
# Table structure for table 'Paramètre'
#

DROP TABLE IF EXISTS `Paramètre`;

CREATE TABLE `Paramètre` (
  `Code Paramètre` VARCHAR(20) NOT NULL, 
  `Valeur Paramètre` VARCHAR(50), 
  `Intitulé paramètre` VARCHAR(50), 
  INDEX (`Code Paramètre`), 
  PRIMARY KEY (`Code Paramètre`)
) ENGINE=innodb DEFAULT CHARSET=utf8;

SET autocommit=1;

#
# Dumping data for table 'Paramètre'
#

INSERT INTO `Paramètre` (`Code Paramètre`, `Valeur Paramètre`, `Intitulé paramètre`) VALUES ('ATTACH_NAME', 'Medical.mdb', 'Nom de la base de données attachée');
INSERT INTO `Paramètre` (`Code Paramètre`, `Valeur Paramètre`, `Intitulé paramètre`) VALUES ('ATTACH_PATH', 'R:\\medical', 'Chemin de la base de données attachée');
# 2 records

