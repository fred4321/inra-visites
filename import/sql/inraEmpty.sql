-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Dim 16 Novembre 2014 à 16:47
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `inra`
--
CREATE DATABASE IF NOT EXISTS `inra` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `inra`;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `agent`, `bs`, `bs_observation`, `groupe`, `groupe_agent`, `import_stack`, `journal`, `observation`, `params`, `rapport`, `unite`, `user`, `verification_import`, `verification_stack`, `vm`, `vm_observation`;

-- --------------------------------------------------------

--
-- Structure de la table `agent`
--

CREATE TABLE IF NOT EXISTS `agent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `archive` bit(1) NOT NULL,
  `courriel` varchar(255) DEFAULT NULL,
  `date_arret_travail` datetime DEFAULT NULL,
  `date_creation` datetime NOT NULL,
  `date_debut_contrat` datetime DEFAULT NULL,
  `date_dernierevm` datetime DEFAULT NULL,
  `date_entree` datetime DEFAULT NULL,
  `date_fin_contrat` datetime DEFAULT NULL,
  `date_modification` datetime NOT NULL,
  `date_naissance` datetime DEFAULT NULL,
  `date_sortie` datetime DEFAULT NULL,
  `departement` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `groupe` varchar(255) DEFAULT NULL,
  `gu_id` bigint(20) DEFAULT NULL,
  `insee` varchar(255) DEFAULT NULL,
  `lib_emploi` varchar(255) DEFAULT NULL,
  `lieu_naissance` varchar(255) DEFAULT NULL,
  `matricule` varchar(255) NOT NULL,
  `nationalite` varchar(255) DEFAULT NULL,
  `naturevm` varchar(255) DEFAULT NULL,
  `nom` varchar(255) NOT NULL,
  `num_tel` varchar(255) DEFAULT NULL,
  `periodicite` varchar(255) DEFAULT NULL,
  `position_adm` varchar(255) DEFAULT NULL,
  `poste_travail` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) NOT NULL,
  `prioritairebs` bit(1) NOT NULL,
  `prioritairevm` bit(1) NOT NULL,
  `site` varchar(255) DEFAULT NULL,
  `unite_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5hstg9uis3edp6n12sjldkphx` (`gu_id`),
  KEY `FK_rxhw0etua1q9pxyq6wafa6vac` (`unite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `bs`
--

CREATE TABLE IF NOT EXISTS `bs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `absent` bit(1) NOT NULL,
  `agent_id` bigint(20) NOT NULL,
  `date_convocation` datetime DEFAULT NULL,
  `date_passage` datetime DEFAULT NULL,
  `date_prescription` datetime NOT NULL,
  `heure_debut` varchar(255) DEFAULT NULL,
  `heure_fin` varchar(255) DEFAULT NULL,
  `lieu` varchar(255) DEFAULT NULL,
  `statut` varchar(255) NOT NULL DEFAULT 'créé',
  `type` longtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gab5sns696nt3t2chsuo7s9bi` (`agent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `bs_observation`
--

CREATE TABLE IF NOT EXISTS `bs_observation` (
  `bs_remarques_id` bigint(20) DEFAULT NULL,
  `observation_id` bigint(20) DEFAULT NULL,
  `remarques_idx` int(11) DEFAULT NULL,
  KEY `FK_2xobpgu6yns2rjw5qnc764fj7` (`observation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

CREATE TABLE IF NOT EXISTS `groupe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `groupe_agent`
--

CREATE TABLE IF NOT EXISTS `groupe_agent` (
  `groupe_agents_id` bigint(20) DEFAULT NULL,
  `agent_id` bigint(20) DEFAULT NULL,
  KEY `FK_7c94bcc7skqcq5laujw4o5pu1` (`agent_id`),
  KEY `FK_27k783vakfacphrnijuyu74aj` (`groupe_agents_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `import_stack`
--

CREATE TABLE IF NOT EXISTS `import_stack` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `erreur` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `matricule` varchar(255) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `rapport_id` bigint(20) NOT NULL,
  `row` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_71u3tn6pg0iaf85a009op1x3x` (`rapport_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `journal`
--

CREATE TABLE IF NOT EXISTS `journal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `matricule` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `operation` varchar(255) NOT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `ref` int(11) NOT NULL,
  `type_object` varchar(255) NOT NULL,
  `unite` varchar(255) DEFAULT NULL,
  `user` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `observation`
--

CREATE TABLE IF NOT EXISTS `observation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `text` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `params`
--

CREATE TABLE IF NOT EXISTS `params` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `groupe` varchar(255) NOT NULL,
  `info1` varchar(255) DEFAULT NULL,
  `info2` varchar(255) DEFAULT NULL,
  `info3` varchar(255) DEFAULT NULL,
  `info4` varchar(255) DEFAULT NULL,
  `valeur` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `rapport`
--

CREATE TABLE IF NOT EXISTS `rapport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `statut` varchar(255) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `unite`
--

CREATE TABLE IF NOT EXISTS `unite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `default_gu_id` bigint(20) DEFAULT NULL,
  `importer` bit(1) NOT NULL,
  `lieu` varchar(255) DEFAULT NULL,
  `nom` varchar(255) NOT NULL,
  `site` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5fixmugo5oj63xbsf6yj4i4ay` (`default_gu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `identifiant` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profil` varchar(255) NOT NULL,
  `unite_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3v100tm8avphpa158wbk1bl87` (`unite_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `version`, `email`, `identifiant`, `password`, `profil`, `unite_id`) VALUES
