-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 20, 2020 at 06:35 AM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id12123509_mirzashasan95`
--

-- --------------------------------------------------------

--
-- Table structure for table `loginAuth`
--

CREATE TABLE `loginAuth` (
  `id` int(11) NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `role` int(11) NOT NULL,
  `device_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `loginAuth`
--

INSERT INTO `loginAuth` (`id`, `email`, `password`, `role`, `device_id`) VALUES
(1, 'hasanbaig@hotmail.com', '202cb962ac59075b964b07152d234b70', 1, '123'),
(5, 'ali123@hotmail.com', '202cb962ac59075b964b07152d234b70', 2, NULL),
(6, 'ali123@hotmail.com', '81dc9bdb52d04dc20036dbd8313ed055', 2, NULL),
(7, 'kkk@gm.com', '25d55ad283aa400af464c76d713c07ad', 2, NULL),
(8, 'iusama@gmail.com', 'e807f1fcf82d132f9bb018ca6738a19f', 2, '123'),
(9, 'isuam@l.com', '25d55ad283aa400af464c76d713c07ad', 2, NULL),
(10, 'iu@gm.com', '1d4b8859dc8ac9c792d47e01917daa32', 2, NULL),
(11, 'ali123@hotmail.com', 'e10adc3949ba59abbe56e057f20f883e', 2, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `loginAuth`
--
ALTER TABLE `loginAuth`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `loginAuth`
--
ALTER TABLE `loginAuth`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
