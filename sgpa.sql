-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 31-Jul-2017 às 16:30
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

--
-- Extraindo dados da tabela `momentos`
--

INSERT INTO `momentos` (`id`, `planos_de_aulas_id`, `nome`, `texto`) VALUES
(2, 2, 'texto grande', 'texto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grande'),
(3, 2, 'texto grande', 'texto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grande'),
(4, 2, 'texto grande', 'texto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grande'),
(5, 2, 'texto grande', 'texto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grande'),
(6, 2, 'texto grande', 'texto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grande'),
(7, 2, 'texto grande', 'texto grandetexto grandetexto grande');

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
(2, 'Teste 1', 'subtitulo', 'texto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grandetexto grande');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `planos_de_aula`
--
ALTER TABLE `planos_de_aula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `recursos`
--
ALTER TABLE `recursos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `momentos`
--
ALTER TABLE `momentos`
  ADD CONSTRAINT `fk_momentos_planos_de_aulas` FOREIGN KEY (`planos_de_aulas_id`) REFERENCES `planos_de_aula` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `recursos`
--
ALTER TABLE `recursos`
  ADD CONSTRAINT `fk_recursos_momentos1` FOREIGN KEY (`momentos_id`) REFERENCES `momentos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
