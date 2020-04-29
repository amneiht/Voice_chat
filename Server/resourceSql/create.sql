-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Apr 28, 2020 at 03:47 PM
-- Server version: 5.7.29
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chat`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`%` PROCEDURE `addMember` (`gp` VARCHAR(35), `admin` VARCHAR(35), `mem` VARCHAR(35))  BEGIN
DECLARE pri int(2) ;
SELECT quyen into pri FROM tvNhom WHERE idNhom = gp and idTV = admin ;
if ( pri = 1 ) THEN
INSERT INTO tvNhom VALUES ( gp , mem , admin , 0 ) ;
end if ;
end$$

CREATE DEFINER=`root`@`%` PROCEDURE `addPri` (`gp` VARCHAR(35), `admin` VARCHAR(35), `mem` VARCHAR(35), `dt` INT(2))  BEGIN
DECLARE pri int(2) ;
SELECT quyen into pri FROM tvNhom WHERE idNhom = gp and idTV = admin ;
if ( pri = 1 ) THEN
update tvNhom set quyen = dt where idNhom = gp and idTV = mem ;
end if ;
end$$

CREATE DEFINER=`root`@`%` PROCEDURE `deleteGroup` (`gp` VARCHAR(35), `admin` VARCHAR(35))  BEGIN
DECLARE pri int(2) ;
SELECT quyen into pri FROM tvNhom WHERE idNhom = gp and idTV = admin ;
if ( pri = 1 ) THEN
delete from tinNhan where idNhan = gp ;
delete from tvNhom where idNhom = gp ;
delete from nhom where nhom.idNhom = gp ;
end if ;
end$$

CREATE DEFINER=`root`@`%` PROCEDURE `deleteMember` (`gp` VARCHAR(35), `admin` VARCHAR(35), `mem` VARCHAR(35))  BEGIN
DECLARE pri int(2) ;
SELECT quyen into pri FROM tvNhom WHERE idNhom = gp and idTV = admin ;
if ( pri = 1 ) THEN
delete from  tvNhom where tvNhom.idNhom = gp and tvNhom.idTV = mem ;
end if ;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `banBe`
--

CREATE TABLE `banBe` (
  `id1` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `id2` varchar(35) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nhom`
--

CREATE TABLE `nhom` (
  `idNhom` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `nguoiTao` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `hinhAnh` varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tenNhom` varchar(70) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tepTin`
--

CREATE TABLE `tepTin` (
  `idFile` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `data` mediumblob NOT NULL,
  `ngayGui` date DEFAULT NULL,
  `tenFile` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `thongtin`
--

CREATE TABLE `thongtin` (
  `nguoiDung` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `ten` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `matKhau` varchar(500) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tinNhan`
--

CREATE TABLE `tinNhan` (
  `idNhan` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `idGui` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `noiDung` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `idFile` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngayGui` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tvNhom`
--

CREATE TABLE `tvNhom` (
  `idNhom` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `idTV` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `idAdd` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `quyen` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `yeuCau`
--

CREATE TABLE `yeuCau` (
  `idNhom` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `idTv` varchar(35) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `banBe`
--
ALTER TABLE `banBe`
  ADD PRIMARY KEY (`id1`,`id2`),
  ADD KEY `id2` (`id2`);

--
-- Indexes for table `nhom`
--
ALTER TABLE `nhom`
  ADD PRIMARY KEY (`idNhom`);

--
-- Indexes for table `thongtin`
--
ALTER TABLE `thongtin`
  ADD PRIMARY KEY (`ten`);

--
-- Indexes for table `tinNhan`
--
ALTER TABLE `tinNhan`
  ADD KEY `idGui` (`idGui`);

--
-- Indexes for table `tvNhom`
--
ALTER TABLE `tvNhom`
  ADD PRIMARY KEY (`idNhom`,`idTV`),
  ADD KEY `idTV` (`idTV`);

--
-- Indexes for table `yeuCau`
--
ALTER TABLE `yeuCau`
  ADD PRIMARY KEY (`idNhom`,`idTv`),
  ADD KEY `idTv` (`idTv`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `banBe`
--
ALTER TABLE `banBe`
  ADD CONSTRAINT `banBe_ibfk_1` FOREIGN KEY (`id1`) REFERENCES `thongtin` (`ten`),
  ADD CONSTRAINT `banBe_ibfk_2` FOREIGN KEY (`id2`) REFERENCES `thongtin` (`ten`);

--
-- Constraints for table `tinNhan`
--
ALTER TABLE `tinNhan`
  ADD CONSTRAINT `tinNhan_ibfk_1` FOREIGN KEY (`idGui`) REFERENCES `thongtin` (`ten`),
  ADD CONSTRAINT `tinNhan_ibfk_2` FOREIGN KEY (`idGui`) REFERENCES `thongtin` (`ten`);

--
-- Constraints for table `tvNhom`
--
ALTER TABLE `tvNhom`
  ADD CONSTRAINT `tvNhom_ibfk_1` FOREIGN KEY (`idNhom`) REFERENCES `nhom` (`idNhom`),
  ADD CONSTRAINT `tvNhom_ibfk_2` FOREIGN KEY (`idTV`) REFERENCES `thongtin` (`ten`);

--
-- Constraints for table `yeuCau`
--
ALTER TABLE `yeuCau`
  ADD CONSTRAINT `yeuCau_ibfk_1` FOREIGN KEY (`idNhom`) REFERENCES `nhom` (`idNhom`),
  ADD CONSTRAINT `yeuCau_ibfk_2` FOREIGN KEY (`idTv`) REFERENCES `thongtin` (`ten`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;