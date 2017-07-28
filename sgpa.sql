-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 28-Jul-2017 às 18:40
-- Versão do servidor: 10.1.21-MariaDB
-- PHP Version: 7.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sgpa`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `momentos`
--

CREATE TABLE `momentos` (
  `id` int(11) NOT NULL,
  `planos_de_aulas_id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `texto` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `planos_de_aula`
--

CREATE TABLE `planos_de_aula` (
  `id` int(11) NOT NULL,
  `titulo` varchar(45) DEFAULT NULL,
  `subtitulo` varchar(45) DEFAULT NULL,
  `descricao` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `planos_de_aula`
--

INSERT INTO `planos_de_aula` (`id`, `titulo`, `subtitulo`, `descricao`) VALUES
(1, 'Plano 1', 'Subtitulo', 'descrição do plano 1 blblabalbalbalbalbal'),
(2, 'Plano 2 ', 'subtitulo', 'blablabalbablablabalbablablabalbablablabalba'),
(3, 'teste3', 'subtitulo ', 'desc'),
(4, 'teste 4 ', 'sub titulo', 'desc');

-- --------------------------------------------------------

--
-- Estrutura da tabela `recursos`
--

CREATE TABLE `recursos` (
  `id` int(11) NOT NULL,
  `momentos_id` int(11) NOT NULL,
  `link` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `momentos`
--
ALTER TABLE `momentos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_momentos_planos_de_aulas_idx` (`planos_de_aulas_id`);

--
-- Indexes for table `planos_de_aula`
--
ALTER TABLE `planos_de_aula`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `recursos`
--
ALTER TABLE `recursos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_recursos_momentos1_idx` (`momentos_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `momentos`
--
ALTER TABLE `momentos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `planos_de_aula`
--
ALTER TABLE `planos_de_aula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `recursos`
--
ALTER TABLE `recursos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `momentos`
--
ALTER TABLE `momentos`
  ADD CONSTRAINT `fk_momentos_planos_de_aulas` FOREIGN KEY (`planos_de_aulas_id`) REFERENCES `planos_de_aula` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `recursos`
--
ALTER TABLE `recursos`
  ADD CONSTRAINT `fk_recursos_momentos1` FOREIGN KEY (`momentos_id`) REFERENCES `momentos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