(1, 0, NULL, 'admin', 'admin', 'administrateur', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `verification_import`
--

CREATE TABLE IF NOT EXISTS `verification_import` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `statut` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `verification_stack`
--

CREATE TABLE IF NOT EXISTS `verification_stack` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `info1` varchar(255) DEFAULT NULL,
  `rapport_name` varchar(255) NOT NULL,
  `row` int(11) NOT NULL,
  `type_error` varchar(255) NOT NULL,
  `verif_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c2k8rlhdxe325nxl8cew2necj` (`verif_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `vm`
--

CREATE TABLE IF NOT EXISTS `vm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `agent_id` bigint(20) DEFAULT NULL,
  `amenagement` bit(1) NOT NULL,
  `apte` varchar(255) DEFAULT NULL,
  `avis` varchar(255) DEFAULT NULL,
  `bs_id` bigint(20) DEFAULT NULL,
  `creneau` varchar(255) DEFAULT NULL,
  `date` datetime NOT NULL,
  `date_convoc` datetime DEFAULT NULL,
  `date_envois_courrier_agent` datetime DEFAULT NULL,
  `date_envois_courrierdurrh` datetime DEFAULT NULL,
  `lieu` varchar(255) NOT NULL,
  `modification` datetime NOT NULL,
  `naturevm` varchar(255) DEFAULT NULL,
  `ref_medecin` varchar(255) DEFAULT NULL,
  `site` varchar(255) NOT NULL,
  `statut` varchar(255) NOT NULL,
  `unite_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_s25svca5j6otniihcj2ilwglj` (`agent_id`),
  KEY `FK_9xqbh7c01l0pjku11udlo5uvt` (`bs_id`),
  KEY `FK_c43e90aum5pyrpgqqn7d2cyke` (`unite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `vm_observation`
--

CREATE TABLE IF NOT EXISTS `vm_observation` (
  `vm_conclusions_id` bigint(20) DEFAULT NULL,
  `observation_id` bigint(20) DEFAULT NULL,
  `conclusions_idx` int(11) DEFAULT NULL,
  `vm_observations_id` bigint(20) DEFAULT NULL,
  `observations_idx` int(11) DEFAULT NULL,
  KEY `FK_jrdgkxyu0trkhdd6e3f9lcshj` (`observation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `agent`
--
ALTER TABLE `agent`
  ADD CONSTRAINT `FK_rxhw0etua1q9pxyq6wafa6vac` FOREIGN KEY (`unite_id`) REFERENCES `unite` (`id`),
  ADD CONSTRAINT `FK_5hstg9uis3edp6n12sjldkphx` FOREIGN KEY (`gu_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `bs`
--
ALTER TABLE `bs`
  ADD CONSTRAINT `FK_gab5sns696nt3t2chsuo7s9bi` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`id`);

--
-- Contraintes pour la table `bs_observation`
--
ALTER TABLE `bs_observation`
  ADD CONSTRAINT `FK_2xobpgu6yns2rjw5qnc764fj7` FOREIGN KEY (`observation_id`) REFERENCES `observation` (`id`);

--
-- Contraintes pour la table `groupe_agent`
--
ALTER TABLE `groupe_agent`
  ADD CONSTRAINT `FK_27k783vakfacphrnijuyu74aj` FOREIGN KEY (`groupe_agents_id`) REFERENCES `groupe` (`id`),
  ADD CONSTRAINT `FK_7c94bcc7skqcq5laujw4o5pu1` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`id`);

--
-- Contraintes pour la table `import_stack`
--
ALTER TABLE `import_stack`
  ADD CONSTRAINT `FK_71u3tn6pg0iaf85a009op1x3x` FOREIGN KEY (`rapport_id`) REFERENCES `rapport` (`id`);

--
-- Contraintes pour la table `unite`
--
ALTER TABLE `unite`
  ADD CONSTRAINT `FK_5fixmugo5oj63xbsf6yj4i4ay` FOREIGN KEY (`default_gu_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK_3v100tm8avphpa158wbk1bl87` FOREIGN KEY (`unite_id`) REFERENCES `unite` (`id`);

--
-- Contraintes pour la table `verification_stack`
--
ALTER TABLE `verification_stack`
  ADD CONSTRAINT `FK_c2k8rlhdxe325nxl8cew2necj` FOREIGN KEY (`verif_id`) REFERENCES `verification_import` (`id`);

--
-- Contraintes pour la table `vm`
--
ALTER TABLE `vm`
  ADD CONSTRAINT `FK_c43e90aum5pyrpgqqn7d2cyke` FOREIGN KEY (`unite_id`) REFERENCES `unite` (`id`),
  ADD CONSTRAINT `FK_9xqbh7c01l0pjku11udlo5uvt` FOREIGN KEY (`bs_id`) REFERENCES `bs` (`id`),
  ADD CONSTRAINT `FK_s25svca5j6otniihcj2ilwglj` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`id`);

--
-- Contraintes pour la table `vm_observation`
--
ALTER TABLE `vm_observation`
  ADD CONSTRAINT `FK_jrdgkxyu0trkhdd6e3f9lcshj` FOREIGN KEY (`observation_id`) REFERENCES `observation` (`id`);

  SET FOREIGN_KEY_CHECKS=1;
  
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
